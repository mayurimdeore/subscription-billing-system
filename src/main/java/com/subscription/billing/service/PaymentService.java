package com.subscription.billing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.subscription.billing.entity.Payment;
import com.subscription.billing.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, Payment payment) {
        Payment existingPayment = paymentRepository.findById(id).orElse(null);

        if (existingPayment != null) {
            existingPayment.setAmount(payment.getAmount());
            existingPayment.setPaymentDate(payment.getPaymentDate());
            existingPayment.setPaymentMethod(payment.getPaymentMethod());
            existingPayment.setStatus(payment.getStatus());
            existingPayment.setInvoice(payment.getInvoice());

            return paymentRepository.save(existingPayment);
        }

        return null;
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
