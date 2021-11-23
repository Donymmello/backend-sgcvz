package com.supportportal.service;

import com.supportportal.domain.Loan;


public interface LoanService {

    Loan create(String loanAmount, String propertyValue, String loanStatus, String nuit);
}
