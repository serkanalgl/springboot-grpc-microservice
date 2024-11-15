package com.github.serkanalgl.banking.grpc.moneytransfer.service;

import com.github.serkanalgl.banking.grpc.moneytransfer.MoneyTransferRequest;
import com.github.serkanalgl.banking.grpc.moneytransfer.MoneyTransferResponse;

public interface MoneyTransferService {

    MoneyTransferResponse transfer(MoneyTransferRequest moneyTransferRequest);

}
