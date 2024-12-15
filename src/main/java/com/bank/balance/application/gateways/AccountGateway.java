package com.bank.balance.application.gateways;

import com.bank.balance.domain.Account;

public interface AccountGateway {

    Account save(Account account);

    Account findByAccountNumber(String accountNumber);

}
