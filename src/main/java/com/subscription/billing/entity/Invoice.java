package com.subscription.billing.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNumber;
    private Double amount;
    private String invoiceDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    public Invoice() {
    }

    public Invoice(String invoiceNumber, Double amount, String invoiceDate,
                   String status, Subscription subscription) {
        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
        this.invoiceDate = invoiceDate;
        this.status = status;
        this.subscription = subscription;
    }

    public Long getId() {
        return id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public String getStatus() {
        return status;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}