package com.bank.balance.infrastructure.gateways;

import com.bank.balance.domain.Account;
import com.bank.balance.infrastructure.persistence.entity.AccountEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class AccountEntityMapper {

    private final ModelMapper modelMapper;

    public AccountEntityMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountEntity toEntity(Account account) {
        var entity = modelMapper.map(account, AccountEntity.class);
        if (Objects.isNull(entity.getBalance())) {
            entity.setBalance(new BigDecimal(0L));
        }
        return entity;
    }

    public Account toDomain(AccountEntity entity) {
        return modelMapper.map(entity, Account.class);
    }

}
