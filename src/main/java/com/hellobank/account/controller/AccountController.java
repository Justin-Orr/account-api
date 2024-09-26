package com.hellobank.account.controller;

import com.hellobank.account.controller.domain.AccountControllerListResponse;
import com.hellobank.account.controller.domain.AccountControllerResponse;
import com.hellobank.account.domain.Account;
import com.hellobank.account.repository.error.AccountNotFoundException;
import com.hellobank.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/accounts")
public class AccountController {

    // Dependency Injection Setup
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // The Response Entity object serializes the object its wrapping to turn into JSON.
    @PostMapping(produces = "application/json")
    public ResponseEntity<AccountControllerResponse> createAccount(@RequestParam(name = "name") String name) {
        Account account = accountService.createAccount(name);
        return ResponseEntity.status(201).body(new AccountControllerResponse(account));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<AccountControllerResponse> findAccount(@PathVariable("id") UUID id) {
        try {
            Account account = accountService.getAccount(id);
            return ResponseEntity.status(200).body(new AccountControllerResponse(account));
        } catch (AccountNotFoundException exception) {
            throw new ResponseStatusException(exception.getStatus(), exception.getMessage(), exception);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<AccountControllerListResponse> getAllAccounts() {
        List<Account> accounts = accountService.getAccounts();
        return ResponseEntity.status(200).body(new AccountControllerListResponse(accounts));
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<AccountControllerResponse> updateAccount(
            @PathVariable("id") UUID id,
            @RequestParam(name = "name") String name)
    {
        try {
            Account account = accountService.updateAccount(id, name);
            return ResponseEntity.status(200).body(new AccountControllerResponse(account));
        } catch (AccountNotFoundException exception) {
            throw new ResponseStatusException(exception.getStatus(), exception.getMessage(), exception);
        }
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<AccountControllerResponse> deleteAccount(@PathVariable("id") UUID id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.status(200).body(new AccountControllerResponse("Successfully deleted account"));
        } catch (AccountNotFoundException exception) {
            throw new ResponseStatusException(exception.getStatus(), exception.getMessage(), exception);
        }
    }
}
