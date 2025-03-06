package com.deep.banking.service;

import com.deep.banking.dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto account);

    AccountDto getAccountById(long id);

    AccountDto deposit(long id, double depositAmount);

    AccountDto withdraw(long id, double depositAmount);

    List<AccountDto> getAllAccounts();

    void deleteAccount(long id);

}
