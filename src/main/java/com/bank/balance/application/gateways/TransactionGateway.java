package com.bank.balance.application.gateways;

import com.bank.balance.domain.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionGateway {

    Transaction depositFunds(Transaction transaction);

    Transaction findById(Long id);

    List<Transaction> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

}
