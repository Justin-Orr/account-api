package com.hellobank.accountsapi.account.repository;

import com.hellobank.accountsapi.account.domain.Account;
import com.hellobank.accountsapi.account.repository.error.AccountNotFoundException;
import org.springframework.http.HttpStatusCode;
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

    public Account findAccount(UUID id) throws AccountNotFoundException {
        for(Account account : accounts) {
            if(account.getID().compareTo(id) == 0) {
                return account;
            }
        }
        throw new AccountNotFoundException(HttpStatusCode.valueOf(404), "Account not found");
    }

    public Account updateAccount(Account inputAccount) throws AccountNotFoundException {
        for(Account account : accounts) {
            if(account.getID().compareTo(inputAccount.getID()) == 0) {
                account.setName(inputAccount.getName());
                return inputAccount;
            }
        }
        throw new AccountNotFoundException(HttpStatusCode.valueOf(404), "Account not found");
    }

    public void deleteAccount(UUID id) throws AccountNotFoundException {
        Account account = findAccount(id);
        accounts.remove(account);
    }
}
