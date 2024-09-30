package com.hellobank.account.controller.domain;

import com.hellobank.account.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountControllerResponseTest {

    @Test
    @DisplayName("Test overloaded constructor that takes only an account")
    void accountOverloadConstructorTest() {
        AccountControllerResponse accountControllerResponse = new AccountControllerResponse(
                new Account(UUID.randomUUID(), "John Doe")
        );
        assertNotNull(accountControllerResponse);
    }

    @Test
    @DisplayName("Test overloaded constructor that takes only a message")
    void messageOverloadConstructorTest() {
        AccountControllerResponse accountControllerResponse = new AccountControllerResponse("Message");
        assertNotNull(accountControllerResponse);
    }

    @Test
    void getAccount() {
        AccountControllerResponse accountControllerResponse = new AccountControllerResponse(
                new Account(UUID.randomUUID(), "John Doe")
        );
        assertNotNull(accountControllerResponse.getAccount());
    }

    @Test
    void setAccount() {
        AccountControllerResponse accountControllerResponse = new AccountControllerResponse(
                new Account(UUID.randomUUID(), "John Doe")
        );
        accountControllerResponse.setAccount(null);
        assertNull(accountControllerResponse.getAccount());
    }

    @Test
    void getMessage() {
        AccountControllerResponse accountControllerResponse = new AccountControllerResponse("Message");
        assertNotNull(accountControllerResponse.getMessage());
    }

    @Test
    void setMessage() {
        AccountControllerResponse accountControllerResponse = new AccountControllerResponse("Message");
        accountControllerResponse.setMessage(null);
        assertNull(accountControllerResponse.getMessage());
    }
}