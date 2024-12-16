package com.bank.balance.infrastructure.controller.dto.retrievehistoricalbalance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SourceAccountReponse {
    private Long id;
    private String accountNumber;
    private String holderName;
}
