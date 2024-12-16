package com.bank.balance.infrastructure.controller;

import com.bank.balance.application.usecases.CreateAccountUseCase;
import com.bank.balance.domain.Account;
import com.bank.balance.infrastructure.controller.dto.accountcreate.AccountCreateRequest;
import com.bank.balance.infrastructure.controller.dto.accountcreate.AccountCreateResponse;
import com.bank.balance.infrastructure.controller.mapper.AccountDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerTest {

    @Mock
    private CreateAccountUseCase createAccountUseCase;

    @Mock
    private AccountDtoMapper accountDtoMapper;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void testCreateAccount_ValidRequest() throws Exception {
        // Arrange
        AccountCreateRequest request = new AccountCreateRequest("1234567890", "John Doe");
        Account account = new Account(1L, "1234567890", "John Doe", null);
        AccountCreateResponse response = new AccountCreateResponse(1L, "1234567890", "John Doe");

        when(accountDtoMapper.toAccount(any())).thenReturn(account);
        when(createAccountUseCase.execute(account)).thenReturn(account);
        when(accountDtoMapper.toAccountCreateResponse(account)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "accountNumber": "1234567890",
                                    "holderName": "John Doe"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.holderName").value("John Doe"));
    }

    @Test
    void testCreateAccount_InvalidRequest_MissingFields() throws Exception {
        // Act & Assert: Missing `accountNumber` and `holderName`
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "accountNumber": "",
                                    "holderName": ""
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

}
