package com.subscription.billing.service;

import com.subscription.billing.model.Subscription;
import com.subscription.billing.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Optional<Subscription> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Subscription updateSubscription(Long id, Subscription subscription) {

        Subscription existingSubscription =
                subscriptionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Subscription not found"));

        existingSubscription.setCustomerName(subscription.getCustomerName());
        existingSubscription.setPlanName(subscription.getPlanName());
        existingSubscription.setAmount(subscription.getAmount());
        existingSubscription.setStatus(subscription.getStatus());

        return subscriptionRepository.save(existingSubscription);
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
}
