package com.supportportal.service.impl;

import com.supportportal.domain.Payment;
import com.supportportal.exception.domain.PaymentExistException;
import com.supportportal.exception.domain.PaymentNotFoundException;
import com.supportportal.repository.PaymentRepository;
import com.supportportal.service.PaymentService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment register(double taxa, double valor) throws PaymentNotFoundException, PaymentExistException {
        Payment payment = new Payment();
        payment.setPaymentId(generatedPaymentId());
        payment.setTaxa(taxa);
        payment.setValor(valor);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment addNewPayment(double taxa, double valor) {
        Payment payment = new Payment();
        payment.setPaymentId(generatedPaymentId());
        payment.setTaxa(taxa);
        payment.setValor(valor);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findPaymentById(long id) {
        return paymentRepository.findPaymentById(id);
    }

    private String generatedPaymentId() {
        return RandomStringUtils.randomNumeric(10);
    }
}
