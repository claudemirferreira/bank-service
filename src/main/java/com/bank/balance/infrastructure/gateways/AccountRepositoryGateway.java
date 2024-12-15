package com.bank.balance.infrastructure.gateways;

import com.bank.balance.domain.Account;
import com.bank.balance.application.gateways.AccountGateway;
import com.bank.balance.infrastructure.excption.AccountNotFoundException;
import com.bank.balance.infrastructure.persistence.entity.AccountEntity;
import com.bank.balance.infrastructure.persistence.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountRepositoryGateway implements AccountGateway {

    private final AccountRepository accountRepository;
    private final AccountEntityMapper accountEntityMapper;

    public AccountRepositoryGateway(AccountRepository accountRepository, AccountEntityMapper accountEntityMapper) {
        this.accountRepository = accountRepository;
        this.accountEntityMapper = accountEntityMapper;
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = accountEntityMapper.toEntity(account);
        AccountEntity savedEntity = accountRepository.save(entity);
        return accountEntityMapper.toDomain(savedEntity);
    }

    public Account findByAccountNumber(String accountNumber) {
        AccountEntity savedEntity = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Notfound accountNumber = " + accountNumber));
        return accountEntityMapper.toDomain(savedEntity);
    }
}
