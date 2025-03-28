package co.srdejo.account.domain.service;

import co.srdejo.account.application.dto.AccountDto;
import co.srdejo.account.application.dto.NewAccountDto;
import co.srdejo.account.domain.exception.AccountNotFoundException;


public interface AccountService {
    AccountDto create(NewAccountDto dto);
    AccountDto getAccount(Long id) throws AccountNotFoundException;
}
