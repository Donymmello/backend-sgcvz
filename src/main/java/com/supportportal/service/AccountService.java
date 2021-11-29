package com.supportportal.service;

import com.supportportal.domain.Account;
import com.supportportal.exception.domain.AccountExistException;
import com.supportportal.exception.domain.AccountNotFoundException;

import java.io.IOException;
import java.util.List;

public interface AccountService {

    Account register(String type, double balance) throws AccountNotFoundException, AccountExistException, IOException;

    Account addNewAccount(String type, double balance) throws AccountNotFoundException, AccountExistException, IOException;

    Account findAccountById(long id);


    List<Account> getAccounts();
}

