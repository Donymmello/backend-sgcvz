package com.supportportal.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String loanId;

    @ManyToOne
    private Payment payment;

    @NotNull
    @Min(1)
    private String loanAmount;

    @NotNull
    @Min(1)
    private String propertyValue;

    private String loanStatus;

    @NotNull
    @Min(value = 100000000, message = "Bad Value provided for SSN ")
    private String nuit;

    public Loan() {
    }

    public Loan(Long id, String loanId, String loanAmount, String propertyValue, String loanStatus, String nuit) {
        this.id = id;
        this.loanId = loanId;
        //this.payment = payment;
        this.loanAmount = loanAmount;
        this.propertyValue = propertyValue;
        this.loanStatus = loanStatus;
        this.nuit = nuit;
    }

    /**
     * Getters and Setters
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getNuit() {
        return nuit;
    }

    public void setNuit(String nuit) {
        this.nuit = nuit;
    }
    //@Override
    //public String toString() {
        //return "Loan Amount: " + this.loanAmount + " for Property Value: " + this.propertyValue;
    //}
}