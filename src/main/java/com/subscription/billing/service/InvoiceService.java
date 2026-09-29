package com.subscription.billing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.subscription.billing.entity.Invoice;
import com.subscription.billing.repository.InvoiceRepository;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Long id, Invoice invoice) {
        Invoice existingInvoice = invoiceRepository.findById(id).orElse(null);

        if (existingInvoice != null) {
            existingInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
            existingInvoice.setAmount(invoice.getAmount());
            existingInvoice.setInvoiceDate(invoice.getInvoiceDate());
            existingInvoice.setStatus(invoice.getStatus());
            existingInvoice.setSubscription(invoice.getSubscription());

            return invoiceRepository.save(existingInvoice);
        }

        return null;
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}