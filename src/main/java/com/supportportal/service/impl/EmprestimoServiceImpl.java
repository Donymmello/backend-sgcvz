package com.supportportal.service.impl;

import com.supportportal.domain.Emprestimo;
import com.supportportal.repository.EmprestimoRepository;
import com.supportportal.service.EmprestimoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EmprestimoServiceImpl implements EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;
    
    @Autowired
    public EmprestimoServiceImpl(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    @Override
    public Emprestimo register(String username, double montante, long data_emp, long data_pag, long data_rec) {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setEmprestimoId(generateEmprestimoId());
        emprestimo.setUsername(username);
        emprestimo.setMontante(montante);
        emprestimo.setData_emp(data_emp);
        emprestimo.setData_pag(data_pag);
        emprestimo.setData_rec(data_rec);
        emprestimoRepository.save(emprestimo);
        return emprestimo;
    }

    @Override
    public Emprestimo addNewEmprestimo(String username, double montante, long data_emp, long data_pag, long data_rec) {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setEmprestimoId(generateEmprestimoId());
        emprestimo.setUsername(username);
        emprestimo.setMontante(montante);
        emprestimo.setData_emp(data_emp);
        emprestimo.setData_pag(data_pag);
        emprestimo.setData_rec(data_rec);
        emprestimoRepository.save(emprestimo);
        return emprestimo;
    }

    @Override
    public Emprestimo findEmprestimoById(long id) {
        return null;
    }

    @Override
    public Emprestimo findEmprestimoByUsername(String username) {
        return null;
    }

    @Override
    public List<Emprestimo> getEmprestimos() {
        return null;
    }

    private String generateEmprestimoId() {
        return RandomStringUtils.randomNumeric(10);
    }
}
