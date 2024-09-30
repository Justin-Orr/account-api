package com.hellobank.account.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hellobank.account.domain.Account;
import com.hellobank.account.repository.AccountRepository;
import com.hellobank.account.repository.error.AccountNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks // Injects the @Mock annotated dependencies into (accountRepository) into the account service
    AccountService accountService;

    @Test
    @DisplayName("Successfully create an account")
    void successfullyCreateAccountTest() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "John");
        when(accountRepository.insertAccount(new Account(any(), "John"))).thenReturn(account);

        Account insertedAccount = accountService.createAccount("John");

        assertNotNull(insertedAccount);
        assertEquals(0, account.getID().compareTo(insertedAccount.getID()));
        assertEquals(account.getName(), insertedAccount.getName());
        verify(accountRepository, times(1)).insertAccount(any());
    }

    @Test
    @DisplayName("Get all accounts")
    void getAllAccountsTest() {
        List<Account> listOfAccounts = new ArrayList<Account>();
        UUID id = UUID.randomUUID();
        listOfAccounts.add(new Account(id, "John"));
        when(accountRepository.getAccounts()).thenReturn(listOfAccounts);

        List<Account> expectedAccounts = accountService.getAccounts();

        assertNotNull(expectedAccounts);
        assertEquals(0, expectedAccounts.get(0).getID().compareTo(id));
        assertEquals(0, expectedAccounts.get(0).getName().compareTo("John"));
        verify(accountRepository, times(1)).getAccounts();
    }

    @Test
    @DisplayName("Find a specific account")
    void successfullyFindAccountTest() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "John");

        try {
            when(accountRepository.findAccount(id)).thenReturn(account);

            Account expectedAccount = accountService.getAccount(id);

            assertNotNull(expectedAccount);
            assertEquals(0, expectedAccount.getID().compareTo(id));
            assertEquals(0, expectedAccount.getName().compareTo("John"));
            verify(accountRepository, times(1)).findAccount(id);
        } catch (AccountNotFoundException exception) {
            fail("Expected no exception to be thrown");
        }
    }

    @Test
    @DisplayName("Fail to find an account")
    void failToFindAccountTest() {
        try {
            when(accountRepository.findAccount(any()))
                    .thenThrow(
                            new AccountNotFoundException(HttpStatusCode.valueOf(404), "Account not found")
                    );
        } catch (AccountNotFoundException exception) {
            assertEquals(exception.getStatus(), HttpStatusCode.valueOf(404));
            assertEquals(0, exception.getMessage().compareTo("Account not found"));
        }

        try {
            accountService.getAccount(UUID.randomUUID());
            verify(accountRepository, times(1)).insertAccount(any());
            fail("No account should be found");
        } catch (AccountNotFoundException exception) {
            assertEquals(exception.getStatus(), HttpStatusCode.valueOf(404));
            assertEquals(0, exception.getMessage().compareTo("Account not found"));
        }
    }

    @Test
    @DisplayName("Successfully update account")
    void successfullyUpdateAccountTest() {
        UUID id = UUID.randomUUID();
        Account updatedAccount = new Account(id, "Doe");

        try {
            when(accountRepository.updateAccount(any())).thenReturn(updatedAccount);
            Account expectedUpdatedAccount = accountService.updateAccount(id, "Doe");

            assertNotNull(expectedUpdatedAccount);
            assertEquals(0, expectedUpdatedAccount.getID().compareTo(id));
            assertEquals(0, expectedUpdatedAccount.getName().compareTo("Doe"));
            verify(accountRepository, times(1)).updateAccount(any());
        } catch (AccountNotFoundException exception) {
            fail("Expected no exceptions to be thrown");
        }
    }

    @Test
    @DisplayName("Fail to update account information")
    void failToUpdateAccountTest() {
        UUID id = UUID.randomUUID();
        try {
            when(accountRepository.updateAccount(any())).thenThrow(
                    new AccountNotFoundException(HttpStatusCode.valueOf(404), "Account not found")
            );
        } catch (AccountNotFoundException exception) {
            assertEquals(exception.getStatus(), HttpStatusCode.valueOf(404));
            assertEquals(0, exception.getMessage().compareTo("Account not found"));
        }

        try {
            accountService.updateAccount(id, "John");

            fail("No account should be updated");
        } catch (AccountNotFoundException exception) {
            assertEquals(exception.getStatus(), HttpStatusCode.valueOf(404));
            assertEquals(0, exception.getMessage().compareTo("Account not found"));
        }

        try {
            verify(accountRepository, times(1)).updateAccount(any());
        } catch (Exception exception) {
            fail("Unexpected Exception occurred: " + exception.getMessage());
        }
    }

    @Test
    @DisplayName("Successfully delete an account")
    void successfullyDeleteAccountTest() {
        UUID id = UUID.randomUUID();

        try {
            Mockito.doNothing().when(accountRepository).deleteAccount(id);
            accountService.deleteAccount(id);
            verify(accountRepository, times(1)).deleteAccount(id);
        } catch (AccountNotFoundException exception) {
            fail("Expected no exceptions to be thrown");
        }
    }

    @Test
    @DisplayName("Fail to delete account")
    void failToDeleteAccount() {
        UUID id = UUID.randomUUID();

        try {
            Mockito.doThrow(
                    new AccountNotFoundException(HttpStatusCode.valueOf(404), "Account not found")
            ).when(accountRepository).deleteAccount(id);
        } catch (AccountNotFoundException exception) {
            assertEquals(exception.getStatus(), HttpStatusCode.valueOf(404));
            assertEquals(0, exception.getMessage().compareTo("Account not found"));
        }

        try {
            accountService.deleteAccount(id);
            fail("Expected exception to be thrown");
        } catch (AccountNotFoundException exception) {
            assertEquals(exception.getStatus(), HttpStatusCode.valueOf(404));
            assertEquals(0, exception.getMessage().compareTo("Account not found"));
        }

        try {
            verify(accountRepository, times(1)).deleteAccount(id);
        } catch (Exception exception) {
            fail("Unexpected Exception occurred: " + exception.getMessage());
        }
    }
}

