package co.srdejo.transaction.application.service;

import co.srdejo.transaction.application.dto.TransactionRequestDto;
import co.srdejo.transaction.application.dto.TransactionResponseDto;
import co.srdejo.transaction.domain.model.Transaction;
import co.srdejo.transaction.domain.model.TransactionStatus;
import co.srdejo.transaction.domain.service.TransactionService;
import co.srdejo.transaction.infrastructure.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponseDto create(TransactionRequestDto dto) {
        Transaction transaction = new Transaction(dto.getFromAccount(), dto.getToAccount(), dto.getAmount());
        transactionRepository.save(transaction);
        // @ToDo update balances
        return new TransactionResponseDto(TransactionStatus.SUCCESS.name(), transaction.getId());
    }

    @Override
    public List<Transaction> getTransactions(long accountId) {
        // @ToDo validate if exists
        return transactionRepository.findByFromAccount(accountId);
    }
}
