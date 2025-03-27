package co.srdejo.account.domain.service;

import co.srdejo.account.application.dto.NewAccountDto;
import co.srdejo.account.domain.exception.AccountNotFoundException;
import co.srdejo.account.domain.exception.InsufficientFundsException;
import co.srdejo.account.domain.model.Account;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    void shouldCreateAccountSuccessfully() {
        NewAccountDto dto = new NewAccountDto("John Doe", 1000.0);
        Account account = accountService.create(dto);

        assertThat(account.getId()).isNotNull();
        assertThat(account.getName()).isEqualTo("John Doe");
        assertThat(account.getBalance()).isEqualTo(1000.0);
    }

    @Test
    void shouldThrowExceptionWhenGettingNonExistingAccount() {
        assertThatThrownBy(() -> accountService.getAccount(99L))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessageContaining("Cuenta 99 invalida");
    }

    @Test
    void shouldTransferFundsSuccessfully() throws AccountNotFoundException {
        Account acc1 = accountService.create(new NewAccountDto("Alice", 500.0));
        Account acc2 = accountService.create(new NewAccountDto("Bob", 200.0));

        accountService.updateBalance(acc1.getId(), acc2.getId(), 100.0);

        assertThat(accountService.getAccount(acc1.getId()).getBalance()).isEqualTo(400.0);
        assertThat(accountService.getAccount(acc2.getId()).getBalance()).isEqualTo(300.0);
    }

    @Test
    void shouldThrowExceptionWhenTransferringMoreThanAvailableBalance() {
        Account acc1 = accountService.create(new NewAccountDto("Alice", 100.0));
        Account acc2 = accountService.create(new NewAccountDto("Bob", 200.0));

        assertThatThrownBy(() -> accountService.updateBalance(acc1.getId(), acc2.getId(), 150.0))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessage("Insufficient funds");
    }

}