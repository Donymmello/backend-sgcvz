package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String paymentId;
    private String username;
    private String nr_conta;
    private String valor;

    public Payment() {
    }

    public Payment(Long id, String paymentId, String username, String nr_conta, String valor) {
        this.id = id;
        this.paymentId = paymentId;
        this.username = username;
        this.nr_conta = nr_conta;
        this.valor = valor;
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

    public String getNr_conta() {
        return nr_conta;
    }

    public void setNr_conta(String nr_conta) {
        this.nr_conta = nr_conta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
