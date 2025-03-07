package com.deep.banking.service.impl;

import com.deep.banking.dto.AccountDto;
import com.deep.banking.entity.Account;
import com.deep.banking.exception.AccountException;
import com.deep.banking.mapper.AccountMapper;
import com.deep.banking.repository.AccountRepository;
import com.deep.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;

    AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
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
        return AccountMapper.mapToAccDto(account);
    }

    @Override
    public AccountDto withdraw(long id, double withdrawAmount){
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist with given id."));

        if(account.getBalance() < withdrawAmount) { new AccountException("Insufficient Balance"); }

        double updatedBalance = account.getBalance() - withdrawAmount;

        account.setBalance(updatedBalance);
        accountRepository.save(account);
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
                .orElseThrow(() -> new AccountException ("Account with given Id doesnot exists"));
        accountRepository.deleteById(id);
    }
}
