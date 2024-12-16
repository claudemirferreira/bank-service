package com.bank.balance.infrastructure.controller.dto.retrievehistoricalbalance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DestinationAccountReponse {
    private Long id;
    private String accountNumber;
    private String holderName;
    private BigDecimal balance;
}
