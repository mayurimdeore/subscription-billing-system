package com.subscription.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subscription.billing.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}