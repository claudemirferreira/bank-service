package com.bank.balance.application.usecases;

import com.bank.balance.domain.Transaction;

public interface TransferFundsUseCase {
    Transaction execute(Transaction transaction);
}
