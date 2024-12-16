package com.bank.balance.application.gateways;

import com.bank.balance.domain.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionGateway {

    Transaction depositFunds(Transaction transaction);

    Transaction findById(Long id);

    List<Transaction> getTransactions(Long sourceAccountId, LocalDate startDate, LocalDate endDate);

}
