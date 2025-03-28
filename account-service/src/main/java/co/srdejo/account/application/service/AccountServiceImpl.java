package co.srdejo.account.application.service;

import co.srdejo.account.application.dto.AccountDto;
import co.srdejo.account.application.dto.EventTransaction;
import co.srdejo.account.application.dto.NewAccountDto;
import co.srdejo.account.application.dto.TransactionResponseDto;
import co.srdejo.account.domain.exception.AccountNotFoundException;
import co.srdejo.account.domain.model.Account;
import co.srdejo.account.domain.model.TransactionStatus;
import co.srdejo.account.domain.service.AccountKafkaProducerService;
import co.srdejo.account.domain.service.AccountService;
import co.srdejo.account.infrastructure.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {

    private static final Log log = LogFactory.getLog(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final AccountKafkaProducerService accountKafkaProducerService;

    public AccountServiceImpl(AccountRepository accountRepository, AccountKafkaProducerService accountKafkaProducerService) {
        this.accountRepository = accountRepository;
        this.accountKafkaProducerService = accountKafkaProducerService;
    }


    @Override
    public AccountDto create(NewAccountDto dto) {
        Account account = new Account(dto.getName(), dto.getInitialBalance());
        accountRepository.save(account);
        return new AccountDto(account.getId(), account.getName(), account.getBalance());
    }

    @Override
    public AccountDto getAccount(Long id) throws AccountNotFoundException {
        Account account =  accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Cuenta "+id+" invalida"));
        return new AccountDto(account.getId(), account.getName(), account.getBalance());
    }

    @Transactional
    @KafkaListener(topics = "transaction-created-topic", groupId = "transaction-group")
    public void updateBalance(String message){
        TransactionResponseDto responseDto = new TransactionResponseDto();
        log.info("Received transaction response: " + message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EventTransaction transaction = objectMapper.readValue(message, EventTransaction.class);
            responseDto.setTransactionId(transaction.getTransactionId());
            Account fromAccount = accountRepository.findById(transaction.getFromAccount()).orElseThrow(() -> new AccountNotFoundException("Cuenta " + transaction.getFromAccount() + " invalida"));
            Account toAccount = accountRepository.findById(transaction.getToAccount()).orElseThrow(() -> new AccountNotFoundException("Cuenta " + transaction.getToAccount() + " invalida"));
            fromAccount.withdraw(transaction.getAmount());
            toAccount.deposit(transaction.getAmount());
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
            responseDto.setStatus(TransactionStatus.SUCCESS.name());
        } catch (Exception e) {
            responseDto.setStatus(TransactionStatus.FAILURE.name());
        } finally {
            accountKafkaProducerService.send(responseDto);
        }
    }
}
