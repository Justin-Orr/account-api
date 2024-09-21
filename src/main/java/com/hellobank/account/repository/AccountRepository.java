package com.hellobank.account.repository;

import com.hellobank.account.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class AccountRepository {
    private static final ArrayList<Account> accounts = new ArrayList<>();

    public Account insertAccount(Account account) {
        accounts.add(account);
        return account;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Account findAccount(UUID id) {
        for(Account account : accounts) {
            if(account.getID().compareTo(id) == 0) {
                return account;
            }
        }
        return null;
    }

    public Account updateAccount(Account inputAccount) {
        for(Account account : accounts) {
            if(account.getID().compareTo(inputAccount.getID()) == 0) {
                account.setName(inputAccount.getName());
                return account;
            }
        }
        return null;
    }

    public Account deleteAccount(UUID id) {
        Account account = findAccount(id);
        accounts.remove(account);
        return account;
    }
}
