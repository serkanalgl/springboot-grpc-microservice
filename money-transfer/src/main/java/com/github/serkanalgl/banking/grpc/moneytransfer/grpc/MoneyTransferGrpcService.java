package com.github.serkanalgl.banking.grpc.moneytransfer.grpc;

import com.github.serkanalgl.banking.grpc.moneytransfer.MoneyTransferRequest;
import com.github.serkanalgl.banking.grpc.moneytransfer.MoneyTransferResponse;
import com.github.serkanalgl.banking.grpc.moneytransfer.MoneyTransferServiceGrpc;
import com.github.serkanalgl.banking.grpc.moneytransfer.service.MoneyTransferService;
import io.grpc.stub.StreamObserver;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class MoneyTransferGrpcService extends MoneyTransferServiceGrpc.MoneyTransferServiceImplBase{

    private final MoneyTransferService moneyTransferService;

    @Override
    public void transfer(MoneyTransferRequest request, StreamObserver<MoneyTransferResponse> responseObserver) {

        MoneyTransferResponse moneyTransferResponse = moneyTransferService.transfer(request);

        responseObserver.onNext(moneyTransferResponse);
        responseObserver.onCompleted();

    }
}
