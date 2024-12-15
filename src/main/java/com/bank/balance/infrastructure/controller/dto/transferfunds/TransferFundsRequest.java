package com.bank.balance.infrastructure.controller.dto.transferfunds;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferFundsRequest {
    @NotNull(message = "accountNumber is required")
    @Size(min = 1, max = 20, message = "sourceAccount must be between 10 and 20 characters")
    private String sourceAccount;
    @NotNull(message = "destinationAccount is required")
    @Size(min = 1, max = 20, message = "destinationAccount must be between 10 and 20 characters")
    private String destinationAccount;
//    @NotNull(message = "amount is required")
//    @Size(min = 1, max = 20, message = "amount must be between 10 and 20 characters")
    private BigDecimal amount;
    @NotNull(message = "description is required")
    @Size(min = 1, max = 20, message = "description must be between 10 and 20 characters")
    private String description;
}
