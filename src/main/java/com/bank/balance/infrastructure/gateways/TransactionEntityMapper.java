package com.bank.balance.infrastructure.gateways;

import com.bank.balance.domain.Transaction;
import com.bank.balance.infrastructure.persistence.entity.AccountEntity;
import com.bank.balance.infrastructure.persistence.entity.TransactionEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransactionEntityMapper {

    private final ModelMapper modelMapper;

    public TransactionEntityMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransactionEntity toEntity(Transaction transaction) {
        return TransactionEntity
                .builder()
                .id(transaction.getId())
                .destinationAccount(AccountEntity.builder().id(transaction.getDestinationAccount().getId()).build())
                .sourceAccount(Objects.nonNull(transaction.getSourceAccount()) ?
                        AccountEntity.builder().id(transaction.getSourceAccount().getId())
                                .holderName(transaction.getSourceAccount().getHolderName())
                                .balance(transaction.getSourceAccount().getBalance())
                                .accountNumber(transaction.getSourceAccount().getAccountNumber())
                                .build() : null)
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .operationType(transaction.getOperationType())
                .build();
    }

    public Transaction toDomain(TransactionEntity entity) {
        return modelMapper.map(entity, Transaction.class);
    }

    public List<Transaction> toDomainList(List<TransactionEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

}
