package com.accout.service.controller;


import com.accout.service.entity.Account;
import com.accout.service.entity.AccountRepository;
import com.accout.service.entity.CreateAccount;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping
    @Transactional
    public void createAccount(@RequestBody @Valid CreateAccount account) {
         accountRepository.save(new Account(account));
    }

    @GetMapping
    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Account> getAccountById(@PathVariable String id) {
        return accountRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable String id, @RequestBody Account accountDetails) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setUsername(accountDetails.getUsername());
        account.setEmail(accountDetails.getEmail());
        account.setPassword(accountDetails.getPassword());
        return accountRepository.save(account);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable String id) {
        accountRepository.deleteById(id);
    }
}

