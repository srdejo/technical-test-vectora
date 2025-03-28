package co.srdejo.transaction.domain.service;

import co.srdejo.transaction.domain.model.Transaction;

public interface TransactionKafkaProducer {
    void send(Transaction transaction);
}
