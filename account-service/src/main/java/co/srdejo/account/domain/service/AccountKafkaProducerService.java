package co.srdejo.account.domain.service;


import co.srdejo.account.application.dto.TransactionResponseDto;

public interface AccountKafkaProducerService {

    void send(TransactionResponseDto transaction);
}
