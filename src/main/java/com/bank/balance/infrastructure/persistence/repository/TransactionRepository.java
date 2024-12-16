package com.bank.balance.infrastructure.persistence.repository;

import com.bank.balance.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>, JpaSpecificationExecutor<TransactionEntity> {
    List<TransactionEntity> findBySourceAccountIdOrDestinationAccountId(Long sourceAccountId, Long destinationAccountId);
}

