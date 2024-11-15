package com.github.serkanalgl.banking.grpc.moneytransfer.service.impl;

import com.github.serkanalgl.banking.grpc.account.AccountResponse;
import com.github.serkanalgl.banking.grpc.account.AccountServiceGrpc;
import com.github.serkanalgl.banking.grpc.account.GetAccountRequest;
import com.github.serkanalgl.banking.grpc.moneytransfer.MoneyTransferRequest;
import com.github.serkanalgl.banking.grpc.moneytransfer.MoneyTransferResponse;
import com.github.serkanalgl.banking.grpc.moneytransfer.service.MoneyTransferService;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class MoneyTransferServiceImpl implements MoneyTransferService {

    @GrpcClient("account")
    AccountServiceGrpc.AccountServiceBlockingStub accountServiceStub;

    @Override
    public MoneyTransferResponse transfer(MoneyTransferRequest moneyTransferRequest) {

        AccountResponse fromAccount = accountServiceStub.getAccount(GetAccountRequest.newBuilder().setAccountNumber(moneyTransferRequest.getFromAccount()).build());
        AccountResponse toAccount = accountServiceStub.getAccount(GetAccountRequest.newBuilder().setAccountNumber(moneyTransferRequest.getToAccount()).build());

        //check balance
        //subtract amount from origin account
        //add amount to destination account
        //save transaction record for both account

        log.info("Money transfered from account: {} to account: {}", fromAccount.getAccountNumber(), toAccount.getAccountNumber());

        return MoneyTransferResponse.newBuilder()
                .setTransactionReference(UUID.randomUUID().toString())
                .setFromAccount(fromAccount.getAccountNumber())
                .setToAccount(toAccount.getAccountNumber())
                .setAmount(moneyTransferRequest.getAmount())
                .setTransferAt(Instant.now().getEpochSecond())
                .build();

    }
}
