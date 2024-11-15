package com.github.serkanalgl.banking.grpc.account.repository;

import com.github.serkanalgl.banking.grpc.account.model.AccountType;
import com.github.serkanalgl.banking.grpc.account.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByAccountNumber(String accountNumber);
}
