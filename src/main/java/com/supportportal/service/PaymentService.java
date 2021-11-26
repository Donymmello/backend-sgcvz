package com.supportportal.service;

import com.supportportal.domain.Payment;
import com.supportportal.exception.domain.PaymentExistException;
import com.supportportal.exception.domain.PaymentNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PaymentService {

    Payment register(double taxa, double valor) throws PaymentNotFoundException, PaymentExistException;

    List<Payment> getPayments();

    Payment findPaymentById(long id);

    Payment addNewPayment(double taxa, double valor) throws PaymentNotFoundException, PaymentExistException, IOException;
}
