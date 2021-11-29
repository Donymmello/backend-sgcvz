package com.supportportal.service.impl;

import com.supportportal.domain.Account;
import com.supportportal.domain.User;
import com.supportportal.exception.domain.AccountExistException;
import com.supportportal.exception.domain.AccountNotFoundException;
import com.supportportal.repository.AccountRepository;
import com.supportportal.service.AccountService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account register(String type, double balance) throws AccountNotFoundException, AccountExistException {
        Account account = new Account();
        account.setAccountId(generateAccountId());
        account.setType(type);
        account.setBalance(balance);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account addNewAccount(String type, double balance)  {
        Account account = new Account();
        account.setAccountId(generateAccountId());
        account.setType(type);
        account.setBalance(balance);
        accountRepository.save(account);
        return account;
    }

    private String generateAccountId() {
        return RandomStringUtils.randomNumeric(10);
    }

    @Override
    public Account findAccountById(long id) {
        return accountRepository.findAccountByid(id);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

}
