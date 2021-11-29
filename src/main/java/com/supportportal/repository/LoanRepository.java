package com.supportportal.repository;

import com.supportportal.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Loan findLoanById(long id);
}
