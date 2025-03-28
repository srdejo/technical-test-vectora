package co.srdejo.account.infrastructure.kafka;

import co.srdejo.account.application.dto.TransactionResponseDto;
import co.srdejo.account.domain.service.AccountKafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AccountKafkaProducerImpl implements AccountKafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public AccountKafkaProducerImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    @Override
    public void send(TransactionResponseDto transaction) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            kafkaTemplate.send("transaction-finished-topic", String.valueOf(transaction.getTransactionId()),
                    objectMapper.writeValueAsString(transaction));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
