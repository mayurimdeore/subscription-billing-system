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
import com.subscription.billing.entity.Subscription;
import com.subscription.billing.service.SubscriptionService;
import com.subscription.billing.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public List<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);

        if (subscription != null) {
            return ResponseEntity.ok(subscription);
        }

        throw new ResourceNotFoundException(
                "Subscription not found with id: " + id
        );
    }

    @PostMapping
    public Subscription createSubscription(@Valid @RequestBody Subscription subscription) {
        return subscriptionService.createSubscription(subscription);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscription> updateSubscription(
            @PathVariable Long id,
            @Valid @RequestBody Subscription subscription) {

        Subscription updatedSubscription =
                subscriptionService.updateSubscription(id, subscription);

        if (updatedSubscription != null) {
            return ResponseEntity.ok(updatedSubscription);
        }

        throw new ResourceNotFoundException(
                "Subscription not found with id: " + id
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        Subscription existingSubscription =
                subscriptionService.getSubscriptionById(id);

        if (existingSubscription != null) {
            subscriptionService.deleteSubscription(id);
            return ResponseEntity.noContent().build();
        }

        throw new ResourceNotFoundException(
                "Subscription not found with id: " + id
        );
    }
}