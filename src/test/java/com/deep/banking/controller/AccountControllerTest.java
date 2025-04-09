package com.deep.banking.controller;

import com.deep.banking.dto.AccountDto;
import com.deep.banking.service.AccountService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AccountControllerTest
{

    @Mock   //Mocks the AccountService so that it doesn’t call the actual database or logic — just simulates it.
    private AccountService accountService;

    @InjectMocks //Creates an actual object of AccountController, and injects the mock AccountService into it.
    private AccountController accountController;

//  Before each test, this line initializes all the @Mock and @InjectMocks variables.
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    //Method to test Add Account
    @Test  //to mark this method as test, so JUnit will execute it
    void testAddAccount(){
        AccountDto input = new AccountDto(0L, "Test User 1", 1000.0);  //dummy input
        AccountDto saved = new AccountDto(1l, "Test User 2", 1000.0);  //mock saved object

        when(accountService.createAccount(input)).thenReturn(saved);

        ResponseEntity<AccountDto> response = accountController.addAccount(input);

        assertEquals(201,response.getStatusCodeValue());
        assertEquals(saved, response.getBody());

    }

    @Test
    void testGetAccountById(){
        AccountDto saved = new AccountDto(01,"Isha",500);

        when(accountService.getAccountById(saved.id())).thenReturn(saved);

        ResponseEntity<AccountDto> response = accountController.getAccountById(saved.id());
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(saved, response.getBody());
    }
}
