package com.supportportal.repository;

import com.supportportal.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findPaymentById(long id);
}
