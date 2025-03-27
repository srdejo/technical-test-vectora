package co.srdejo.transaction.domain.service;

import co.srdejo.transaction.application.dto.TransactionRequestDto;
import co.srdejo.transaction.application.dto.TransactionResponseDto;
import co.srdejo.transaction.domain.model.Transaction;

import java.util.List;

public interface TransactionService {
    TransactionResponseDto create(TransactionRequestDto dto);
    List<Transaction> getTransactions(long accountId);
}
