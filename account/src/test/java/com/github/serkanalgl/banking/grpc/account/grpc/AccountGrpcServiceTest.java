package com.github.serkanalgl.banking.grpc.account.grpc;

import com.github.serkanalgl.banking.grpc.account.*;
import com.github.serkanalgl.banking.grpc.account.service.AccountService;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.github.serkanalgl.banking.grpc.account.AccountType.FIXED_DEPOSIT;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountGrpcServiceTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    AccountGrpcService accountGrpcService;

    @Test
    void testCreateAccount(){

        CreateAccountRequest createAccountRequest = CreateAccountRequest.newBuilder()
                .setAccountName("Vadesiz")
                .setAccountType(FIXED_DEPOSIT)
                .setBranchId(1)
                .setCustomerId(1)
                .setCurrencyCode("TL")
                .build();

        AccountResponse accountResponse = AccountResponse.newBuilder()
                .setAccountNumber("70007001")
                .setAccountId(1L)
                .setAccountName("Vadesiz")
                .setAccountType(FIXED_DEPOSIT)
                .setBranchId(1)
                .setAvailableBalance(0)
                .setCustomerId(1)
                .setAccountStatus(AccountStatus.ACTIVE)
                .build();

        when(accountService.createAccount(createAccountRequest)).thenReturn(accountResponse);

        StreamObserver<AccountResponse> observer = mock(StreamObserver.class);

        accountGrpcService.createAccount(createAccountRequest, observer);

        verify(accountService, times(1)).createAccount(createAccountRequest);
        verify(observer, times(1)).onNext(accountResponse);
        verify(observer, times(1)).onCompleted();

    }

    @Test
    void testGetAccount(){

        GetAccountRequest getAccountRequest = GetAccountRequest.newBuilder()
                .setAccountNumber("70007001")
                .build();

        AccountResponse accountResponse = AccountResponse.newBuilder()
                .setAccountNumber("70007001")
                .setAccountId(1L)
                .setAccountName("Vadesiz")
                .setAccountType(FIXED_DEPOSIT)
                .setBranchId(1)
                .setAvailableBalance(0)
                .setCustomerId(1)
                .setAccountStatus(AccountStatus.ACTIVE)
                .build();

        when(accountService.getAccount(getAccountRequest)).thenReturn(accountResponse);

        StreamObserver<AccountResponse> observer = mock(StreamObserver.class);

        accountGrpcService.getAccount(getAccountRequest, observer);

        verify(accountService, times(1)).getAccount(getAccountRequest);
        verify(observer, times(1)).onNext(accountResponse);
        verify(observer, times(1)).onCompleted();

    }

}
