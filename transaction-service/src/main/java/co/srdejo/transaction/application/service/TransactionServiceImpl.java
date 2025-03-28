package co.srdejo.transaction.application.service;

import co.srdejo.transaction.application.dto.AccountDto;
import co.srdejo.transaction.application.dto.TransactionRequestDto;
import co.srdejo.transaction.domain.exception.AccountNotFoundException;
import co.srdejo.transaction.domain.exception.InsufficientFundsException;
import co.srdejo.transaction.domain.model.Transaction;
import co.srdejo.transaction.domain.model.TransactionStatus;
import co.srdejo.transaction.domain.service.TransactionService;
import co.srdejo.transaction.infrastructure.repository.TransactionRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Log log = LogFactory.getLog(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    public TransactionServiceImpl(TransactionRepository transactionRepository, RestTemplate restTemplate) {
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Transaction create(TransactionRequestDto dto) {
        validateAccounts(dto);
        Transaction transaction = new Transaction(dto.getFromAccount(), dto.getToAccount(), dto.getAmount(), TransactionStatus.PENDING);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public void updateTransaction(long transactionId, TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);
        if (Objects.nonNull(transaction)) {
            transaction.setStatus(status);
            transactionRepository.save(transaction);
        }
    }

    @Override
    public List<Transaction> getTransactions(long accountId) {
        return transactionRepository.findByFromAccount(accountId);
    }

    private void validateAccounts(TransactionRequestDto dto) {
        AccountDto fromAccount = getAccount(dto.getFromAccount());
        if (fromAccount.getSaldo() < dto.getAmount()) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        getAccount(dto.getToAccount());
    }

    private AccountDto getAccount(long accountId) {
        try{
            ResponseEntity<AccountDto> accountDto = restTemplate.getForEntity("http://account-service:8080/accounts/"+accountId, AccountDto.class);
            return accountDto.getBody();
        } catch (RestClientException e) {
            throw new AccountNotFoundException("Cuenta "+accountId+" invalida");
        }
    }

}
