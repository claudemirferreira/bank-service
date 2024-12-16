package com.bank.balance.application.usecases.impl;

import com.bank.balance.application.gateways.AccountGateway;
import com.bank.balance.application.gateways.TransactionGateway;
import com.bank.balance.domain.Account;
import com.bank.balance.domain.Transaction;
import com.bank.balance.domain.enumarations.OperationTypeEnum;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class DepositFundsCaseImplTest {

    @Mock
    private TransactionGateway transactionGateway;

    @Mock
    private AccountGateway accountGateway;

    @InjectMocks
    private DepositFundsCaseImpl depositFundsUseCase;

    public DepositFundsCaseImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_DepositFundsSuccessfully() {
        // Arrange
        String accountNumber = "1234567890";
        BigDecimal amount = new BigDecimal("100.00");
        String description = "Deposit Test";

        Account account = new Account(1L, accountNumber, "John Doe", new BigDecimal("500.00"));
        Transaction expectedTransaction = Transaction
                .builder()
                .destinationAccount(Account.builder().id(1L).build())
                .amount(new BigDecimal("500.00"))
                .description("Deposit")
                .operationType(OperationTypeEnum.CREDIT)
                .build();

        when(accountGateway.findByAccountNumber(accountNumber)).thenReturn(account);
        when(transactionGateway.depositFunds(any(Transaction.class))).thenReturn(expectedTransaction);

        // Act
        Transaction result = depositFundsUseCase.execute(accountNumber, amount, description);

        // Assert
        assertNotNull(result);
        assertEquals(expectedTransaction.getId(), result.getId());
        assertEquals(expectedTransaction.getAmount(), result.getAmount());
        assertEquals(expectedTransaction.getDescription(), result.getDescription());
        assertEquals(OperationTypeEnum.CREDIT, result.getOperationType());
        assertEquals(account, result.getDestinationAccount());

        verify(accountGateway, times(1)).findByAccountNumber(accountNumber);
        verify(accountGateway, times(1)).save(account);
        verify(transactionGateway, times(1)).depositFunds(any(Transaction.class));
    }
}
