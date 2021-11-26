package com.supportportal.service;

import com.supportportal.domain.Loan;

import java.util.Date;


public interface LoanService {
    Loan create(String email, String loanAmount, String nuit, String loanStatus, Date createdOn);
}
