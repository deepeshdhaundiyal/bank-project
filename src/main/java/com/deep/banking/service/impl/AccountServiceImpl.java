package com.deep.banking.service.impl;

import com.deep.banking.dto.AccountDto;
import com.deep.banking.entity.Account;
import com.deep.banking.mapper.AccountMapper;
import com.deep.banking.repository.AccountRepository;
import com.deep.banking.service.AccountService;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RuntimeException("Account Doest not exists with provided ID"));
        return AccountMapper.mapToAccDto(account);
    }

}
