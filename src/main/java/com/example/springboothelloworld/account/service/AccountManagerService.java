package com.example.springboothelloworld.account.service;

import com.example.springboothelloworld.account.domain.Account;
import com.example.springboothelloworld.account.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class AccountManagerService {

    private final AccountRepository accountRepository;
    private static int idCounter = 1;

    public AccountManagerService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean createAccount(String name) {
        return accountRepository.insertAccount(new Account(idCounter++, name));
    }

    public Account getAccount(int id) {
        return accountRepository.findAccount(id);
    }

    public ArrayList<Account> getAccounts() {
        return accountRepository.getAccounts();
    }

    public boolean updateAccount(Account account) {
        return accountRepository.updateAccount(account);
    }

    public boolean deleteAccount(int id) {
        return accountRepository.deleteAccount(id);
    }

}
