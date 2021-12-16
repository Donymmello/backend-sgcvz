package com.supportportal.repository;

import com.supportportal.domain.Loan;
import com.supportportal.enumeration.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByLoanStatus(LoanStatus status);
}
