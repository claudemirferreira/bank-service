package com.bank.balance.application.gateways;

import com.bank.balance.domain.Account;
import com.bank.balance.infrastructure.gateways.AccountEntityMapper;
import com.bank.balance.infrastructure.persistence.entity.AccountEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AccountEntityMapperTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccountEntityMapper accountEntityMapper;

    public AccountEntityMapperTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToEntity_Success() {
        // Arrange
        Account account = Account.builder()
                .id(1L)
                .accountNumber("123456789")
                .holderName("John Doe")
                .balance(new BigDecimal("500.00"))
                .build();

        AccountEntity expectedEntity = new AccountEntity();
        when(modelMapper.map(account, AccountEntity.class)).thenReturn(expectedEntity);

        // Act
        AccountEntity result = accountEntityMapper.toEntity(account);

        // Assert
        assertNotNull(result);
        assertEquals(expectedEntity, result);
        assertEquals(new BigDecimal("0"), result.getBalance()); // balance should be initialized to 0 if null

        verify(modelMapper, times(1)).map(account, AccountEntity.class);
    }

    @Test
    void testToDomain_Success() {
        // Arrange
        AccountEntity entity = new AccountEntity();
        entity.setId(1L);
        entity.setAccountNumber("123456789");
        entity.setHolderName("John Doe");
        entity.setBalance(new BigDecimal("500.00"));

        Account expectedAccount = Account.builder()
                .id(1L)
                .accountNumber("123456789")
                .holderName("John Doe")
                .balance(new BigDecimal("500.00"))
                .build();

        when(modelMapper.map(entity, Account.class)).thenReturn(expectedAccount);

        // Act
        Account result = accountEntityMapper.toDomain(entity);

        // Assert
        assertNotNull(result);
        assertEquals(expectedAccount, result);

        verify(modelMapper, times(1)).map(entity, Account.class);
    }

    @Test
    void testToEntity_AccountWithNullBalance() {
        // Arrange
        Account account = Account.builder()
                .id(1L)
                .accountNumber("123456789")
                .holderName("John Doe")
                .build();

        AccountEntity expectedEntity = new AccountEntity();
        expectedEntity.setBalance(new BigDecimal("0"));

        when(modelMapper.map(account, AccountEntity.class)).thenReturn(expectedEntity);

        // Act
        AccountEntity result = accountEntityMapper.toEntity(account);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("0"), result.getBalance());

        verify(modelMapper, times(1)).map(account, AccountEntity.class);
    }
}
