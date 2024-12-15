package com.bank.balance.application.usecases;

import com.bank.balance.domain.Transaction;

import java.math.BigDecimal;

public interface WithdrawFundsUseCase {
    Transaction execute(String accountNumber, BigDecimal amount, String description);
}
