package com.bank.balance.application.usecases.impl;

import com.bank.balance.application.gateways.AccountGateway;
import com.bank.balance.application.usecases.CreateAccountUseCase;
import com.bank.balance.domain.Account;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    private final AccountGateway accountGateway;

    public CreateAccountUseCaseImpl(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    @Override
    public Account execute(Account account) {
        return accountGateway.save(account);
    }

}
