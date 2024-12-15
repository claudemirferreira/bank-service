package com.bank.balance.application.usecases.impl;

import com.bank.balance.application.gateways.AccountGateway;
import com.bank.balance.application.gateways.TransactionGateway;
import com.bank.balance.application.usecases.DepositFundsUseCase;
import com.bank.balance.domain.Account;
import com.bank.balance.domain.Transaction;
import com.bank.balance.domain.enumarations.OperationTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class DepositFundsCaseImpl implements DepositFundsUseCase {
    private final TransactionGateway transactionGateway;
    private final AccountGateway accountGateway;

    public DepositFundsCaseImpl(TransactionGateway transactionGateway,
                                AccountGateway accountGateway) {
        this.transactionGateway = transactionGateway;
        this.accountGateway = accountGateway;
    }

    @Override
    @Transactional
    public Transaction execute(String accountNumber, BigDecimal amount, String description) {
        Account account = this.accountGateway.findByAccountNumber(accountNumber);
        account.setBalance(account.getBalance().add(amount));
        this.accountGateway.save(account);
        var transaction = transactionGateway.depositFunds(
                Transaction
                        .builder()
                        .amount(amount)
                        .destinationAccount(account)
                        .description(description)
                        .operationType(OperationTypeEnum.CREDIT)
                        .build());
        transaction.setDestinationAccount(account);
        return transaction;
    }
}
