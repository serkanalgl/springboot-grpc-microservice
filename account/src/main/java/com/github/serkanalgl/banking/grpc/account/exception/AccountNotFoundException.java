package com.github.serkanalgl.banking.grpc.account.exception;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String message){
        super(message);
    }

}
