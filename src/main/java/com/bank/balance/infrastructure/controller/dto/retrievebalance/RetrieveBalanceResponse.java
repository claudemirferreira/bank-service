package com.bank.balance.infrastructure.controller.dto.retrievebalance;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RetrieveBalanceResponse {
    private String accountNumber;
    private String holderName;
    private BigDecimal balance;
}
