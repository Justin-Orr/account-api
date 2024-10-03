package com.hellobank.accountsapi.account.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountTest {

    @Test
    void defaultConstructorTest() {
        Account account = new Account();
        assertNotNull(account);
    }

    @Test
    void overloadedConstructorTest() {
        Account account = new Account(UUID.randomUUID(), "John Doe");
        assertNotNull(account);
    }

    @Test
    void getID() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "John Doe");
        assertEquals(0, account.getID().compareTo(id));
    }

    @Test
    void getName() {
        Account account = new Account(UUID.randomUUID(), "John Doe");
        assertEquals(0, account.getName().compareTo("John Doe"));
    }

    @Test
    void setId() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Account account = new Account(id1, "John Doe");
        account.setId(id2);
        assertEquals(0, account.getID().compareTo(id2));
    }

    @Test
    void setName() {
        String name1 = "John";
        String name2 = "Doe";
        Account account = new Account(UUID.randomUUID(), name1);
        account.setName(name2);
        assertEquals(0, account.getName().compareTo(name2));
    }
}