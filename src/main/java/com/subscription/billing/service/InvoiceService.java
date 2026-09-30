package com.subscription.billing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.subscription.billing.entity.Invoice;
import com.subscription.billing.entity.Subscription;
import com.subscription.billing.repository.InvoiceRepository;
import com.subscription.billing.repository.SubscriptionRepository;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final SubscriptionRepository subscriptionRepository;

    public InvoiceService(
            InvoiceRepository invoiceRepository,
            SubscriptionRepository subscriptionRepository) {

        this.invoiceRepository = invoiceRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public Invoice createInvoice(Invoice invoice) {

        Subscription subscription = subscriptionRepository
                .findById(invoice.getSubscription().getId())
                .orElse(null);

        if (subscription == null) {
            return null;
        }

        invoice.setSubscription(subscription);

        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Long id, Invoice invoice) {

        Invoice existingInvoice =
                invoiceRepository.findById(id).orElse(null);

        if (existingInvoice != null) {

            Subscription subscription = subscriptionRepository
                    .findById(invoice.getSubscription().getId())
                    .orElse(null);

            if (subscription == null) {
                return null;
            }

            existingInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
            existingInvoice.setAmount(invoice.getAmount());
            existingInvoice.setInvoiceDate(invoice.getInvoiceDate());
            existingInvoice.setStatus(invoice.getStatus());
            existingInvoice.setSubscription(subscription);

            return invoiceRepository.save(existingInvoice);
        }

        return null;
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}