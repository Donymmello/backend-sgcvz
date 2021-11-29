package com.supportportal.service.impl;

import com.supportportal.domain.Loan;
import com.supportportal.exception.domain.LoanExistException;
import com.supportportal.exception.domain.LoanNotFoundException;
import com.supportportal.repository.LoanRepository;
import com.supportportal.service.LoanService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;


    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loan create(String loanAmount, String nuit) throws LoanNotFoundException, LoanExistException, MessagingException {
        Loan loan = new Loan();
        loan.setLoanId(generateLoanId());
        loan.setLoanAmount(loanAmount);
        loan.setCreatedOn(new Date());
        loanRepository.save(loan);
        return loan;
    }

    @Override
    public Loan request(String loanAmount, String nuit) throws LoanNotFoundException, LoanExistException, IOException {
        Loan loan = new Loan();
        loan.setLoanId(generateLoanId());
        loan.setLoanAmount(loanAmount);
        loan.setNuit(nuit);
        loan.setCreatedOn(new Date());
        loanRepository.save(loan);
        return loan;
    }

    @Override
    public Loan findLoanById(long id) {
        return loanRepository.findLoanById(id);
    }

    @Override
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    private String generateLoanId() {
        return RandomStringUtils.randomNumeric(10);
    }
}