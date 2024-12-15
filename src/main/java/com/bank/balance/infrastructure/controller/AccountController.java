package com.bank.balance.infrastructure.controller;

import com.bank.balance.application.usecases.CreateAccountUseCase;
import com.bank.balance.application.usecases.RetrieveBalanceUseCase;
import com.bank.balance.infrastructure.controller.dto.accountcreate.AccountCreateRequest;
import com.bank.balance.infrastructure.controller.dto.accountcreate.AccountCreateResponse;
import com.bank.balance.infrastructure.controller.dto.retrievebalance.RetrieveBalanceResponse;
import com.bank.balance.infrastructure.controller.mapper.AccountDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
@Valid
    private final CreateAccountUseCase createAccountUseCase;
    private final RetrieveBalanceUseCase retrieveBalanceUseCase;
    private final AccountDtoMapper accountDtoMapper;

    public AccountController(CreateAccountUseCase createAccountUseCase,
                             RetrieveBalanceUseCase retrieveBalanceUseCase,
                             AccountDtoMapper accountDtoMapper) {
        this.createAccountUseCase = createAccountUseCase;
        this.retrieveBalanceUseCase = retrieveBalanceUseCase;
        this.accountDtoMapper = accountDtoMapper;
    }

    @Operation(summary = "Create a new account", description = "Creates a new account with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountCreateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public AccountCreateResponse createAccount(@Valid @RequestBody AccountCreateRequest request) {
        var account = accountDtoMapper.toAccount(request);
        var accountSaved = createAccountUseCase.execute(account);
        return accountDtoMapper.toAccountCreateResponse(accountSaved);
    }

    @Operation(summary = "Find balance account", description = "Find balance account with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find executed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountCreateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<RetrieveBalanceResponse> getBalance(@PathVariable(value = "accountNumber", required = true) String accountNumber) {
        var account = retrieveBalanceUseCase.execute(accountNumber);
        return ResponseEntity.ok( accountDtoMapper.toRetrieveBalanceResponse(account));
    }

}

