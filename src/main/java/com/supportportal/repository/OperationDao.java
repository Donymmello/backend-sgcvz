package com.supportportal.repository;

import com.supportportal.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationDao extends JpaRepository<Operation, Long> {
    @Query("select o from Operation o where o.account.id=:x order by o.operationDate desc")
    public List<Operation> checkingAccount(@Param("x") int id);
}
