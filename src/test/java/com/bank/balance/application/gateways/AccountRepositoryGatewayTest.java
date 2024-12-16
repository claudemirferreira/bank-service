package com.bank.balance.application.gateways;

import com.bank.balance.domain.Account;
import com.bank.balance.infrastructure.excption.AccountNotFoundException;
import com.bank.balance.infrastructure.gateways.AccountEntityMapper;
import com.bank.balance.infrastructure.gateways.AccountRepositoryGateway;
import com.bank.balance.infrastructure.persistence.entity.AccountEntity;
import com.bank.balance.infrastructure.persistence.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountRepositoryGatewayTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountEntityMapper accountEntityMapper;

    @InjectMocks
    private AccountRepositoryGateway accountRepositoryGateway;

    public AccountRepositoryGatewayTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAccount_Success() {
        // Arrange
        Account account = Account.builder()
                .id(1L)
                .accountNumber("123456789")
                .holderName("John Doe")
                .balance(new BigDecimal("500.00"))
                .build();

        AccountEntity entity = new AccountEntity();
        when(accountEntityMapper.toEntity(account)).thenReturn(entity);
        when(accountRepository.save(entity)).thenReturn(entity);
        when(accountEntityMapper.toDomain(entity)).thenReturn(account);

        // Act
        Account result = accountRepositoryGateway.save(account);

        // Assert
        assertNotNull(result);
        assertEquals(account.getId(), result.getId());
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
        assertEquals(account.getHolderName(), result.getHolderName());
        assertEquals(account.getBalance(), result.getBalance());

        verify(accountRepository, times(1)).save(entity);
    }

    @Test
    void testFindByAccountNumber_Success() {
        // Arrange
        String accountNumber = "123456789";
        AccountEntity entity = new AccountEntity();
        entity.setAccountNumber(accountNumber);
        Account account = Account.builder()
                .id(1L)
                .accountNumber(accountNumber)
                .holderName("John Doe")
                .balance(new BigDecimal("500.00"))
                .build();

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(entity));
        when(accountEntityMapper.toDomain(entity)).thenReturn(account);

        // Act
        Account result = accountRepositoryGateway.findByAccountNumber(accountNumber);

        // Assert
        assertNotNull(result);
        assertEquals(accountNumber, result.getAccountNumber());

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
    }

    @Test
    void testFindByAccountNumber_AccountNotFound() {
        // Arrange
        String accountNumber = "123456789";
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(AccountNotFoundException.class, () ->
                accountRepositoryGateway.findByAccountNumber(accountNumber));

        assertEquals("Notfound accountNumber = " + accountNumber, exception.getMessage());

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
    }
}
