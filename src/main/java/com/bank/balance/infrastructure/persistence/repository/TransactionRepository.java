package com.bank.balance.infrastructure.persistence.repository;

import com.bank.balance.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findBySourceAccountIdOrDestinationAccountId(Long sourceAccountId, Long destinationAccountId);
    List<TransactionEntity> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
}

