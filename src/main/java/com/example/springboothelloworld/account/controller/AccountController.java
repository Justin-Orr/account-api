package com.example.springboothelloworld.account.controller;

import com.example.springboothelloworld.account.controller.domain.AccountControllerErrorResponse;
import com.example.springboothelloworld.account.controller.domain.AccountControllerResponse;
import com.example.springboothelloworld.account.controller.domain.AccountControllerSuccessfulResponse;
import com.example.springboothelloworld.account.domain.Account;
import com.example.springboothelloworld.account.service.AccountManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    // Dependency Injection Setup
    private final AccountManagerService accountManagerService;

    public AccountController(AccountManagerService accountManagerService) {
        this.accountManagerService = accountManagerService;
    }

    // The Response Entity object serializes the object its wrapping to turn into JSON.
    @PostMapping(produces = "application/json")
    public ResponseEntity<AccountControllerResponse> createAccount(@RequestParam(name = "name") String name) {
        Account account = accountManagerService.createAccount(name);
        if (account != null) {
            return ResponseEntity
                    .status(201)
                    .body(new AccountControllerSuccessfulResponse(account));
        } else {
            return ResponseEntity
                    .unprocessableEntity()
                    .body(new AccountControllerErrorResponse("Failed to create account with name: " + name));
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<AccountControllerResponse> getAllAccounts() {
        List<Account> accounts = accountManagerService.getAccounts();
        if(accounts != null) {
            return ResponseEntity
                    .ok()
                    .body(new AccountControllerSuccessfulResponse(accounts));
        } else {
            return ResponseEntity
                    .internalServerError()
                    .body(new AccountControllerErrorResponse("Internal Server Error occurred"));
        }
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<AccountControllerResponse> findAccount(@PathVariable("id") UUID id) {
        Account account = accountManagerService.getAccount(id);
        if(account != null) {
            return ResponseEntity
                    .ok()
                    .body(new AccountControllerSuccessfulResponse(account));
        } else {
            return ResponseEntity
                    .status(404)
                    .body(new AccountControllerErrorResponse("No account found"));
        }
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<AccountControllerResponse> updateAccount(@RequestBody Account updatedAccount) {
        Account account = accountManagerService.updateAccount(updatedAccount);
        if(account != null) {
            return ResponseEntity
                    .ok()
                    .body(new AccountControllerSuccessfulResponse(account));
        } else {
            return ResponseEntity
                    .unprocessableEntity()
                    .body(new AccountControllerErrorResponse("Failed to update account"));
        }
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<AccountControllerResponse> deleteAccount(@RequestParam(name = "id") UUID id) {
        Account account = accountManagerService.deleteAccount(id);
        if(account != null) {
            return ResponseEntity
                    .ok()
                    .body(new AccountControllerSuccessfulResponse(account));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new AccountControllerErrorResponse("Failed to delete account"));
        }
    }
}
