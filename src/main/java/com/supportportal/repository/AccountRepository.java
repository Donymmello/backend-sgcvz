package com.supportportal.repository;

import com.supportportal.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByOwner(String Owner);

    Account findAccountByid(Long id);
}
