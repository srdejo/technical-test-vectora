package co.srdejo.transaction.infrastructure.kafka;

import co.srdejo.transaction.application.dto.EventTransaction;
import co.srdejo.transaction.domain.model.Transaction;
import co.srdejo.transaction.domain.service.TransactionKafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TransactionKafkaProducerImpl implements TransactionKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public TransactionKafkaProducerImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    @Override
    public void send(Transaction transaction) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            kafkaTemplate.send("transaction-created-topic", transaction.getId().toString(),
                    objectMapper.writeValueAsString(new EventTransaction(transaction.getFromAccount(), transaction.getToAccount(), transaction.getAmount(), transaction.getId())));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
