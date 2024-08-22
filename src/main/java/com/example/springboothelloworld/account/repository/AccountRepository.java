package com.example.springboothelloworld.account.repository;

import com.example.springboothelloworld.account.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class AccountRepository {
    private static final ArrayList<Account> accounts = new ArrayList<Account>();

    public boolean insertAccount(Account account) {
        return accounts.add(account);
    }

    public Account findAccount(int id) {
        for(Account account : accounts) {
            if(account.getID() == id) {
                return account;
            }
        }
        return null;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public boolean updateAccount(Account inputAccount) {
        for(Account account : accounts) {
            if(account.getID() == inputAccount.getID()) {
                accounts.remove(account);
                return accounts.add(inputAccount);
            }
        }
        return false;
    }

    public boolean deleteAccount(Account account) {
        return accounts.remove(account);
    }
}
