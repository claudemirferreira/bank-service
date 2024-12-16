package com.bank.balance.infrastructure.persistence.especification;

import com.bank.balance.infrastructure.persistence.entity.AccountEntity;
import com.bank.balance.infrastructure.persistence.entity.TransactionEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionSpecification {

    public static Specification<TransactionEntity> byDestinationAccount(Long sourceAccountId) {
        return (root, query, criteriaBuilder) -> {
            Join<TransactionEntity, AccountEntity> sourceAccountJoin = root.join("destinationAccount");
            return criteriaBuilder.equal(sourceAccountJoin.get("id"), sourceAccountId);
        };
    }

    public static Specification<TransactionEntity> byOperationDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("operationDate"),
                        startDate.atTime(LocalTime.of(0, 0, 0)),
                        endDate.atTime(LocalTime.of(23, 59, 59)));
    }

    public static Specification<TransactionEntity> byDestinationAccountAndPeriod(Long sourceAccountId, LocalDate startDate, LocalDate endDate) {
        return Specification
                .where(byDestinationAccount(sourceAccountId))
                .and(byOperationDateBetween(startDate, endDate));
    }

}
