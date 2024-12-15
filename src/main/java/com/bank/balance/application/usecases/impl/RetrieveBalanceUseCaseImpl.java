package com.bank.balance.application.usecases.impl;

import com.bank.balance.application.gateways.AccountGateway;
import com.bank.balance.application.usecases.RetrieveBalanceUseCase;
import com.bank.balance.domain.Account;
import org.springframework.stereotype.Service;

@Service
public class RetrieveBalanceUseCaseImpl implements RetrieveBalanceUseCase {

    private final AccountGateway accountGateway;

    public RetrieveBalanceUseCaseImpl(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    @Override
    public Account execute(String accountNumber) {
        return accountGateway.findByAccountNumber(accountNumber);
    }

}
