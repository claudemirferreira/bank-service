package com.bank.balance.application.usecases.impl;

import com.bank.balance.application.gateways.AccountGateway;
import com.bank.balance.application.gateways.TransactionGateway;
import com.bank.balance.application.usecases.TransferFundsUseCase;
import com.bank.balance.domain.Account;
import com.bank.balance.domain.Transaction;
import com.bank.balance.infrastructure.excption.InsufficientBalanceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bank.balance.domain.enumarations.OperationTypeEnum;

import java.math.BigDecimal;

@Slf4j
@Service
public class TransferFundsUseCaseImpl implements TransferFundsUseCase {

    private final AccountGateway accountGateway;
    private final TransactionGateway transactionGateway;

    public TransferFundsUseCaseImpl(AccountGateway accountGateway, TransactionGateway transactionGateway) {
        this.accountGateway = accountGateway;
        this.transactionGateway = transactionGateway;
    }

    @Override
    public Transaction execute(Transaction transaction) {
        Account sourceAccount = this.accountGateway.findByAccountNumber(transaction.getSourceAccount().getAccountNumber());
        validAmount(sourceAccount, transaction.getAmount());
        Account destinationAccount = this.accountGateway.findByAccountNumber(transaction.getDestinationAccount().getAccountNumber());
        var result = transferFunds(sourceAccount, destinationAccount, transaction);
        return transactionGateway.findById(result.getId());
    }

    @Transactional
    public Transaction transferFunds(Account sourceAccount, Account destinationAccount, Transaction transaction) {
        destinationAccount.setBalance(destinationAccount.getBalance().add(transaction.getAmount()));
        this.accountGateway.save(destinationAccount);

        destinationAccount.setBalance(sourceAccount.getBalance().subtract(transaction.getAmount()));
        this.accountGateway.save(destinationAccount);

        var transactionCredit = transactionGateway.depositFunds(
                Transaction
                        .builder()
                        .amount(transaction.getAmount())
                        .destinationAccount(destinationAccount)
                        .sourceAccount(sourceAccount)
                        .description(transaction.getDescription())
                        .operationType(OperationTypeEnum.CREDIT)
                        .build());
        log.info("{}", transactionCredit);
        var transactionDebit = transactionGateway.depositFunds(
                Transaction
                        .builder()
                        .amount(transaction.getAmount())
                        .destinationAccount(sourceAccount)
                        .sourceAccount(destinationAccount)
                        .description(transaction.getDescription())
                        .operationType(OperationTypeEnum.DEBIT)
                        .build());
        log.info("{}", transactionDebit);

        return transactionDebit;
    }

    private void validAmount(Account account, BigDecimal amout){
        if (account.getBalance().compareTo(amout) < 0){
            throw new InsufficientBalanceException("Insufficient Balance");
        }
    }

}
