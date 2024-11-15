package service;

import com.github.serkanalgl.banking.grpc.account.AccountResponse;
import com.github.serkanalgl.banking.grpc.account.AccountServiceGrpc;
import com.github.serkanalgl.banking.grpc.account.AccountStatus;
import com.github.serkanalgl.banking.grpc.account.AccountType;
import com.github.serkanalgl.banking.grpc.account.GetAccountRequest;
import com.github.serkanalgl.banking.grpc.moneytransfer.MoneyTransferRequest;
import com.github.serkanalgl.banking.grpc.moneytransfer.MoneyTransferResponse;
import com.github.serkanalgl.banking.grpc.moneytransfer.service.impl.MoneyTransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MoneyTransferServiceTest {

    @Mock
    AccountServiceGrpc.AccountServiceBlockingStub accountServiceStub;

    @InjectMocks
    MoneyTransferServiceImpl moneyTransferService;

    @Test
    void transferTestSuccess(){

        GetAccountRequest getAccountRequestForFromAccount = GetAccountRequest.newBuilder()
                .setAccountNumber("70007001")
                .build();

        GetAccountRequest getAccountRequestForToAccount = GetAccountRequest.newBuilder()
                .setAccountNumber("70007002")
                .build();


        AccountResponse fromAccount = AccountResponse.newBuilder()
                .setAccountNumber("70007001")
                .setAccountId(1L)
                .setAccountName("Vadesiz")
                .setAccountType(AccountType.FIXED_DEPOSIT)
                .setBranchId(1)
                .setAvailableBalance(0)
                .setCustomerId(1)
                .setAccountStatus(AccountStatus.ACTIVE)
                .build();

        AccountResponse toAccount = AccountResponse.newBuilder()
                .setAccountNumber("70007002")
                .setAccountId(2L)
                .setAccountName("Vadesiz")
                .setAccountType(AccountType.FIXED_DEPOSIT)
                .setBranchId(1)
                .setAvailableBalance(0)
                .setCustomerId(1)
                .setAccountStatus(AccountStatus.ACTIVE)
                .build();

        when(accountServiceStub.getAccount(getAccountRequestForFromAccount)).thenReturn(fromAccount);
        when(accountServiceStub.getAccount(getAccountRequestForToAccount)).thenReturn(toAccount);

        MoneyTransferRequest moneyTransferRequest = MoneyTransferRequest.newBuilder()
                .setFromAccount("70007001")
                .setToAccount("70007002")
                .setAmount(10)
                .build();

        MoneyTransferResponse moneyTransferResponse = moneyTransferService.transfer(moneyTransferRequest);

        assertEquals(moneyTransferResponse.getFromAccount(), moneyTransferRequest.getFromAccount());
        assertEquals(moneyTransferResponse.getToAccount(), moneyTransferRequest.getToAccount());
        assertEquals(moneyTransferResponse.getAmount(), moneyTransferRequest.getAmount());
        assertNotNull(moneyTransferResponse.getTransactionReference());
        assertNotEquals(moneyTransferResponse.getTransferAt(), 0);

    }

}
