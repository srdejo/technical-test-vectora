package co.srdejo.account.application.service;

import co.srdejo.account.application.dto.NewAccountDto;
import co.srdejo.account.domain.exception.InsufficientFundsException;
import co.srdejo.account.domain.model.Account;
import co.srdejo.account.domain.service.AccountService;
import co.srdejo.account.infrastructure.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account create(NewAccountDto dto) {
        Account account = new Account(dto.getName(), dto.getInitialBalance());
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(Long id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Cuenta "+id+" invalida"));
    }

    @Override
    public void updateBalance(Long fromAccountId, Long toAccountId, double amount)
            throws AccountNotFoundException, InsufficientFundsException {

        Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow(() -> new AccountNotFoundException("Cuenta "+fromAccountId+" invalida"));
        Account toAccount = accountRepository.findById(toAccountId).orElseThrow(() -> new AccountNotFoundException("Cuenta "+toAccountId+" invalida"));
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
