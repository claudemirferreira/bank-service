package com.bank.balance.domain;

import com.bank.balance.domain.enumarations.OperationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long id;
    private Account sourceAccount;
    private Account destinationAccount;
    private BigDecimal amount;
    private LocalDateTime operationDate;
    private String description;
    private OperationTypeEnum operationType;
}

