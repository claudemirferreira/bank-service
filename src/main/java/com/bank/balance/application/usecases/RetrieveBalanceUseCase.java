package com.bank.balance.application.usecases;

import com.bank.balance.domain.Account;

public interface RetrieveBalanceUseCase {
    Account execute(String accountNumber);
}
