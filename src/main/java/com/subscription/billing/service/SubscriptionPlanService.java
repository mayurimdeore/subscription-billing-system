package com.subscription.billing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.subscription.billing.entity.SubscriptionPlan;
import com.subscription.billing.repository.SubscriptionPlanRepository;

@Service
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanService(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    public List<SubscriptionPlan> getAllPlans() {
        return subscriptionPlanRepository.findAll();
    }

    public SubscriptionPlan getPlanById(Long id) {
        return subscriptionPlanRepository.findById(id).orElse(null);
    }

    public SubscriptionPlan createPlan(SubscriptionPlan plan) {
        return subscriptionPlanRepository.save(plan);
    }

    public SubscriptionPlan updatePlan(Long id, SubscriptionPlan plan) {
        SubscriptionPlan existingPlan = subscriptionPlanRepository.findById(id).orElse(null);

        if (existingPlan != null) {
            existingPlan.setName(plan.getName());
            existingPlan.setPrice(plan.getPrice());
            existingPlan.setDurationInDays(plan.getDurationInDays());

            return subscriptionPlanRepository.save(existingPlan);
        }

        return null;
    }

    public void deletePlan(Long id) {
        subscriptionPlanRepository.deleteById(id);
    }
}
