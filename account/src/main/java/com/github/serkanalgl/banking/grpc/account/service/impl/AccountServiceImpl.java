package com.github.serkanalgl.banking.grpc.account.service.impl;

import com.github.serkanalgl.banking.grpc.account.AccountResponse;
import com.github.serkanalgl.banking.grpc.account.CreateAccountRequest;
import com.github.serkanalgl.banking.grpc.account.GetAccountRequest;
import com.github.serkanalgl.banking.grpc.account.exception.AccountNotFoundException;
import com.github.serkanalgl.banking.grpc.account.mapper.AccountMapper;
import com.github.serkanalgl.banking.grpc.account.model.AccountStatus;
import com.github.serkanalgl.banking.grpc.account.model.entity.Account;
import com.github.serkanalgl.banking.grpc.account.repository.AccountRepository;
import com.github.serkanalgl.banking.grpc.account.repository.SequenceRepository;
import com.github.serkanalgl.banking.grpc.account.service.AccountService;
import com.github.serkanalgl.banking.grpc.account.service.SequenceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private static final String ACCOUNT_PREFIX = "70007";

    private final SequenceService sequenceService;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    @Override
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest createAccountRequest) {

        Account account = accountMapper.createAccountRequestToAccount(createAccountRequest);
        account.setAccountNumber(ACCOUNT_PREFIX + String.format("%03d",sequenceService.create().getAccountNumber()));
        account.setBranchId(1L);
        account.setCustomerId(1L);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAvailableBalance(BigDecimal.valueOf(0));

        account = accountRepository.save(account);

        log.info("Account save: {}", account);

        return accountMapper.accountToAccountResponse(account);
    }

    @Override
    public AccountResponse getAccount(GetAccountRequest getAccountRequest) {

        Account account = accountRepository.findAccountByAccountNumber(getAccountRequest.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found given with account number"));

        return accountMapper.accountToAccountResponse(account);
    }
}
