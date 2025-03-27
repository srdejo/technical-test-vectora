package co.srdejo.account.domain.service;

import co.srdejo.account.application.dto.NewAccountDto;
import co.srdejo.account.domain.model.Account;

import javax.security.auth.login.AccountNotFoundException;

public interface AccountService {
    Account create(NewAccountDto dto);
    Account getAccount(Long id) throws AccountNotFoundException;
    void updateBalance(Long fromAccountId, Long toAccountId, double amount) throws AccountNotFoundException;
}
