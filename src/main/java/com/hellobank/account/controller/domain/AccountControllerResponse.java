package com.hellobank.account.controller.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hellobank.account.domain.Account;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountControllerResponse {
    @JsonProperty("account")
    private Account account;
    @JsonProperty("message")
    private String message;

    public AccountControllerResponse(Account account) {
        this.account = account;
        this.message = null;
    }

    public AccountControllerResponse(String message) {
        this.account = null;
        this.message = message;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
