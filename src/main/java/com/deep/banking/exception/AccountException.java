package com.deep.banking.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

public class AccountException extends RuntimeException{

    public AccountException(String message){
        super(message);
    }
}
