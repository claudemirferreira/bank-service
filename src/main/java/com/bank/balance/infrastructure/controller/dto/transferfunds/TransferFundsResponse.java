package com.bank.balance.infrastructure.controller.dto.transferfunds;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferFundsResponse {
    private String sourceAccount;
    private String sourceHolderName;
    private String destinationAccount;
    private String destinationHolderName;
    private BigDecimal amount;
    private String description;
    private LocalDateTime timestamp;
}
