package com.bank.balance.infrastructure.controller;

import com.bank.balance.application.usecases.DepositFundsUseCase;
import com.bank.balance.application.usecases.TransferFundsUseCase;
import com.bank.balance.application.usecases.WithdrawFundsUseCase;
import com.bank.balance.infrastructure.controller.dto.accountcreate.AccountCreateResponse;
import com.bank.balance.infrastructure.controller.dto.depositfunds.DepositFundsRequest;
import com.bank.balance.infrastructure.controller.dto.depositfunds.DepositFundsResponse;
import com.bank.balance.infrastructure.controller.dto.transferfunds.TransferFundsRequest;
import com.bank.balance.infrastructure.controller.dto.transferfunds.TransferFundsResponse;
import com.bank.balance.infrastructure.controller.mapper.TransactionDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final DepositFundsUseCase depositFundsUseCase;
    private final TransactionDtoMapper transactionDtoMapper;
    private final TransferFundsUseCase transferFundsUseCase;
    private final WithdrawFundsUseCase withdrawFundsUseCase;

    public TransactionController(DepositFundsUseCase depositFundsUseCase,
                                 TransactionDtoMapper transactionDtoMapper,
                                 TransferFundsUseCase transferFundsUseCase, WithdrawFundsUseCase withdrawFundsUseCase) {
        this.depositFundsUseCase = depositFundsUseCase;
        this.transactionDtoMapper = transactionDtoMapper;
        this.transferFundsUseCase = transferFundsUseCase;
        this.withdrawFundsUseCase = withdrawFundsUseCase;
    }

    @Operation(summary = "Execute Deposit Funds", description = "Execute Deposit Funds account with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation executed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountCreateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/deposit-funds")
    public DepositFundsResponse depositFunds(@RequestBody @Valid DepositFundsRequest request) {
        var transaction = depositFundsUseCase.execute(request.getAccountNumber(), request.getAmount(), request.getDescription());
        return transactionDtoMapper.toDepositFundsResponse(transaction);
    }

    @Operation(summary = "Execute Deposit Funds", description = "Execute Deposit Funds account with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation executed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountCreateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/withdraw-funds")
    public DepositFundsResponse withdrawFunds(@RequestBody @Valid DepositFundsRequest request) {
        var transaction = withdrawFundsUseCase.execute(request.getAccountNumber(), request.getAmount(), request.getDescription());
        return transactionDtoMapper.toDepositFundsResponse(transaction);
    }

    @Operation(summary = "Execute Transfer Funds between Account", description = "Execute Transfer Funds between Account with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation executed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountCreateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/transfer-funds")
    public TransferFundsResponse transferFunds(@RequestBody @Valid TransferFundsRequest request) {
        var transaction = transferFundsUseCase.execute( transactionDtoMapper.toTransaction(request));
        return transactionDtoMapper.toTransferFundsResponse(transaction);
    }

}

