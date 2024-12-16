package com.bank.balance.application.usecases.impl;

import com.bank.balance.application.gateways.AccountGateway;
import com.bank.balance.domain.Account;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RetrieveBalanceUseCaseImplTest {

    @Mock
    private AccountGateway accountGateway;

    @InjectMocks
    private RetrieveBalanceUseCaseImpl retrieveBalanceUseCase;

    public RetrieveBalanceUseCaseImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_RetrieveBalanceSuccessfully() {
        // Arrange
        String accountNumber = "1234567890";
        Account expectedAccount = new Account(1L, accountNumber, "John Doe", new BigDecimal("500.00"));

        when(accountGateway.findByAccountNumber(accountNumber)).thenReturn(expectedAccount);

        // Act
        Account result = retrieveBalanceUseCase.execute(accountNumber);

        // Assert
        assertNotNull(result);
        assertEquals(expectedAccount.getId(), result.getId());
        assertEquals(expectedAccount.getAccountNumber(), result.getAccountNumber());
        assertEquals(expectedAccount.getHolderName(), result.getHolderName());
        assertEquals(expectedAccount.getBalance(), result.getBalance());

        verify(accountGateway, times(1)).findByAccountNumber(accountNumber);
    }

    @Test
    void testExecute_AccountNotFound() {
        // Arrange
        String accountNumber = "non-existing-account";

        when(accountGateway.findByAccountNumber(accountNumber)).thenReturn(null);

        // Act
        Account result = retrieveBalanceUseCase.execute(accountNumber);

        // Assert
        assertNull(result);
        verify(accountGateway, times(1)).findByAccountNumber(accountNumber);
    }
}
