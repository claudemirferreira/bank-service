package com.bank.balance.application.usecases.impl;

import com.bank.balance.application.gateways.AccountGateway;
import com.bank.balance.domain.Account;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CreateAccountUseCaseImplTest {

    @Mock
    private AccountGateway accountGateway;

    @InjectMocks
    private CreateAccountUseCaseImpl createAccountUseCase;

    public CreateAccountUseCaseImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_ReturnsSavedAccount() {
        // Arrange
        Account accountToSave = new Account(1L, "1234567890", "John Doe", null);
        Account savedAccount = new Account(1L, "1234567890", "John Doe", null);
        when(accountGateway.save(accountToSave)).thenReturn(savedAccount);
        Account result = createAccountUseCase.execute(accountToSave);

        // Assert
        assertNotNull(result);
        assertEquals(savedAccount.getId(), result.getId());
        assertEquals(savedAccount.getAccountNumber(), result.getAccountNumber());
        assertEquals(savedAccount.getHolderName(), result.getHolderName());

        verify(accountGateway, times(1)).save(accountToSave);
    }
}
