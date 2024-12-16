package com.bank.balance.infrastructure.controller.dto.retrievehistoricalbalance;

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
public class RetrieveHistoricalBalanceReponse {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime operationDate;
    private String description;
    private String operationType;
    private SourceAccountReponse sourceAccount;
    private DestinationAccountReponse destinationAccount;
}
