package co.srdejo.transaction.application.facade;

import co.srdejo.transaction.application.dto.TransactionRequestDto;
import co.srdejo.transaction.application.dto.TransactionResponseDto;
import co.srdejo.transaction.domain.model.Transaction;
import co.srdejo.transaction.domain.service.TransactionKafkaProducer;
import co.srdejo.transaction.domain.service.TransactionService;
import org.springframework.stereotype.Component;

@Component
public class TransactionFacade {

    private final TransactionService transactionService;
    private final TransactionKafkaProducer transactionKafkaProducer;

    public TransactionFacade(TransactionService transactionService, TransactionKafkaProducer transactionKafkaProducer) {
        this.transactionService = transactionService;
        this.transactionKafkaProducer = transactionKafkaProducer;
    }

    public TransactionResponseDto makeTransaction(TransactionRequestDto transactionRequestDto) {
        Transaction transaction = transactionService.create(transactionRequestDto);
        transactionKafkaProducer.send(transaction);
        return new TransactionResponseDto(transaction.getStatus().name(), transaction.getId());
    }
}
