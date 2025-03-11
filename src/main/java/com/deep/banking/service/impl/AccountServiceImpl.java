package com.deep.banking.service.impl;

import com.deep.banking.dto.AccountDto;
import com.deep.banking.dto.TransferFundDto;
import com.deep.banking.entity.Account;
import com.deep.banking.entity.Transaction;
import com.deep.banking.exception.AccountException;
import com.deep.banking.mapper.AccountMapper;
import com.deep.banking.repository.AccountRepository;
import com.deep.banking.repository.TransactionRepository;
import com.deep.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;

    private static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";

    private static final String TRANSACTION_TYPE_WITHDRAW = "WITHDRAW";

    AccountServiceImpl(AccountRepository accountRepository,
                       TransactionRepository transactionRepository){
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    //method to create an Account
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        //map my account dto to jpa entity
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccDto(savedAccount);
    }

    //method to find Account by id
    @Override
    public AccountDto getAccountById(long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account Doest not exists with provided ID"));
        return AccountMapper.mapToAccDto(account);
    }

    //method to update the balance
    @Override
    public AccountDto deposit(long id, double depositAmount) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new AccountException("Account does not exists with provided id."));

        double updatedBalance = account.getBalance() + depositAmount;
        account.setBalance(updatedBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(depositAmount);
        transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
        transaction.setTimeStamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        return AccountMapper.mapToAccDto(account);
    }

    @Override
    public AccountDto withdraw(long id, double withdrawAmount){
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist with given id."));

        if(account.getBalance() < withdrawAmount) { throw new AccountException("Insufficient Balance"); }

        double updatedBalance = account.getBalance() - withdrawAmount;

        account.setBalance(updatedBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(withdrawAmount);
        transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAW);
        transaction.setTimeStamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        return AccountMapper.mapToAccDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accountList = accountRepository.findAll();
        List<AccountDto> accountDtoList = accountList.stream().map((account) -> AccountMapper.mapToAccDto(account)).collect(Collectors.toList());
        return accountDtoList;
    }

    @Override
    public void deleteAccount(long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountException ("Account with given Id does not exists"));
        accountRepository.deleteById(id);
    }

    @Override
    public void transferFund(TransferFundDto transferFundDto){

        //get details for account id need to debit the amount
        Account fromAccount = accountRepository.findById(transferFundDto.fromAccountId())
                .orElseThrow(() -> new AccountException("Account with given Id does not exists"));

        //logic to handle if transfer amount is greater than balance in account
        if (fromAccount.getBalance() < transferFundDto.amount()) {
            throw new AccountException("Insufficient Balance!");  // Properly throwing exception
        }

        //get details for account id need to credit the amount
        Account toAccount = accountRepository.findById(transferFundDto.toAccountId())
                .orElseThrow(() -> new AccountException("Account with given Id does not exists"));

        //logic to debit amount fromAccount holder
        fromAccount.setBalance(
            fromAccount.getBalance() - transferFundDto.amount()
        );

            //logic to credit amount toAccount holder
            toAccount.setBalance(toAccount.getBalance() + transferFundDto.amount());

            accountRepository.save(fromAccount);

            accountRepository.save(toAccount);
    }
}
