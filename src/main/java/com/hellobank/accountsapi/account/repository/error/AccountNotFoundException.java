package com.hellobank.accountsapi.account.repository.error;

import org.springframework.http.HttpStatusCode;

public class AccountNotFoundException extends Exception {

    private final HttpStatusCode status;

    public AccountNotFoundException(HttpStatusCode status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

}
