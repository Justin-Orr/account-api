package com.example.springboothelloworld.account.controller;

import com.example.springboothelloworld.account.domain.Account;
import com.example.springboothelloworld.account.service.AccountManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountManagerService accountManagerService;

    public AccountController(AccountManagerService accountManagerService) {
        this.accountManagerService = accountManagerService;
    }

    @PostMapping
    public String createAccount(@RequestParam(name = "name") String name) {
        boolean successful = accountManagerService.createAccount(name);
        if (successful)
            return "Successfully created account with name: " + name;
        else
            return "Failed to create account with name: " + name;
    }

    @GetMapping
    public ArrayList<Account> getAllAccounts() {
        return accountManagerService.getAccounts();
    }

    @GetMapping("/{id}")
    public Account findAccount(@PathVariable("id") int id) {
        return accountManagerService.getAccount(id);
    }

    @PutMapping
    public String updateAccount(@RequestBody Account account) {
        boolean successful = accountManagerService.updateAccount(account);
        if (successful)
            return "Successfully update account with id: " + account.getID();
        else
            return "Failed to update account with id: " + account.getID();
    }

    //Todo: Fix this method, the delete does not actually delete and change the input to id and not account also add github support.
    @DeleteMapping
    public String deleteAccount(@RequestBody Account account) {
        boolean successful = accountManagerService.deleteAccount(account);
        if (successful)
            return "Successfully deleted account with id: " + account.getID();
        else
            return "Failed to delete account with id: " + account.getID();
    }
}
