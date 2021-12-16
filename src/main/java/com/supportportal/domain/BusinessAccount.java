package com.supportportal.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("BA")
public class BusinessAccount extends Account {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private double loan;
    private double tax;

    public BusinessAccount() {
        super();
    }

    public BusinessAccount(String number, Date createDate, double rate, User user, double loan, double tax) {
        super(number, createDate, rate, user);
        this.loan = loan;
        this.tax = tax;
    }

    public double getLoan() {
        return loan;
    }

    public void setLoan(double loan) {
        this.loan = loan;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
}

