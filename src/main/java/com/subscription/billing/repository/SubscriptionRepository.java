package com.subscription.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subscription.billing.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}