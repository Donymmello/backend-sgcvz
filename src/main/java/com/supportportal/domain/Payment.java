package com.supportportal.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String paymentId;
    private String username;
    @ManyToOne
    private Loan loan;
    private double valor;
    private double taxa;

    public Payment() {
    }

    public Payment(Long id, String paymentId, String username, double valor, double taxa) {
        this.id = id;
        this.paymentId = paymentId;
        this.username = username;
        this.valor = valor;
        this.taxa = taxa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getValor() {
        return valor + taxa;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
