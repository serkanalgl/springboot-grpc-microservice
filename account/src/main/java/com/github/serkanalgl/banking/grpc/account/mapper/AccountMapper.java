package com.github.serkanalgl.banking.grpc.account.mapper;

import com.github.serkanalgl.banking.grpc.account.AccountResponse;
import com.github.serkanalgl.banking.grpc.account.CreateAccountRequest;
import com.github.serkanalgl.banking.grpc.account.model.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper  {

    Account createAccountRequestToAccount(CreateAccountRequest createAccountRequest);
    AccountResponse accountToAccountResponse(Account account);
}
