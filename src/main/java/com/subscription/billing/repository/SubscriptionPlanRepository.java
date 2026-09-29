package com.subscription.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subscription.billing.entity.SubscriptionPlan;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {

}