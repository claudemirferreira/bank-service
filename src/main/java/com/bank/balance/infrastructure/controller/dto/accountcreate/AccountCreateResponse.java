package com.bank.balance.infrastructure.controller.dto.accountcreate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateResponse {
    private Long id;
    private String accountNumber;
    private String holderName;
}
