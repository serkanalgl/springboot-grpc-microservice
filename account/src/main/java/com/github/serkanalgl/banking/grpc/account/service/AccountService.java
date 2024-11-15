package com.github.serkanalgl.banking.grpc.account.service;

import com.github.serkanalgl.banking.grpc.account.AccountResponse;
import com.github.serkanalgl.banking.grpc.account.CreateAccountRequest;
import com.github.serkanalgl.banking.grpc.account.GetAccountRequest;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest createAccountRequest);

    AccountResponse getAccount(GetAccountRequest getAccountRequest);
}
