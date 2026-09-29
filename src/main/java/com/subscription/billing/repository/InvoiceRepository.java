package com.subscription.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subscription.billing.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}