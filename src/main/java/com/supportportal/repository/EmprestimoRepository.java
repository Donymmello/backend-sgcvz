package com.supportportal.repository;

import com.supportportal.domain.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    Emprestimo findEmprestimoById(long Id);
}
