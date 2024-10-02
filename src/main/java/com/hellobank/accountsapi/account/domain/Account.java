package com.hellobank.accountsapi.account.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;

    public Account() {
        // Needed for Jackson to deserialize objects from the REST Controller
    }

    public Account(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
