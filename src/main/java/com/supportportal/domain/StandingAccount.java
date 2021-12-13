package com.supportportal.domain;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CA")
public class StandingAccount extends Account {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double loan;

	public StandingAccount() {
		super();
	}

	public StandingAccount(String code, Date createDate, double rate, User user, double loan) {
		super(code, createDate, rate, user);
		this.loan = loan;
	}

	public double getLoan() {
		return loan;
	}

	public void setLoan(double loan) {
		this.loan = loan;
	}   	

}
