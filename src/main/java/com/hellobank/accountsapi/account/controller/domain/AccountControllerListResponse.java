package com.hellobank.accountsapi.account.controller.domain;

import com.hellobank.accountsapi.account.domain.Account;

import java.util.List;

public class AccountControllerListResponse {
    private List<Account> accounts;
    private MetaData metadata;

    public AccountControllerListResponse(List<Account> accounts) {
        this.accounts = accounts;
        this.metadata = new MetaData(accounts.size());
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        this.metadata = new MetaData(accounts.size());
    }

    public MetaData getMetadata() {
        return metadata;
    }

    public record MetaData(int totalCount) {}
}
