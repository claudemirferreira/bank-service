package com.bank.balance.application.usecases.impl;

import com.bank.balance.application.gateways.TransactionGateway;
import com.bank.balance.domain.Account;
import com.bank.balance.domain.Transaction;
import com.bank.balance.domain.enumarations.OperationTypeEnum;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RetrieveHistoricalBalanceUseCaseImplTest {

    @Mock
    private TransactionGateway transactionGateway;

    @InjectMocks
    private RetrieveHistoricalBalanceUseCaseImpl retrieveHistoricalBalanceUseCase;

    public RetrieveHistoricalBalanceUseCaseImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_RetrieveHistoricalBalanceSuccessfully() {
        // Arrange
        Long sourceAccountId = 1L;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        Transaction transaction1 = Transaction
                .builder()
                .id(1L)
                .operationType(OperationTypeEnum.CREDIT)
                .destinationAccount(Account.builder().id(1L).build())
                .sourceAccount(Account.builder().id(2L).build())
                .description("CREDIT")
                .build();
        // Transaction(1L, sourceAccountId, new BigDecimal("100.00"), "Test Transaction 1", LocalDate.now(), OperationTypeEnum.CREDIT);
        Transaction transaction2 = Transaction
                .builder()
                .id(2L)
                .operationType(OperationTypeEnum.DEBIT)
                .destinationAccount(Account.builder().id(1L).build())
                .sourceAccount(Account.builder().id(2L).build())
                .description("DEBIT")
                .build();
        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);
        when(transactionGateway.getTransactions(sourceAccountId, startDate, endDate)).thenReturn(expectedTransactions);
        List<Transaction> result = retrieveHistoricalBalanceUseCase.execute(sourceAccountId, startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(expectedTransactions));

        verify(transactionGateway, times(1)).getTransactions(sourceAccountId, startDate, endDate);
    }

    @Test
    void testExecute_NoTransactionsFound() {
        // Arrange
        Long sourceAccountId = 1L;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        when(transactionGateway.getTransactions(sourceAccountId, startDate, endDate)).thenReturn(List.of());

        // Act
        List<Transaction> result = retrieveHistoricalBalanceUseCase.execute(sourceAccountId, startDate, endDate);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(transactionGateway, times(1)).getTransactions(sourceAccountId, startDate, endDate);
    }
}
