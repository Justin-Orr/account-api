package com.hellobank.accountsapi.account.service;

import com.hellobank.accountsapi.account.domain.Account;
import com.hellobank.accountsapi.account.repository.AccountRepository;
import com.hellobank.accountsapi.account.repository.error.AccountNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(String name) {
        return accountRepository.insertAccount(new Account(UUID.randomUUID(), name));
    }

    public List<Account> getAccounts() {
        return accountRepository.getAccounts();
    }

    public Account getAccount(UUID id) throws AccountNotFoundException {
        return accountRepository.findAccount(id);
    }

    public Account updateAccount(UUID id, String name) throws AccountNotFoundException {
        return accountRepository.updateAccount(new Account(id, name));
    }

    public void deleteAccount(UUID id) throws AccountNotFoundException {
        accountRepository.deleteAccount(id);
    }

}
