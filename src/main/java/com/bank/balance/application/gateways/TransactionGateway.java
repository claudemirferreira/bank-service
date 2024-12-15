package com.bank.balance.application.gateways;

import com.bank.balance.domain.Transaction;

public interface TransactionGateway {

    Transaction depositFunds(Transaction transaction);

    Transaction findById(Long id);

}
