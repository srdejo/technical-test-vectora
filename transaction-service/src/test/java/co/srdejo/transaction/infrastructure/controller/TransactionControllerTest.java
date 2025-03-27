package co.srdejo.transaction.infrastructure.controller;

import co.srdejo.transaction.application.dto.TransactionRequestDto;
import co.srdejo.transaction.application.dto.TransactionResponseDto;
import co.srdejo.transaction.domain.model.Transaction;
import co.srdejo.transaction.domain.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateTransactionSuccessfully() throws Exception {
        // Arrange
        TransactionRequestDto requestDto = new TransactionRequestDto(1001L, 2001L, 500.0);
        TransactionResponseDto responseDto = new TransactionResponseDto("Success", 1101);

        when(transactionService.create(any(TransactionRequestDto.class))).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1101))
                .andExpect(jsonPath("$.status").value("Success"));
    }

    @Test
    void shouldGetTransactionsForAccount() throws Exception {
        // Arrange
        long accountId = 1001L;
        List<Transaction> transactions = List.of(
                new Transaction(accountId, 2001L, 500.0),
                new Transaction(accountId, 3001L, 300.0)
        );

        when(transactionService.getTransactions(accountId)).thenReturn(transactions);

        // Act & Assert
        mockMvc.perform(get("/transactions/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].fromAccount").value(1001L))
                .andExpect(jsonPath("$[0].toAccount").value(2001L))
                .andExpect(jsonPath("$[0].amount").value(500.0))
                .andExpect(jsonPath("$[1].toAccount").value(3001L));
    }
}