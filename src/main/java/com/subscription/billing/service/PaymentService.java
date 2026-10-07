package com.subscription.billing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.subscription.billing.entity.Invoice;
import com.subscription.billing.entity.Payment;
import com.subscription.billing.exception.BusinessRuleException;
import com.subscription.billing.repository.InvoiceRepository;
import com.subscription.billing.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            InvoiceRepository invoiceRepository) {

        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public Payment createPayment(Payment payment) {

        Invoice invoice = invoiceRepository
                .findById(payment.getInvoice().getId())
                .orElse(null);

        if (invoice == null) {
            return null;
        }
        
        if (payment.getAmount() > invoice.getAmount()) {
            throw new BusinessRuleException(
                    "Payment amount cannot be greater than invoice amount"
            );
        }

        payment.setInvoice(invoice);

        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, Payment payment) {

        Payment existingPayment =
                paymentRepository.findById(id).orElse(null);

        if (existingPayment != null) {

            Invoice invoice = invoiceRepository
                    .findById(payment.getInvoice().getId())
                    .orElse(null);

            if (invoice == null) {
                return null;
            }
            
            if (payment.getAmount() > invoice.getAmount()) {
                throw new BusinessRuleException(
                        "Payment amount cannot be greater than invoice amount"
                );
            }

            existingPayment.setAmount(payment.getAmount());
            existingPayment.setPaymentDate(payment.getPaymentDate());
            existingPayment.setPaymentMethod(payment.getPaymentMethod());
            existingPayment.setStatus(payment.getStatus());
            existingPayment.setInvoice(invoice);

            return paymentRepository.save(existingPayment);
        }

        return null;
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}