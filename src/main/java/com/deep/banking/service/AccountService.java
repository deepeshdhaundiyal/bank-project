package com.deep.banking.service;

import com.deep.banking.dto.AccountDto;

public interface AccountService {

    AccountDto createAccount(AccountDto account);

    AccountDto getAccountById(long id);

}
