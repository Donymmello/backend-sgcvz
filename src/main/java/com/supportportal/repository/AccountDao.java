package com.supportportal.repository;

import com.supportportal.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountDao extends JpaRepository<Account, Integer> {
        Optional<Account> findByNumber(String number);
}
