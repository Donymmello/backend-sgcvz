package com.supportportal.service;

import com.supportportal.domain.Loan;
import com.supportportal.exception.domain.LoanExistException;
import com.supportportal.exception.domain.LoanNotFoundException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface LoanService {

    Loan create(String loanAmount, String nuit) throws LoanNotFoundException, LoanExistException, MessagingException;

    Loan request(String loanAmount, String nuit) throws LoanNotFoundException, LoanExistException, IOException;

    Loan findLoanById(long id);

    List<Loan> getLoans();
}
