package com.supportportal.exception.domain;

public class AccountExistException extends Exception{
    public AccountExistException(String message) {
        super(message);
    }
}
