package com.deep.banking.service;

import com.deep.banking.dto.AccountDto;
import com.deep.banking.dto.TransactionDto;
import com.deep.banking.dto.TransferFundDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto account);

    AccountDto getAccountById(long id);

    AccountDto deposit(long id, double depositAmount);

    AccountDto withdraw(long id, double depositAmount);

    List<AccountDto> getAllAccounts();

    void deleteAccount(long id);

    void transferFund(TransferFundDto transferFundDto);

    List<TransactionDto> getAccountTransaction(long accountId);

}
