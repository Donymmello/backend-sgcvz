package com.supportportal.service;

import com.supportportal.domain.Account;
import com.supportportal.domain.User;
import com.supportportal.exception.domain.AccountExistException;
import com.supportportal.exception.domain.AccountNotFoundException;

public interface AccountService {

    Account register(String type, double balance, User owner) throws AccountNotFoundException, AccountExistException;

    Account addNewAccount(String type, double balance, User owner) throws AccountNotFoundException, AccountExistException;

    Account findAccountById(long id);

    Account findAccountByOwner(String owner);
}

