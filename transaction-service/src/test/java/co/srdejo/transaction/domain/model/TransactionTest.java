package co.srdejo.transaction.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void shouldCreateTransactionCorrectly() {
        // Arrange
        long fromAccount = 1001;
        long toAccount = 2001;
        double amount = 500.0;

        // Act
        Transaction transaction = new Transaction(fromAccount, toAccount, amount);

        // Assert
        assertEquals(fromAccount, transaction.getFromAccount());
        assertEquals(toAccount, transaction.getToAccount());
        assertEquals(amount, transaction.getAmount());
        assertNotNull(transaction.getDateTime());
        assertTrue(transaction.getDateTime().isBefore(LocalDateTime.now().plusSeconds(1)));
    }
}