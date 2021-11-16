package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Emprestimo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;         //Id do emprestimo
    private String emprestimoId;
    private String username; //user que fez emprestimo
    private double montante;
    private long data_emp;   //data de emprestimo
    private long data_pag;   //prazo de pagamento
    private long data_rec;   //data da recpcao

    public Emprestimo() {
    }

    public Emprestimo(Long id, String emprestimoId, String username, double montante, long data_emp, long data_pag, long data_rec) {
        this.id = id;
        this.emprestimoId = emprestimoId;
        this.username = username;
        this.montante = montante;
        this.data_emp = data_emp;
        this.data_pag = data_pag;
        this.data_rec = data_rec;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmprestimoId() {
        return emprestimoId;
    }

    public void setEmprestimoId(String emprestimoId) {
        this.emprestimoId = emprestimoId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getMontante() {
        return montante;
    }

    public void setMontante(double montante) {
        this.montante = montante;
    }

    public long getData_emp() {
        return data_emp;
    }

    public void setData_emp(long data_emp) {
        this.data_emp = data_emp;
    }

    public long getData_pag() {
        return data_pag;
    }

    public void setData_pag(long data_pag) {
        this.data_pag = data_pag;
    }

    public long getData_rec() {
        return data_rec;
    }

    public void setData_rec(long data_rec) {
        this.data_rec = data_rec;
    }
}

