package com.github.serkanalgl.banking.grpc.account.service;

import com.github.serkanalgl.banking.grpc.account.AccountType;
import com.github.serkanalgl.banking.grpc.account.CreateAccountRequest;
import com.github.serkanalgl.banking.grpc.account.GetAccountRequest;
import com.github.serkanalgl.banking.grpc.account.exception.AccountNotFoundException;
import com.github.serkanalgl.banking.grpc.account.mapper.AccountMapper;
import com.github.serkanalgl.banking.grpc.account.model.AccountStatus;
import com.github.serkanalgl.banking.grpc.account.model.entity.Account;
import com.github.serkanalgl.banking.grpc.account.model.entity.Sequence;
import com.github.serkanalgl.banking.grpc.account.repository.AccountRepository;
import com.github.serkanalgl.banking.grpc.account.service.impl.AccountServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    SequenceService sequenceService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    AccountMapper accountMapper;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    void testCreateAccount(){

        CreateAccountRequest createAccountRequest = CreateAccountRequest.newBuilder()
                .setAccountName("Vadesiz")
                .setAccountType(AccountType.FIXED_DEPOSIT)
                .setBranchId(1)
                .setCustomerId(1)
                .setCurrencyCode("TL")
                .build();

        Sequence sequence = Sequence.builder().sequenceId(1L).build();

        when(sequenceService.create()).thenReturn(sequence);

        Account account = Account.builder()
                .accountNumber("70007" + String.format("%03d",sequenceService.create().getAccountNumber()))
                .accountId(1L)
                .accountName("Vadesiz")
                .accountType(com.github.serkanalgl.banking.grpc.account.model.AccountType.FIXED_DEPOSIT)
                .branchId(1L)
                .availableBalance(BigDecimal.ZERO)
                .customerId(1L)
                .accountStatus(AccountStatus.ACTIVE)
                .build();

        when(accountMapper.createAccountRequestToAccount(createAccountRequest)).thenReturn(account);

        accountService.createAccount(createAccountRequest);
        verify(accountMapper, times(1)).createAccountRequestToAccount(createAccountRequest);
        verify(accountRepository, times(1)).save(account);

    }

    @Test
    void testGetAccountIfAccountNotFound(){

        GetAccountRequest getAccountRequest = GetAccountRequest.newBuilder()
                .setAccountNumber("70007001")
                .build();

        when(accountRepository.findAccountByAccountNumber(getAccountRequest.getAccountNumber()))
                .thenThrow(AccountNotFoundException.class);

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(getAccountRequest));

    }

    @Test
    void testGetAccountSuccess(){

        GetAccountRequest getAccountRequest = GetAccountRequest.newBuilder()
                .setAccountNumber("70007001")
                .build();

        Account account = Account.builder()
                .accountNumber("70007001")
                .accountId(1L)
                .accountName("Vadesiz")
                .accountType(com.github.serkanalgl.banking.grpc.account.model.AccountType.FIXED_DEPOSIT)
                .branchId(1L)
                .availableBalance(BigDecimal.ZERO)
                .customerId(1L)
                .accountStatus(AccountStatus.ACTIVE)
                .build();

        when(accountRepository.findAccountByAccountNumber(getAccountRequest.getAccountNumber())).thenReturn(Optional.of(account));

        accountService.getAccount(getAccountRequest);

        verify(accountMapper, times(1)).accountToAccountResponse(account);


    }

}
