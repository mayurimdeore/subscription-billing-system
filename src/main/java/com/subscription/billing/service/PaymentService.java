
package com.subscription.billing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Payment createPayment(Payment payment) {

        Invoice invoice = invoiceRepository
                .findById(payment.getInvoice().getId())
                .orElse(null);

        if (invoice == null) {
            return null;
        }

        double totalPaid =
                paymentRepository
                        .getTotalCompletedPaymentsByInvoiceId(invoice.getId());

        double paymentAmount = payment.getAmount();

        if ("completed".equalsIgnoreCase(payment.getStatus())) {
            if (totalPaid + paymentAmount > invoice.getAmount()) {
                throw new BusinessRuleException(
                        "Total completed payments cannot exceed invoice amount");
            }
        }

        payment.setInvoice(invoice);
        Payment savedPayment = paymentRepository.save(payment);

        updateInvoiceStatus(invoice);

        return savedPayment;
    }

    @Transactional
    public Payment updatePayment(Long id, Payment payment) {

        Payment existingPayment =
                paymentRepository.findById(id).orElse(null);

        if (existingPayment == null) {
            return null;
        }

        Invoice oldInvoice = existingPayment.getInvoice();

        Invoice newInvoice = invoiceRepository
                .findById(payment.getInvoice().getId())
                .orElse(null);

        if (newInvoice == null) {
            return null;
        }

        double totalPaid =
                paymentRepository
                        .getTotalCompletedPaymentsByInvoiceId(newInvoice.getId());

        if (oldInvoice.getId().equals(newInvoice.getId())
                && "completed".equalsIgnoreCase(existingPayment.getStatus())) {
            totalPaid -= existingPayment.getAmount();
        }

        if ("completed".equalsIgnoreCase(payment.getStatus())) {
            if (totalPaid + payment.getAmount() > newInvoice.getAmount()) {
                throw new BusinessRuleException(
                        "Total completed payments cannot exceed invoice amount");
            }
        }

        existingPayment.setAmount(payment.getAmount());
        existingPayment.setPaymentDate(payment.getPaymentDate());
        existingPayment.setPaymentMethod(payment.getPaymentMethod());
        existingPayment.setStatus(payment.getStatus());
        existingPayment.setInvoice(newInvoice);

        Payment updatedPayment = paymentRepository.save(existingPayment);

        updateInvoiceStatus(oldInvoice);

        if (!oldInvoice.getId().equals(newInvoice.getId())) {
            updateInvoiceStatus(newInvoice);
        }

        return updatedPayment;
    }

    @Transactional
    public void deletePayment(Long id) {

        Payment payment = paymentRepository.findById(id).orElse(null);

        if (payment == null) {
            return;
        }

        Invoice invoice = payment.getInvoice();

        paymentRepository.delete(payment);

        updateInvoiceStatus(invoice);
    }

    private void updateInvoiceStatus(Invoice invoice) {

        double totalPaid =
                paymentRepository
                        .getTotalCompletedPaymentsByInvoiceId(invoice.getId());

        if (totalPaid == 0) {
            invoice.setStatus("PENDING");
        } else if (totalPaid < invoice.getAmount()) {
            invoice.setStatus("PARTIALLY_PAID");
        } else {
            invoice.setStatus("PAID");
        }

        invoiceRepository.save(invoice);
    }
}
