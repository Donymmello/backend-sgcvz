package com.supportportal.service.impl;

import com.supportportal.domain.Payment;
import com.supportportal.repository.PaymentRepository;
import com.supportportal.service.PaymentService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public Payment register(String username, String nr_conta, String valor) {
        Payment payment = new Payment();
        payment.setPaymentId(generatedPaymentId());
        payment.setUsername(username);
        payment.setNr_conta(nr_conta);
        payment.setValor(valor);
        return payment;
    }

    @Override
    public Payment addNewPayment(String username, String nr_conta, String valor) {
        Payment payment = new Payment();
        payment.setPaymentId(generatedPaymentId());
        payment.setUsername(username);
        payment.setNr_conta(nr_conta);
        payment.setValor(valor);
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
