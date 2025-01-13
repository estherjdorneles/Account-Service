package com.accout.service.entity;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
@NoArgsConstructor
public class Account {

    @Id
    private String id;
    private String username;

    @Indexed(unique = true)
    private String email;
    private String password;


    public Account(CreateAccount data) {
        this.username = data.username();
        this.email = data.email();
        this.password = data.password();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}