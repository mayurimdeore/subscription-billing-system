package com.subscription.billing.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import com.subscription.billing.entity.Payment;
import com.subscription.billing.service.PaymentService;
import com.subscription.billing.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);

        if (payment != null) {
            return ResponseEntity.ok(payment);
        }

        throw new ResourceNotFoundException(
                "Payment not found with id: " + id
        );
    }

    @PostMapping
    public Payment createPayment(@Valid @RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody Payment payment) {

        Payment updatedPayment =
                paymentService.updatePayment(id, payment);

        if (updatedPayment != null) {
            return ResponseEntity.ok(updatedPayment);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        Payment existingPayment = paymentService.getPaymentById(id);

        if (existingPayment != null) {
            paymentService.deletePayment(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}