package com.subscription.billing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.subscription.billing.exception.ResourceNotFoundException;
import com.subscription.billing.entity.Customer;
import com.subscription.billing.entity.Subscription;
import com.subscription.billing.entity.SubscriptionPlan;
import com.subscription.billing.repository.CustomerRepository;
import com.subscription.billing.repository.SubscriptionPlanRepository;
import com.subscription.billing.repository.SubscriptionRepository;
import com.subscription.billing.exception.BusinessRuleException;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            CustomerRepository customerRepository,
            SubscriptionPlanRepository subscriptionPlanRepository) {

        this.subscriptionRepository = subscriptionRepository;
        this.customerRepository = customerRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    public Subscription createSubscription(Subscription subscription) {
    	
    	if (subscription.getEndDate().isBefore(subscription.getStartDate()))  {
    		throw new BusinessRuleException(
    		        "End date cannot be before start date"
    	
    	    );
    	}

        Customer customer = customerRepository
                .findById(subscription.getCustomer().getId())
                .orElse(null);

        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository
                .findById(subscription.getSubscriptionPlan().getId())
                .orElse(null);

        if (customer == null || subscriptionPlan == null) {
            return null;
        }

        subscription.setCustomer(customer);
        subscription.setSubscriptionPlan(subscriptionPlan);

        return subscriptionRepository.save(subscription);
    }

    public Subscription updateSubscription(Long id, Subscription subscription) {

    	if (subscription.getEndDate().isBefore(subscription.getStartDate())) {
    		throw new BusinessRuleException(
    		        "End date cannot be before start date"
    		);
    	} 
        Subscription existingSubscription =
                subscriptionRepository.findById(id).orElse(null);

        if (existingSubscription != null) {

            Customer customer = customerRepository
                    .findById(subscription.getCustomer().getId())
                    .orElse(null);

            SubscriptionPlan subscriptionPlan = subscriptionPlanRepository
                    .findById(subscription.getSubscriptionPlan().getId())
                    .orElse(null);

            if (customer == null || subscriptionPlan == null) {
                return null;
            }

            existingSubscription.setStartDate(subscription.getStartDate());
            existingSubscription.setEndDate(subscription.getEndDate());
            existingSubscription.setStatus(subscription.getStatus());
            existingSubscription.setCustomer(customer);
            existingSubscription.setSubscriptionPlan(subscriptionPlan);

            return subscriptionRepository.save(existingSubscription);
        }

        return null;
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
}