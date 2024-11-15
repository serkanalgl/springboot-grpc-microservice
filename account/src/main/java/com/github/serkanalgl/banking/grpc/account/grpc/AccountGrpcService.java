package com.github.serkanalgl.banking.grpc.account.grpc;

import com.github.serkanalgl.banking.grpc.account.*;
import com.github.serkanalgl.banking.grpc.account.repository.AccountRepository;
import com.github.serkanalgl.banking.grpc.account.service.AccountService;
import com.google.type.Money;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class AccountGrpcService extends AccountServiceGrpc.AccountServiceImplBase {

    private final AccountService accountService;

    @Override
    public void createAccount(CreateAccountRequest request, StreamObserver<AccountResponse> responseObserver) {

        AccountResponse accountResponse = accountService.createAccount(request);

        responseObserver.onNext(accountResponse);
        responseObserver.onCompleted();

        log.info("Created account {}", accountResponse);

    }

    @Override
    public void getAccount(GetAccountRequest request, StreamObserver<AccountResponse> responseObserver) {

        AccountResponse accountResponse = accountService.getAccount(request);

        responseObserver.onNext(accountResponse);
        responseObserver.onCompleted();

    }
}
