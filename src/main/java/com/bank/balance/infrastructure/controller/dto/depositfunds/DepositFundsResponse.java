package com.bank.balance.infrastructure.controller.dto.depositfunds;

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
public class DepositFundsResponse {
    private String holderName;
    private String accountNumber;
    private BigDecimal balance;
    private BigDecimal amount;
    private String description;
    private LocalDateTime timestamp;
}
