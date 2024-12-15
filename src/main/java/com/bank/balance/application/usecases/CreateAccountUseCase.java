package com.bank.balance.application.usecases;

import com.bank.balance.domain.Account;

public interface CreateAccountUseCase {
    Account execute(Account account);
}
