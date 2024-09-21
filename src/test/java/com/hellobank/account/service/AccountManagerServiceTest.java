package com.hellobank.account.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hellobank.account.domain.Account;
import com.hellobank.account.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountManagerServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountManagerService accountService;

    @Test
    @DisplayName("Successfully create an account")
    void successfullyCreateAccountTest() {
        Account account = new Account(UUID.randomUUID(), "John");
        when(accountRepository.insertAccount(new Account(any(), "John"))).thenReturn(account);

        Account insertedAccount = accountService.createAccount("John");

        assertNotNull(insertedAccount);
        assertEquals(account.getName(), insertedAccount.getName());
    }

    @Test
    @DisplayName("Fail to create an account")
    void failToCreateAccountTest() {
        when(accountRepository.insertAccount(new Account(any(), "John"))).thenReturn(null);

        Account insertedAccount = accountService.createAccount("John");

        assertNull(insertedAccount);
    }

    @Test
    @DisplayName("Get all accounts")
    void getAllAccountsTest() {
        List<Account> listOfAccounts = new ArrayList<Account>();
        listOfAccounts.add(new Account(UUID.randomUUID(), "John"));
        when(accountRepository.getAccounts()).thenReturn(listOfAccounts);

        List<Account> expectedAccounts = accountService.getAccounts();

        assertNotNull(expectedAccounts);
        assertEquals(0, expectedAccounts.get(0).getName().compareTo("John"));
    }

    @Test
    @DisplayName("Find a specific account")
    void successfullyFindAccountTest() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "John");
        when(accountRepository.insertAccount(new Account(any(), "John"))).thenReturn(account);
        accountService.createAccount("John");
        when(accountRepository.findAccount(id)).thenReturn(account);

        Account expectedAccount = accountService.getAccount(id);

        assertNotNull(expectedAccount);
        assertEquals(0, expectedAccount.getID().compareTo(id));
        assertEquals(0, expectedAccount.getName().compareTo("John"));
    }

    @Test
    @DisplayName("Fail to find an account")
    void failToFindAccountTest() {
        when(accountRepository.findAccount(any())).thenReturn(null);

        Account expectedAccount = accountService.getAccount(UUID.randomUUID());

        assertNull(expectedAccount);
    }

    @Test
    @DisplayName("Successfully update account")
    void successfullyUpdateAccountTest() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "John");
        Account updatedAccount = new Account(id, "Doe");
        when(accountRepository.insertAccount(new Account(any(), "John"))).thenReturn(account);
        accountService.createAccount("John");
        when(accountService.updateAccount(any())).thenReturn(updatedAccount);

        Account expectedUpdatedAccount = accountService.updateAccount(updatedAccount);

        assertNotNull(expectedUpdatedAccount);
        assertEquals(0, expectedUpdatedAccount.getID().compareTo(id));
        assertEquals(0, expectedUpdatedAccount.getName().compareTo("Doe"));
    }

    @Test
    @DisplayName("Fail to update account information")
    void failToUpdateAccountTest() {
        when(accountService.updateAccount(any())).thenReturn(null);

        Account expectedAccount = accountService.updateAccount(new Account(UUID.randomUUID(), "John"));

        assertNull(expectedAccount);
    }

    @Test
    @DisplayName("Successfully delete an account")
    void successfullyDeleteAccountTest() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "John");
        when(accountRepository.insertAccount(new Account(any(), "John"))).thenReturn(account);
        accountService.createAccount("John");
        when(accountRepository.deleteAccount(id)).thenReturn(account);

        Account deletedAccount = accountService.deleteAccount(id);

        assertNotNull(deletedAccount);
        assertEquals(0, deletedAccount.getID().compareTo(id));
        assertEquals(0, deletedAccount.getName().compareTo("John"));
    }

    @Test
    @DisplayName("Fail to delete account")
    void failToDeleteAccount() {
        when(accountRepository.deleteAccount(any())).thenReturn(null);

        Account deletedAccount = accountService.deleteAccount(UUID.randomUUID());

        assertNull(deletedAccount);
    }
}

