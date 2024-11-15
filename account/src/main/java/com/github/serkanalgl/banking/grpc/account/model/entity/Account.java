package com.github.serkanalgl.banking.grpc.account.model.entity;

import com.github.serkanalgl.banking.grpc.account.model.AccountStatus;
import com.github.serkanalgl.banking.grpc.account.model.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String accountName;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private BigDecimal availableBalance;

    private Long customerId;

    private Long branchId;

    @CreationTimestamp
    private LocalDate createdAt;

}
