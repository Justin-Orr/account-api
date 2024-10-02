package com.hellobank.accountsapi.account.controller;

import com.hellobank.accountsapi.account.controller.AccountController;
import com.hellobank.accountsapi.account.domain.Account;
import com.hellobank.accountsapi.account.repository.error.AccountNotFoundException;
import com.hellobank.accountsapi.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class) // Focus on testing the controller layer
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc; // Used for performing HTTP requests

    @MockBean
    private AccountService accountService; // Mocking the service layer

    private final String baseUrl = "/v1/accounts";

    @Test
    @DisplayName("Create an account")
    void createAccountTest() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "John");

        try {
            when(accountService.createAccount("John")).thenReturn(account);
            mockMvc.perform(
                            post(baseUrl).queryParam("name", "John")
                    ).andExpect(status().isCreated())
                    .andExpect(jsonPath("$.account.id").value(id.toString())) // Path must start with a . or [
                    .andExpect(jsonPath("$.account.name").value("John"));
            verify(accountService, times(1)).createAccount("John");
        } catch (Exception exception) {
            fail("Test failed due to exception: " + exception.getMessage());
        }
    }

    @Test
    @DisplayName("Successfully find an account")
    void successfullyFindAccountTest() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "John");

        try {
            when(accountService.getAccount(id)).thenReturn(account);
            mockMvc.perform(get(baseUrl + "/" + id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.account.id").value(id.toString()))
                    .andExpect(jsonPath("$.account.name").value("John"));
            verify(accountService, times(1)).getAccount(id);
        } catch (Exception exception) {
            fail("Test failed due to exception: " + exception.getMessage());
        }
    }

    @Test
    @DisplayName("Fail to find an account")
    void failToFindAccountTest() {
        try {
            when(accountService.getAccount(any()))
                    .thenThrow(
                            new AccountNotFoundException(HttpStatusCode.valueOf(404), "Account not found")
                    );
            mockMvc.perform(get(baseUrl + "/" + UUID.randomUUID()))
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason("Account not found"));
            verify(accountService, times(1)).getAccount(any());
        } catch (Exception exception) {
            fail("Test failed due to exception: " + exception.getMessage());
        }
    }

    @Test
    @DisplayName("Get all accounts")
    void getAllAccountsTest() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Account account1 = new Account(id1, "John");
        Account account2 = new Account(id2, "Doe");
        ArrayList<Account> accounts = new ArrayList<Account>();
        accounts.add(account1);
        accounts.add(account2);

        try {
            when(accountService.getAccounts()).thenReturn(accounts);
            mockMvc.perform(get(baseUrl))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.metadata.totalCount").value(2))
                    .andExpect(jsonPath("$.accounts[0].id").value(id1.toString()))
                    .andExpect(jsonPath("$.accounts[0].name").value("John"))
                    .andExpect(jsonPath("$.accounts[1].id").value(id2.toString()))
                    .andExpect(jsonPath("$.accounts[1].name").value("Doe"));
            verify(accountService, times(1)).getAccounts();
        } catch (Exception exception) {
            fail("Test failed due to exception: " + exception.getMessage());
        }
    }

    @Test
    @DisplayName("Successfully update an account")
    void successfullyUpdateAccountTest() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "Doe");

        try {
            when(accountService.updateAccount(id, "Doe")).thenReturn(account);
            mockMvc.perform(
                        put(baseUrl + "/" + id).queryParam("name", "Doe")
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.account.id").value(id.toString()))
                    .andExpect(jsonPath("$.account.name").value("Doe"));
            verify(accountService, times(1)).updateAccount(id, "Doe");
        } catch (Exception exception) {
            fail("Test failed due to exception: " + exception.getMessage());
        }
    }

    @Test
    @DisplayName("Fail to update an account")
    void failToUpdateAccountTest() {
        UUID id = UUID.randomUUID();

        try {
            when(accountService.updateAccount(id, "Doe"))
                    .thenThrow(
                            new AccountNotFoundException(HttpStatusCode.valueOf(404), "Account not found")
                    );
            mockMvc.perform(
                            put(baseUrl + "/" + id).queryParam("name", "Doe")
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason("Account not found"));
            verify(accountService, times(1)).updateAccount(id, "Doe");
        } catch (Exception exception) {
            fail("Test failed due to exception: " + exception.getMessage());
        }
    }

    @Test
    @DisplayName("Successfully delete an account")
    void successfullyDeleteAccountTest() {
        UUID id = UUID.randomUUID();

        try {
            Mockito.doNothing().when(accountService).deleteAccount(id);
            mockMvc.perform(delete(baseUrl + "/" + id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Successfully deleted account"));
            verify(accountService, times(1)).deleteAccount(id);
        } catch (Exception exception) {
            fail("Test failed due to exception: " + exception.getMessage());
        }
    }

    @Test
    @DisplayName("Fail to delete an account")
    void failToDeleteAccountTest() {
        UUID id = UUID.randomUUID();

        try {
            Mockito.doThrow(
                    new AccountNotFoundException(HttpStatusCode.valueOf(404), "Account not found")
            ).when(accountService).deleteAccount(id);
            mockMvc.perform(delete(baseUrl + "/" + id))
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason("Account not found"));
            verify(accountService, times(1)).deleteAccount(id);
        } catch (Exception exception) {
            fail("Test failed due to exception: " + exception.getMessage());
        }
    }
}