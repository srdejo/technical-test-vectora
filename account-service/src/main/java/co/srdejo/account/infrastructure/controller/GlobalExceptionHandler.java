package co.srdejo.account.infrastructure.controller;

import co.srdejo.account.application.dto.ExceptionDto;
import co.srdejo.account.domain.exception.AccountNotFoundException;
import co.srdejo.account.domain.exception.InsufficientFundsException;
import co.srdejo.account.domain.exception.NegativeAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleAccountNotFound(AccountNotFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), "404");
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ExceptionDto> handleAccountNotFound(InsufficientFundsException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), "1001");
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NegativeAmountException.class)
    public ResponseEntity<ExceptionDto> handleAccountNotFound(NegativeAmountException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), "1002");
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}
