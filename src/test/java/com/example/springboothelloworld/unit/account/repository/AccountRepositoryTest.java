package com.example.springboothelloworld.unit.account.repository;

import com.example.springboothelloworld.account.domain.Account;
import com.example.springboothelloworld.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryTest {

    AccountRepository accountRepository = new AccountRepository();
    
    @BeforeEach
    void clearRepositoryData() {
        accountRepository.getAccounts().clear();
    }

    @Test
    @DisplayName("Successfully insert an account")
    void successfullyInsertAccountTest() {
        Account account = new Account(UUID.randomUUID(), "John");

        Account insertedAccount = accountRepository.insertAccount(account);

        assertNotNull(insertedAccount);
        assertEquals(0, insertedAccount.getID().compareTo(account.getID()));
        assertEquals(0, insertedAccount.getName().compareTo(account.getName()));
    }

    @Test
    @DisplayName("Fail to insert an account")
    void failInsertAccountTest() {
        assertNull(accountRepository.insertAccount(null));
    }

    @Test
    @DisplayName("Return a list of all of the accounts")
    void returnAllAccountsTest() {
        Account account = new Account(UUID.randomUUID(), "John");
        Account account2 = new Account(UUID.randomUUID(), "Doe");

        accountRepository.insertAccount(account);
        accountRepository.insertAccount(account2);
        List<Account> accounts = accountRepository.getAccounts();

        assertNotNull(accounts);
        assertEquals(2, accounts.size());
        assertEquals(0, accounts.get(0).getID().compareTo(account.getID()));
        assertEquals(0, accounts.get(0).getName().compareTo(account.getName()));
        assertEquals(0, accounts.get(1).getID().compareTo(account2.getID()));
        assertEquals(0, accounts.get(1).getName().compareTo(account2.getName()));
    }

    @Test
    @DisplayName("Successfully find the correct account.")
    void successfullyFindAccountTest() {
        UUID id = UUID.randomUUID();
        Account expectedAccount = new Account(id, "John");

        accountRepository.insertAccount(expectedAccount);
        accountRepository.insertAccount(new Account(UUID.randomUUID(), "Doe"));
        Account searchedAccount = accountRepository.findAccount(id);

        assertNotNull(searchedAccount);
        assertEquals(0, searchedAccount.getID().compareTo(expectedAccount.getID()));
        assertEquals(0, searchedAccount.getName().compareTo(expectedAccount.getName()));
    }

    @Test
    @DisplayName("Fail to find the correct account.")
    void failToFindAccountTest() {
        assertNull(accountRepository.findAccount(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Successfully update account")
    void successfullyUpdateAccountTest() {
        UUID id = UUID.randomUUID();

        accountRepository.insertAccount(new Account(id, "John"));
        Account account = accountRepository.updateAccount(new Account(id, "Doe"));

        assertNotNull(account);
        assertEquals(0, account.getID().compareTo(id));
        assertEquals(0, account.getName().compareTo("Doe"));
    }

    @Test
    @DisplayName("Fail to update account due to non existent account.")
    void failToUpdateAccountTest() {
        assertNull(accountRepository.updateAccount(new Account(UUID.randomUUID(), "John")));
    }

    @Test
    @DisplayName("Successfully delete account.")
    void successfullyDeleteAccountTest() {
        UUID id = UUID.randomUUID();

        accountRepository.insertAccount(new Account(id, "John"));
        assertEquals(1, accountRepository.getAccounts().size());

        Account account = accountRepository.deleteAccount(id);
        assertEquals(0, accountRepository.getAccounts().size());
        assertEquals(0, account.getID().compareTo(id));
        assertEquals(0, account.getName().compareTo("John"));
    }

    @Test
    @DisplayName("Fail to delete account.")
    void failToDeleteAccountTest() {
        assertNull(accountRepository.deleteAccount(UUID.randomUUID()));
    }

}
