package com.hellobank.account.controller.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountControllerSuccessfulResponse implements AccountControllerResponse {
    private Object payload;

    public AccountControllerSuccessfulResponse(Object payload) {
        this.payload = payload;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
