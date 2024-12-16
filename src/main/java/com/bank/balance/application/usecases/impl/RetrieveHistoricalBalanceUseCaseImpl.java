package com.bank.balance.application.usecases.impl;

import com.bank.balance.application.gateways.TransactionGateway;
import com.bank.balance.application.usecases.RetrieveHistoricalBalanceUseCase;
import com.bank.balance.domain.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RetrieveHistoricalBalanceUseCaseImpl implements RetrieveHistoricalBalanceUseCase {

    private final TransactionGateway transactionGateway;

    public RetrieveHistoricalBalanceUseCaseImpl(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    @Override
    public List<Transaction> execute(Long sourceAccountId, LocalDate startDate, LocalDate endDate) {
        return transactionGateway.getTransactions(sourceAccountId, startDate, endDate);
    }

}
