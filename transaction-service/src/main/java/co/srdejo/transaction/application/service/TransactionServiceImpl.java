package co.srdejo.transaction.application.service;

import co.srdejo.transaction.application.dto.TransactionRequestDto;
import co.srdejo.transaction.application.dto.TransactionResponseDto;
import co.srdejo.transaction.domain.model.Transaction;
import co.srdejo.transaction.domain.model.TransactionStatus;
import co.srdejo.transaction.domain.service.TransactionKafkaProducer;
import co.srdejo.transaction.domain.service.TransactionService;
import co.srdejo.transaction.infrastructure.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionKafkaProducer transactionKafkaProducer;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionKafkaProducer transactionKafkaProducer) {
        this.transactionRepository = transactionRepository;
        this.transactionKafkaProducer = transactionKafkaProducer;
    }

    @Override
    public TransactionResponseDto create(TransactionRequestDto dto) {
        Transaction transaction = new Transaction(dto.getFromAccount(), dto.getToAccount(), dto.getAmount());
        transactionRepository.save(transaction);
        transactionKafkaProducer.send(transaction);
        return new TransactionResponseDto(transaction.getStatus().name(), transaction.getId());
    }

    @Override
    public List<Transaction> getTransactions(long accountId) {
        return transactionRepository.findByFromAccount(accountId);
    }

    @KafkaListener(topics = "transaction-finished-topic", groupId = "transaction-group")
    public void handleTransaction(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        TransactionResponseDto responseDto = null;
        try {
            responseDto = objectMapper.readValue(message, TransactionResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (Objects.nonNull(responseDto.getStatus())) {
            Transaction transaction = transactionRepository.findById(responseDto.getTransactionId()).orElse(null);
            if (Objects.nonNull(transaction)) {
                transaction.transactionStatusChanged(TransactionStatus.valueOf(responseDto.getStatus()));
                transactionRepository.save(transaction);
            }
        }
    }
}
