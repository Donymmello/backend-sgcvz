package com.supportportal.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String loanId;
    @NotNull
    @Min(1)
    private String loanAmount;
    private String nuit;
    private String loanStatus;
    @NotNull
    private Date createdOn;
    @ManyToOne
    private User user;
    private String email;

    public Loan() {
    }

    public Loan(Long id, String loanId, String loanAmount, String nuit, String loanStatus, Date createdOn, User user, String email) {
        this.id = id;
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.nuit = nuit;
        this.loanStatus = loanStatus;
        this.createdOn = createdOn;
        this.user = user;
        this.email = email;
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

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getNuit() {
        return nuit;
    }

    public void setNuit(String nuit) {
        this.nuit = nuit;
    }

    public String getLoanStatus() {
        return loanStatus;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
