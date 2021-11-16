package com.supportportal.service;

import com.supportportal.domain.Emprestimo;

import java.util.List;

public interface EmprestimoService {

    Emprestimo register(String username, double montante, long data_emp, long data_pag, long data_rec);

    Emprestimo addNewEmprestimo(String username, double montante, long data_emp, long data_pag, long data_rec);

    Emprestimo findEmprestimoById(long id);

    Emprestimo findEmprestimoByUsername(String username);

    List<Emprestimo> getEmprestimos();
}
