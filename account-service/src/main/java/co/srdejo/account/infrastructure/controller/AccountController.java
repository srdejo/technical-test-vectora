package co.srdejo.account.infrastructure.controller;

import co.srdejo.account.application.dto.AccountDto;
import co.srdejo.account.application.dto.NewAccountDto;
import co.srdejo.account.domain.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping()
    public ResponseEntity<AccountDto> createAccount(@RequestBody NewAccountDto account) {
        return ResponseEntity.ok(accountService.create(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) throws AccountNotFoundException {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

}
