package com.supportportal.resource;

import com.supportportal.domain.Payment;
import com.supportportal.exception.domain.PaymentExistException;
import com.supportportal.exception.domain.PaymentNotFoundException;
import com.supportportal.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("http://localhost:4200")

public class PaymentResource {
    private final PaymentService paymentService;

    @Autowired
    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay_register")
    public ResponseEntity<Payment> register(@RequestBody Payment payment) throws PaymentNotFoundException, PaymentExistException {
        Payment newPayment = paymentService.register(payment.getUsername(), payment.getNr_conta(), payment.getValor());
        return new ResponseEntity<>(newPayment, OK);
    }

    @PostMapping("/pay_add")
    public ResponseEntity<Payment> addNewPayment(@RequestParam("username") String username,
                                                 @RequestParam("nr_conta") String nr_conta,
                                                 @RequestParam("valor") String valor) throws IOException {
        Payment newPayment = paymentService.addNewPayment(username, nr_conta, valor);
        return new ResponseEntity<>(newPayment, OK);
    }

    @GetMapping("/pay_list")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getPayments();
        return new ResponseEntity<>(payments, OK);
    }
    @GetMapping("/pay_find/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable("id") long id) {
        Payment payment = paymentService.findPaymentById(id);
        return new ResponseEntity<>(payment, OK);
    }
}
