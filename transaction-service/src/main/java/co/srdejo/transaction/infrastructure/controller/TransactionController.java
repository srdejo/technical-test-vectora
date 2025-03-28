package co.srdejo.transaction.infrastructure.controller;

import co.srdejo.transaction.application.dto.TransactionRequestDto;
import co.srdejo.transaction.application.dto.TransactionResponseDto;
import co.srdejo.transaction.application.facade.TransactionFacade;
import co.srdejo.transaction.domain.model.Transaction;
import co.srdejo.transaction.domain.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionFacade transactionFacade;

    public TransactionController(TransactionService transactionService, TransactionFacade transactionFacade) {
        this.transactionService = transactionService;
        this.transactionFacade = transactionFacade;
    }

    @PostMapping()
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {
        return ResponseEntity.ok(transactionFacade.makeTransaction(transactionRequestDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<List<Transaction>> getTransaction(@PathVariable long id) {
        return ResponseEntity.ok(transactionService.getTransactions(id));
    }
}
