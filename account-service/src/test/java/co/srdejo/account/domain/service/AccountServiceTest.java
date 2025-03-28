package co.srdejo.account.domain.service;

import co.srdejo.account.application.dto.AccountDto;
import co.srdejo.account.application.dto.NewAccountDto;
import co.srdejo.account.domain.exception.AccountNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@EmbeddedKafka
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    void shouldCreateAccountSuccessfully() {
        NewAccountDto dto = new NewAccountDto("John Doe", 1000.0);
        AccountDto account = accountService.create(dto);

        assertThat(account.getId()).isNotNull();
        assertThat(account.getNombre()).isEqualTo("John Doe");
        assertThat(account.getSaldo()).isEqualTo(1000.0);
    }

    @Test
    void shouldThrowExceptionWhenGettingNonExistingAccount() {
        assertThatThrownBy(() -> accountService.getAccount(99L))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessageContaining("Cuenta 99 invalida");
    }

}