package co.srdejo.transaction.domain.service;

import co.srdejo.transaction.application.dto.TransactionRequestDto;
import co.srdejo.transaction.domain.model.Transaction;
import co.srdejo.transaction.domain.model.TransactionStatus;

import java.util.List;

public interface TransactionService {
    Transaction create(TransactionRequestDto dto);
    List<Transaction> getTransactions(long accountId);
    void updateTransaction(long transactionId, TransactionStatus status);
}
