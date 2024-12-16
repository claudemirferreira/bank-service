package com.bank.balance.infrastructure.controller;

import com.bank.balance.application.usecases.DepositFundsUseCase;
import com.bank.balance.application.usecases.RetrieveHistoricalBalanceUseCase;
import com.bank.balance.application.usecases.TransferFundsUseCase;
import com.bank.balance.domain.Account;
import com.bank.balance.domain.Transaction;
import com.bank.balance.infrastructure.controller.dto.depositfunds.DepositFundsRequest;
import com.bank.balance.infrastructure.controller.dto.depositfunds.DepositFundsResponse;
import com.bank.balance.infrastructure.controller.dto.retrievehistoricalbalance.RetrieveHistoricalBalanceReponse;
import com.bank.balance.infrastructure.controller.dto.transferfunds.TransferFundsRequest;
import com.bank.balance.infrastructure.controller.dto.transferfunds.TransferFundsResponse;
import com.bank.balance.infrastructure.controller.mapper.TransactionDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionControllerTest {

    @Mock
    private DepositFundsUseCase depositFundsUseCase;

    @Mock
    private TransferFundsUseCase transferFundsUseCase;

    @Mock
    private RetrieveHistoricalBalanceUseCase retrieveHistoricalBalanceUseCase;

    @Mock
    private TransactionDtoMapper transactionDtoMapper;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    private final String ACCOUNT_NUMBER = "1";
    private final LocalDateTime OPERATION_DATE = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void testDepositFunds() throws Exception {
        // Arrange
        DepositFundsRequest request = new DepositFundsRequest("12345", new BigDecimal("1000"), "Deposit");
        Transaction transaction = new Transaction();
        DepositFundsResponse response = DepositFundsResponse
                .builder()
                .accountNumber(ACCOUNT_NUMBER)
                .amount(new BigDecimal("1000"))
                .description("Deposit")
                .build();
        when(depositFundsUseCase.execute(any(), any(), any())).thenReturn(transaction);
        when(transactionDtoMapper.toDepositFundsResponse(transaction)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/transactions/deposit-funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\": \"12345\", \"amount\": 1000, \"description\": \"Deposit\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(ACCOUNT_NUMBER))
                .andExpect(jsonPath("$.amount").value(1000))
                .andExpect(jsonPath("$.description").value("Deposit"));
    }

    @Test
    void testRetrieveHistoricalBalance() throws Exception {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        RetrieveHistoricalBalanceReponse response = RetrieveHistoricalBalanceReponse
                .builder()
                .id(1L)
                .amount(new BigDecimal("1000"))
                .operationDate(OPERATION_DATE)
                .build();

        Transaction transaction = Transaction
                .builder()
                .sourceAccount(Account.builder().accountNumber(ACCOUNT_NUMBER).build())
                .build();

        when(retrieveHistoricalBalanceUseCase.execute(any(), any(), any())).thenReturn(Collections.singletonList(transaction));
        when(transactionDtoMapper.toRetrieveHistoricalBalanceReponseList(any())).thenReturn(List.of(response));

        // Act & Assert
        mockMvc.perform(get("/transactions/retrieve-historical-balance")
                        .param("sourceAccountId", "1")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].amount").value(1000));
    }

    @Test
    void testTransferFunds() throws Exception {
        // Arrange
        TransferFundsRequest request = new TransferFundsRequest("12345", "67890", new BigDecimal("500"), "Transfer");
        Transaction transaction = new Transaction();
        TransferFundsResponse response = TransferFundsResponse
                .builder()
                .sourceAccount("1")
                .sourceHolderName("Claudemir")
                .destinationAccount("2")
                .destinationHolderName("João")
                .amount(new BigDecimal("500"))
                .description("Transfer")
                .operationDate(LocalDateTime.now())
                .build();

        when(transferFundsUseCase.execute(any())).thenReturn(transaction);
        when(transactionDtoMapper.toTransferFundsResponse(transaction)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/transactions/transfer-funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceAccount\": \"1\", \"destinationAccount\": \"2\", \"amount\": 500, \"description\": \"Transfer\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sourceAccount").value("1"))
                .andExpect(jsonPath("$.destinationAccount").value("2"))
                .andExpect(jsonPath("$.amount").value(500))
                .andExpect(jsonPath("$.description").value("Transfer"));
    }
}
