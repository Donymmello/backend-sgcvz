package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "loans")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    @NotNull
    @Min(1000)
    @Max(10000)
    private double amount;
    private String loanStatus;
    private String[] authorities;
    @NotNull
    private Date createdOn;

    @ManyToOne
    @JsonBackReference(value = "user")
    private User user;

    public Loan() {
        super();
    }

    public Loan(double amount, String loanStatus, Date createdOn, User user,  String[] authorities) {
        super();
        this.amount = amount;
        this.loanStatus = loanStatus;
        this.authorities = authorities;
        this.createdOn = createdOn;
        this.user = user;

    }

    /**
     * Getters and Setters
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }
}
