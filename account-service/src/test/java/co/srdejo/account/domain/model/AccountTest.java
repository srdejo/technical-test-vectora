package co.srdejo.account.domain.model;

import co.srdejo.account.domain.exception.InsufficientFundsException;
import co.srdejo.account.domain.exception.NegativeAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account("John Doe", 1000.0);
    }

    @Test
    void shouldCreateAccountWithInitialBalance() {
        assertThat(account.getName()).isEqualTo("John Doe");
        assertThat(account.getBalance()).isEqualTo(1000.0);
    }

    @Test
    void shouldDepositPositiveAmount() {
        account.deposit(500.0);
        assertThat(account.getBalance()).isEqualTo(1500.0);
    }

    @Test
    void shouldThrowExceptionWhenDepositNegativeAmount() {
        assertThatThrownBy(() -> account.deposit(-100.0))
                .isInstanceOf(NegativeAmountException.class)
                .hasMessage("Amount must be positive");
    }

    @Test
    void shouldWithdrawAmountLessThanOrEqualToBalance() {
        account.withdraw(400.0);
        assertThat(account.getBalance()).isEqualTo(600.0);
    }

    @Test
    void shouldThrowExceptionWhenWithdrawMoreThanBalance() {
        assertThatThrownBy(() -> account.withdraw(1500.0))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessage("Insufficient funds");
    }

    @Test
    void shouldThrowExceptionWhenWithdrawNegativeAmount() {
        assertThatThrownBy(() -> account.withdraw(-200.0))
                .isInstanceOf(NegativeAmountException.class)
                .hasMessage("Amount must be positive");
    }
}