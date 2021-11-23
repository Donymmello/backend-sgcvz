package com.supportportal.service.impl;

import com.supportportal.domain.Loan;
import com.supportportal.repository.LoanRepository;
import com.supportportal.service.LoanService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    private final LoanRepository repository;

    @Autowired
    public LoanServiceImpl(final LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan create(String loanAmount, String propertyValue, String loanStatus, String nuit) {
        Loan loan = new Loan();
        loan.setLoanId(generateLoanId());
        //loan.setPayment(payment);
        loan.setLoanAmount(loanAmount);
        loan.setPropertyValue(propertyValue);
        loan.setLoanStatus(loanStatus);
        loan.setNuit(nuit);
        return loan;
    }

    private String generateLoanId() {
        return RandomStringUtils.randomNumeric(10);
    }
}