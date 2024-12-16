package com.bank.balance.infrastructure.controller.mapper;

import com.bank.balance.domain.Account;
import com.bank.balance.domain.Transaction;
import com.bank.balance.infrastructure.controller.dto.depositfunds.DepositFundsResponse;
import com.bank.balance.infrastructure.controller.dto.retrievehistoricalbalance.DestinationAccountReponse;
import com.bank.balance.infrastructure.controller.dto.retrievehistoricalbalance.RetrieveHistoricalBalanceReponse;
import com.bank.balance.infrastructure.controller.dto.retrievehistoricalbalance.SourceAccountReponse;
import com.bank.balance.infrastructure.controller.dto.transferfunds.TransferFundsRequest;
import com.bank.balance.infrastructure.controller.dto.transferfunds.TransferFundsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransactionDtoMapper {

    public DepositFundsResponse toDepositFundsResponse(Transaction transaction) {

        return DepositFundsResponse
                .builder()
                .accountNumber(transaction.getDestinationAccount().getAccountNumber())
                .holderName(transaction.getDestinationAccount().getHolderName())
                .balance(transaction.getDestinationAccount().getBalance())
                .amount(transaction.getAmount())
                .operationDate(transaction.getOperationDate())
                .description(transaction.getDescription())
                .build();
    }

    public Transaction toTransaction(TransferFundsRequest transferFundsRequest) {
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
                .operationDate(transaction.getOperationDate())
                .sourceAccount(transaction.getSourceAccount().getAccountNumber())
                .destinationAccount(transaction.getDestinationAccount().getAccountNumber())
                .sourceHolderName(transaction.getSourceAccount().getHolderName())
                .destinationHolderName(transaction.getDestinationAccount().getHolderName())
                .description(transaction.getDestinationAccount().getHolderName())
                .amount(transaction.getAmount())
                .build();
    }

    public RetrieveHistoricalBalanceReponse toRetrieveHistoricalBalanceReponse(Transaction transaction) {
        return RetrieveHistoricalBalanceReponse
                .builder()
                .id(transaction.getId())
                .operationType(transaction.getOperationType().getDescription())
                .sourceAccount(Objects.nonNull(transaction.getSourceAccount()) ?
                        SourceAccountReponse
                                .builder()
                                .accountNumber(transaction.getSourceAccount().getAccountNumber())
                                .holderName(transaction.getSourceAccount().getHolderName())
                                .id(transaction.getSourceAccount().getId())
                                .build() : null
                )
                .destinationAccount(
                        Objects.nonNull(transaction.getDestinationAccount()) ?
                                DestinationAccountReponse
                                        .builder()
                                        .accountNumber(transaction.getDestinationAccount().getAccountNumber())
                                        .holderName(transaction.getDestinationAccount().getHolderName())
                                        .id(transaction.getDestinationAccount().getId())
                                        .balance(transaction.getDestinationAccount().getBalance())
                                        .build() : null
                )
                .description(transaction.getDestinationAccount().getHolderName())
                .amount(transaction.getAmount())
                .operationDate(transaction.getOperationDate())
                .build();
    }

    public List<RetrieveHistoricalBalanceReponse> toRetrieveHistoricalBalanceReponse(List<Transaction> list) {
        return list.stream()
                .map(this::toRetrieveHistoricalBalanceReponse)
                .collect(Collectors.toList());
    }

}
