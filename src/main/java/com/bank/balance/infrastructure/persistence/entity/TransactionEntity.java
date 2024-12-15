package com.bank.balance.infrastructure.persistence.entity;

import com.bank.balance.domain.enumarations.OperationTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id")
    private AccountEntity sourceAccount;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_account_id", referencedColumnName = "account_id", nullable = false)
    private AccountEntity destinationAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    private String description;

    @Enumerated(EnumType.STRING) // Salva o nome da constante no banco
    @Column(name = "operation_type", nullable = false, length = 10)
    private OperationTypeEnum operationType;

}
