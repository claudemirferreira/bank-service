package com.bank.balance.infrastructure.controller.mapper;

import com.bank.balance.domain.Account;
import com.bank.balance.infrastructure.controller.dto.accountcreate.AccountCreateRequest;
import com.bank.balance.infrastructure.controller.dto.accountcreate.AccountCreateResponse;
import com.bank.balance.infrastructure.controller.dto.retrievebalance.RetrieveBalanceResponse;
import org.springframework.stereotype.Service;

@Service
public class AccountDtoMapper {

    public Account toAccount(AccountCreateRequest request) {
        return Account
                .builder()
                .accountNumber(request.getAccountNumber())
                .holderName(request.getHolderName())
                .build();
    }

    public AccountCreateResponse toAccountCreateResponse(Account account) {
        return AccountCreateResponse
                .builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .holderName(account.getHolderName())
                .build();
    }

    public RetrieveBalanceResponse toRetrieveBalanceResponse(Account account) {
        return RetrieveBalanceResponse
                .builder()
                .accountNumber(account.getAccountNumber())
                .holderName(account.getHolderName())
                .balance(account.getBalance())
                .build();
    }

}
