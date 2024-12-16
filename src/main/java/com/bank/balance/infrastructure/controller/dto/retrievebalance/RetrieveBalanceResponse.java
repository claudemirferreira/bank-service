package com.bank.balance.infrastructure.controller.dto.retrievebalance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveBalanceResponse {
    private String accountNumber;
    private String holderName;
    private BigDecimal balance;
}
