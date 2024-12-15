package com.bank.balance.infrastructure.controller.dto.accountcreate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequest {

    @NotNull(message = "accountNumber is required")
    @Size(min = 1, max = 20, message = "accountNumber must be between 10 and 20 characters")
    String accountNumber;

    @NotNull(message = "holderName is required")
    @Size(min = 3, max = 50, message = "holderName must be between 3 and 50 characters")
    String holderName;
}
