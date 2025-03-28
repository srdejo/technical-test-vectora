package co.srdejo.transaction.domain.service;


import co.srdejo.transaction.application.dto.AccountDto;
import co.srdejo.transaction.application.dto.TransactionRequestDto;
import co.srdejo.transaction.application.service.TransactionServiceImpl;
import co.srdejo.transaction.domain.exception.AccountNotFoundException;
import co.srdejo.transaction.domain.exception.InsufficientFundsException;
import co.srdejo.transaction.domain.model.Transaction;
import co.srdejo.transaction.domain.model.TransactionStatus;
import co.srdejo.transaction.infrastructure.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void createTransaction_shouldSaveTransaction_whenFundsAreSufficient() {
        // Arrange
        TransactionRequestDto dto = new TransactionRequestDto(1L, 2L, 100.0);
        AccountDto fromAccount = new AccountDto(1L, "John Doe", 200.0);
        AccountDto toAccount = new AccountDto(2L, "Jane Doe", 150.0);

        when(restTemplate.getForEntity(anyString(), eq(AccountDto.class)))
                .thenReturn(new ResponseEntity<>(fromAccount, HttpStatus.OK))
                .thenReturn(new ResponseEntity<>(toAccount, HttpStatus.OK));

        // Act
        Transaction transaction = transactionService.create(dto);

        // Assert
        assertNotNull(transaction);
        assertEquals(1L, transaction.getFromAccount());
        assertEquals(2L, transaction.getToAccount());
        assertEquals(100.0, transaction.getAmount());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void createTransaction_shouldThrowException_whenFundsAreInsufficient() {
        // Arrange
        TransactionRequestDto dto = new TransactionRequestDto(1L, 2L, 300.0);
        AccountDto fromAccount = new AccountDto(1L, "John Doe", 200.0);

        when(restTemplate.getForEntity(anyString(), eq(AccountDto.class)))
                .thenReturn(new ResponseEntity<>(fromAccount, HttpStatus.OK));

        // Act & Assert
        assertThrows(InsufficientFundsException.class, () -> transactionService.create(dto));
    }

    @Test
    void createTransaction_shouldThrowException_whenAccountDoesNotExist() {
        // Arrange
        TransactionRequestDto dto = new TransactionRequestDto(1L, 2L, 100.0);

        when(restTemplate.getForEntity(anyString(), eq(AccountDto.class)))
                .thenThrow(new RestClientException("Error"));

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> transactionService.create(dto));
    }

    @Test
    void updateTransaction_shouldUpdateStatus_whenTransactionExists() {
        // Arrange
        Transaction transaction = new Transaction(1L, 2L, 100.0, TransactionStatus.PENDING);
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        // Act
        transactionService.updateTransaction(1L, TransactionStatus.SUCCESS);

        // Assert
        assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
        verify(transactionRepository).save(transaction);
    }

    @Test
    void updateTransaction_shouldDoNothing_whenTransactionDoesNotExist() {
        // Arrange
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        transactionService.updateTransaction(1L, TransactionStatus.SUCCESS);

        // Assert
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void getTransactions_shouldReturnTransactionsList() {
        // Arrange
        List<Transaction> transactions = List.of(
                new Transaction(1L, 2L, 100.0, TransactionStatus.PENDING),
                new Transaction(1L, 3L, 50.0, TransactionStatus.SUCCESS)
        );
        when(transactionRepository.findByFromAccount(1L)).thenReturn(transactions);

        // Act
        List<Transaction> result = transactionService.getTransactions(1L);

        // Assert
        assertEquals(2, result.size());
        verify(transactionRepository).findByFromAccount(1L);
    }
}