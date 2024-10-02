package com.hellobank.accountsapi.account.controller.domain;

import com.hellobank.accountsapi.account.controller.domain.AccountControllerListResponse;
import com.hellobank.accountsapi.account.domain.Account;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountControllerListResponseTest {

    @Test
    void constructorTest() {
        AccountControllerListResponse accountControllerListResponse = new AccountControllerListResponse(new ArrayList<>());
        assertNotNull(accountControllerListResponse);
    }

    @Test
    void getAccounts() {
        Account a = new Account(UUID.randomUUID(), "John");
        AccountControllerListResponse accountControllerListResponse = new AccountControllerListResponse(List.of(a));
        assertEquals(1, accountControllerListResponse.getAccounts().size());
    }

    @Test
    void setAccounts() {
        Account a = new Account(UUID.randomUUID(), "John");
        Account b = new Account(UUID.randomUUID(), "Doe");
        AccountControllerListResponse accountControllerListResponse = new AccountControllerListResponse(List.of(a,b));
        accountControllerListResponse.setAccounts(List.of(a));
        assertEquals(1, accountControllerListResponse.getAccounts().size());
        assertEquals(1,accountControllerListResponse.getMetadata().totalCount());
    }

    @Test
    void getMetadata() {
        AccountControllerListResponse accountControllerListResponse = new AccountControllerListResponse(new ArrayList<>());
        assertNotNull(accountControllerListResponse.getMetadata());
    }
}