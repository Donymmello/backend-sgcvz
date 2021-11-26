package com.supportportal.service.impl;

import com.supportportal.domain.Loan;
import com.supportportal.domain.User;
import com.supportportal.repository.LoanRepository;
import com.supportportal.service.LoanService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;


    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loan create(String email, String loanAmount, String nuit, String loanStatus, Date createdOn ) {
        Loan loan = new Loan();
        loan.setLoanId(generateLoanId());
        loan.setEmail(email);
        loan.setLoanAmount(loanAmount);
        loan.setNuit(nuit);
        loan.setLoanStatus(loanStatus);
        loan.setCreatedOn(createdOn);
        loanRepository.save(loan);
        return loan;
    }

    private String generateLoanId() {
        return RandomStringUtils.randomNumeric(10);
    }
}