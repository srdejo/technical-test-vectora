package co.srdejo.transaction.domain.service;

import co.srdejo.transaction.application.dto.TransactionRequestDto;
import co.srdejo.transaction.application.dto.TransactionResponseDto;
import co.srdejo.transaction.application.service.TransactionServiceImpl;
import co.srdejo.transaction.domain.model.Transaction;
import co.srdejo.transaction.domain.model.TransactionStatus;
import co.srdejo.transaction.infrastructure.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class TransactionServiceTest {

    @Autowired
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldCreateTransactionSuccessfully() {
        // Arrange
        TransactionRequestDto requestDto = new TransactionRequestDto(1001L, 2001L, 500.0);

        // Act
        TransactionResponseDto responseDto = transactionService.create(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals(TransactionStatus.SUCCESS.name(), responseDto.getStatus());
    }

    @Test
    void shouldGetTransactionsForAccount() {
        // Arrange
        long accountId = 1001L;
        List<TransactionRequestDto> transactions = List.of(
                new TransactionRequestDto(accountId, 2001L, 500.0),
                new TransactionRequestDto(accountId, 3001L, 300.0)
        );
        transactions.forEach(dto -> transactionService.create(dto));

        // Act
        List<Transaction> result = transactionService.getTransactions(accountId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(500.0, result.get(0).getAmount());
        assertEquals(300.0, result.get(1).getAmount());
    }
}