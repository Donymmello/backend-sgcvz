package com.supportportal.service;

import com.supportportal.domain.Payment;

import java.util.List;

public interface PaymentService {

    Payment register(String username, String nr_conta, String valor) ;

    List<Payment> getPayments();

    Payment findPaymentById(long id);

    Payment addNewPayment(String username, String nr_conta, String valor) ;
}
