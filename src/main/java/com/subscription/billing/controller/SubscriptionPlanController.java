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

import com.subscription.billing.entity.SubscriptionPlan;
import com.subscription.billing.service.SubscriptionPlanService;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    public SubscriptionPlanController(SubscriptionPlanService subscriptionPlanService) {
        this.subscriptionPlanService = subscriptionPlanService;
    }

    @GetMapping
    public List<SubscriptionPlan> getAllPlans() {
        return subscriptionPlanService.getAllPlans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPlan> getPlanById(@PathVariable Long id) {
        SubscriptionPlan plan = subscriptionPlanService.getPlanById(id);

        if (plan != null) {
            return ResponseEntity.ok(plan);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public SubscriptionPlan createSubscriptionPlan(
            @Valid @RequestBody SubscriptionPlan subscriptionPlan) {
        return subscriptionPlanService.createPlan(subscriptionPlan);
    }
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionPlan> updatePlan(
            @PathVariable Long id,
            @RequestBody SubscriptionPlan plan) {

        SubscriptionPlan updatedPlan =
                subscriptionPlanService.updatePlan(id, plan);

        if (updatedPlan != null) {
            return ResponseEntity.ok(updatedPlan);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        SubscriptionPlan existingPlan =
                subscriptionPlanService.getPlanById(id);

        if (existingPlan != null) {
            subscriptionPlanService.deletePlan(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}