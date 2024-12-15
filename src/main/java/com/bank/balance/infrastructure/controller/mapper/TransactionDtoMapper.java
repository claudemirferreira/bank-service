package com.bank.balance.infrastructure.controller.mapper;

import com.bank.balance.domain.Account;
import com.bank.balance.domain.Transaction;
import com.bank.balance.infrastructure.controller.dto.depositfunds.DepositFundsResponse;
import com.bank.balance.infrastructure.controller.dto.transferfunds.TransferFundsRequest;
import com.bank.balance.infrastructure.controller.dto.transferfunds.TransferFundsResponse;
import org.springframework.stereotype.Service;

@Service
public class TransactionDtoMapper {

    public DepositFundsResponse toDepositFundsResponse(Transaction transaction) {

        return DepositFundsResponse
                .builder()
                .accountNumber(transaction.getDestinationAccount().getAccountNumber())
                .holderName(transaction.getDestinationAccount().getHolderName())
                .balance(transaction.getDestinationAccount().getBalance())
                .amount(transaction.getAmount())
                .timestamp(transaction.getTimestamp())
                .description(transaction.getDescription())
                .build();
    }

    public Transaction toTransaction(TransferFundsRequest transferFundsRequest){
        return Transaction
                .builder()
                .sourceAccount(Account.builder().accountNumber(transferFundsRequest.getSourceAccount()).build())
                .destinationAccount(Account.builder().accountNumber(transferFundsRequest.getDestinationAccount()).build())
                .description(transferFundsRequest.getDescription())
                .amount(transferFundsRequest.getAmount())
                .build();
    }

    public TransferFundsResponse toTransferFundsResponse(Transaction transaction) {
        return TransferFundsResponse
                .builder()
                .timestamp(transaction.getTimestamp())
                .sourceAccount(transaction.getSourceAccount().getAccountNumber())
                .destinationAccount(transaction.getDestinationAccount().getAccountNumber())
                .sourceHolderName(transaction.getSourceAccount().getHolderName())
                .destinationHolderName(transaction.getDestinationAccount().getHolderName())
                .description(transaction.getDestinationAccount().getHolderName())
                .amount(transaction.getAmount())
                .build();
    }

}
