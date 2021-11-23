package com.supportportal.resource;

import com.supportportal.domain.Account;
import com.supportportal.domain.User;
import com.supportportal.exception.ExceptionHandling;
import com.supportportal.exception.domain.AccountExistException;
import com.supportportal.exception.domain.AccountNotFoundException;
import com.supportportal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/account")
public class AccountResource extends ExceptionHandling {
    private final AccountService accountService;

    @Autowired
    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) throws AccountExistException, AccountNotFoundException, IOException {
        Account newAccount = accountService.register(account.getType(), account.getBalance(), account.getOwner());
        return  new ResponseEntity<>(newAccount, OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Account> addNewAccount(@RequestParam("type") String type,
                                                 @RequestParam("balance") double balance,
                                                 @RequestParam("owner") User owner) throws AccountNotFoundException, AccountExistException, IOException {
        Account newAccount = accountService.addNewAccount(type, balance, owner);
        return new ResponseEntity<>(newAccount, OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") long id) {
        Account account = accountService.findAccountById(id);
        return new ResponseEntity<>(account, OK);
    }

    @GetMapping("/find/{owner}")
    public ResponseEntity<Account> getAccount(@PathVariable("owner") String owner) {
        Account account = accountService.findAccountByOwner(owner);
        return new ResponseEntity<>(account, OK);
    }
}

