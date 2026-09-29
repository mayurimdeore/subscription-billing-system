package com.subscription.billing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.subscription.billing.entity.Subscription;
import com.subscription.billing.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Subscription updateSubscription(Long id, Subscription subscription) {
        Subscription existingSubscription =
                subscriptionRepository.findById(id).orElse(null);

        if (existingSubscription != null) {
            existingSubscription.setStartDate(subscription.getStartDate());
            existingSubscription.setEndDate(subscription.getEndDate());
            existingSubscription.setStatus(subscription.getStatus());
            existingSubscription.setCustomer(subscription.getCustomer());
            existingSubscription.setSubscriptionPlan(subscription.getSubscriptionPlan());

            return subscriptionRepository.save(existingSubscription);
        }

        return null;
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
}