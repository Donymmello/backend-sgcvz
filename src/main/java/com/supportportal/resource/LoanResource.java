package com.supportportal.resource;

import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.Loan;
import com.supportportal.domain.User;
import com.supportportal.exception.ExceptionHandling;
import com.supportportal.exception.domain.LoanExistException;
import com.supportportal.exception.domain.LoanNotFoundException;
import com.supportportal.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/loan")
@CrossOrigin("http://localhost:4200")

public class LoanResource extends ExceptionHandling {
    private LoanService loanService;
    private HttpStatus httpStatus;

    @Autowired
    public LoanResource(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/create")
    public ResponseEntity<Loan> create(@RequestBody Loan loan) throws LoanNotFoundException, LoanExistException, MessagingException {
        Loan newLoan = loanService.create(loan.getLoanAmount(), loan.getNuit());
        return new ResponseEntity<>(newLoan, OK);
    }

    @PostMapping("/request")
    public ResponseEntity<Loan> request(@RequestParam("loanAmount") String loanAmount,
                                        @RequestParam("nuit") String nuit) throws LoanNotFoundException, LoanExistException, IOException {
        Loan newLoan = loanService.request(loanAmount, nuit);
        return new ResponseEntity<>(newLoan, OK);
    }
    @GetMapping("/list")
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getLoans();
        return new ResponseEntity<>(loans, OK);
    }
    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}