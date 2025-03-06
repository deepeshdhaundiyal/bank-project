package com.deep.banking.mapper;

import com.deep.banking.dto.AccountDto;
import com.deep.banking.entity.Account;

public class AccountMapper
{

    public static Account mapToAccount(AccountDto accountDto){
        Account account = new Account(accountDto.getId(), accountDto.getAccountHolderName(), accountDto.getBalance());
        return account;
    }

    //return object if error happens
    public static AccountDto mapToAccDto(Account account){

        AccountDto accountDto = new AccountDto(account.getId(), account.getAccountHolderName(), account.getBalance());
        return accountDto;

    }

}
