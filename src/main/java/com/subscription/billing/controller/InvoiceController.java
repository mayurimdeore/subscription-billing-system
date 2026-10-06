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
import com.subscription.billing.entity.Invoice;
import com.subscription.billing.service.InvoiceService;
import com.subscription.billing.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);

        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        }

        throw new ResourceNotFoundException(
                "Invoice not found with id: " + id
        );
    }

    @PostMapping
    public Invoice createInvoice(@Valid @RequestBody Invoice invoice) {
        return invoiceService.createInvoice(invoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody Invoice invoice) {

        Invoice updatedInvoice =
                invoiceService.updateInvoice(id, invoice);

        if (updatedInvoice != null) {
            return ResponseEntity.ok(updatedInvoice);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        Invoice existingInvoice = invoiceService.getInvoiceById(id);

        if (existingInvoice != null) {
            invoiceService.deleteInvoice(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}