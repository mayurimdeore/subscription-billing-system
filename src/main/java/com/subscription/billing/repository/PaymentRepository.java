
package com.subscription.billing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.subscription.billing.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


@Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
       "WHERE p.invoice.id = :invoiceId " +
       "AND LOWER(p.status) = 'completed'")
Double getTotalCompletedPaymentsByInvoiceId(
        @Param("invoiceId") Long invoiceId);


    List<Payment> findByInvoiceId(Long invoiceId);
}
