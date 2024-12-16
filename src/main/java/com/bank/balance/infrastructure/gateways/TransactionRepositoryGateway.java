package com.bank.balance.infrastructure.gateways;

import com.bank.balance.application.gateways.TransactionGateway;
import com.bank.balance.domain.Transaction;
import com.bank.balance.infrastructure.excption.AccountNotFoundException;
import com.bank.balance.infrastructure.persistence.entity.TransactionEntity;
import com.bank.balance.infrastructure.persistence.especification.TransactionSpecification;
import com.bank.balance.infrastructure.persistence.repository.TransactionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionRepositoryGateway implements TransactionGateway {

    private final TransactionRepository transactionRepository;
    private final TransactionEntityMapper transactionEntityMapper;

    public TransactionRepositoryGateway(TransactionRepository transactionRepository,
                                        TransactionEntityMapper transactionEntityMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionEntityMapper = transactionEntityMapper;
    }

    @Override
    public Transaction depositFunds(Transaction transaction) {
        var entity = transactionEntityMapper.toEntity(transaction);
        var entitySaved = transactionRepository.save(entity);
        return transactionEntityMapper.toDomain(entitySaved);
    }

    @Override
    public Transaction findById(Long id) {
        TransactionEntity entity = transactionRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Notfound transaction = " + id));
        return transactionEntityMapper.toDomain(entity);
    }

    @Override
    public List<Transaction> getTransactions(Long sourceAccountId, LocalDate startDate, LocalDate endDate) {
        var list = transactionRepository.findAll(
                TransactionSpecification.byDestinationAccountAndPeriod(sourceAccountId, startDate, endDate),
                Sort.by("operationDate").ascending()
        );
        return transactionEntityMapper.toDomainList(list);
    }
}
