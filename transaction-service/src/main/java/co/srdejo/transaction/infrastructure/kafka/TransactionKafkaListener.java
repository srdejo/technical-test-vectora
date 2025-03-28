package co.srdejo.transaction.infrastructure.kafka;

import co.srdejo.transaction.application.dto.TransactionResponseDto;
import co.srdejo.transaction.domain.model.TransactionStatus;
import co.srdejo.transaction.domain.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionKafkaListener {

    private static final Log log = LogFactory.getLog(TransactionKafkaListener.class);

    private final TransactionService transactionService;

    public TransactionKafkaListener(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "transaction-finished-topic", groupId = "transaction-group")
    public void handleTransaction(String message) {
        log.info("Received transaction message: " + message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            TransactionResponseDto responseDto = objectMapper.readValue(message, TransactionResponseDto.class);
            transactionService.updateTransaction(responseDto.getTransactionId(), TransactionStatus.valueOf(responseDto.getStatus()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
