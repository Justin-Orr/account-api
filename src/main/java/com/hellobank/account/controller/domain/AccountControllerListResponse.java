package com.hellobank.account.controller.domain;

import com.hellobank.account.domain.Account;

import java.util.List;

public class AccountControllerListResponse {
    private List<Account> accounts;
    private MetaData metadata;

    public AccountControllerListResponse(List<Account> accounts) {
        this.accounts = accounts;
        this.metadata = new MetaData(accounts.size());
    }

    public List<Account> getAccount() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public MetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaData metadata) {
        this.metadata = metadata;
    }

    public static class MetaData {
        private int totalCount;

        public MetaData(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }
}
