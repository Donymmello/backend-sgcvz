package com.supportportal.resource;

import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.Loan;
import com.supportportal.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/loan")
@CrossOrigin("http://localhost:4200")

public class LoanResource {
    private LoanService loanService;

    @Autowired
    public LoanResource(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/create")
    public ResponseEntity<Loan> create(@RequestBody Loan loan) {
        Loan newLoan = loanService.create(loan.getLoanAmount(), loan.getPropertyValue(), loan.getLoanStatus(), loan.getNuit());
        return new ResponseEntity<>(newLoan, OK);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}