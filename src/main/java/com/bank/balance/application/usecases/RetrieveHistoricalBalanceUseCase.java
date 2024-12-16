package com.bank.balance.application.usecases;

import com.bank.balance.domain.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface RetrieveHistoricalBalanceUseCase {
    List<Transaction> execute(Long sourceAccountId, LocalDate startDate, LocalDate endDate);
}
