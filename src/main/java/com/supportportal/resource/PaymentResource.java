package com.supportportal.resource;

import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.Payment;
import com.supportportal.exception.domain.PaymentExistException;
import com.supportportal.exception.domain.PaymentNotFoundException;
import com.supportportal.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

//@ResponseBody
@RestController
@RequestMapping(value = "/payment")
@CrossOrigin("http://localhost:4200")

public class PaymentResource {
    private final PaymentService paymentService;

    @Autowired
    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/register")
    public ResponseEntity<Payment> register(@RequestBody Payment payment) throws PaymentNotFoundException, PaymentExistException {
        Payment newPayment = paymentService.register(payment.getTaxa(), payment.getValor());
        return new ResponseEntity<>(newPayment, OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Payment> addNewPayment(@RequestParam("taxa") double taxa,
                                                 @RequestParam("valor") double valor) throws PaymentNotFoundException, PaymentExistException, IOException {
        Payment newPayment = paymentService.addNewPayment(valor, taxa );
        return new ResponseEntity<>(newPayment, OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getPayments();
        return new ResponseEntity<>(payments, OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable("id") long id) {
        Payment payment = paymentService.findPaymentById(id);
        return new ResponseEntity<>(payment, OK);
    }
    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
