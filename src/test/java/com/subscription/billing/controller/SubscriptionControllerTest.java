
package com.subscription.billing.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subscription.billing.entity.Customer;
import com.subscription.billing.entity.Subscription;
import com.subscription.billing.entity.SubscriptionPlan;
import com.subscription.billing.service.SubscriptionService;

@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubscriptionService subscriptionService;

    private Subscription createValidSubscription() {
        Customer customer = new Customer(
                "Mayuri",
                "mayuri@example.com",
                "9876543210"
        );
        customer.setId(1L);

        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setId(1L);

        return new Subscription(
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2027, 10, 1),
                "ACTIVE",
                customer,
                plan
        );
    }

    @Test
    void testGetAllSubscriptions() throws Exception {
        when(subscriptionService.getAllSubscriptions())
                .thenReturn(Arrays.asList(createValidSubscription()));

        mockMvc.perform(get("/api/subscriptions"))
                .andExpect(status().isOk());

        verify(subscriptionService).getAllSubscriptions();
    }

    @Test
    void testGetAllSubscriptionsEmpty() throws Exception {
        when(subscriptionService.getAllSubscriptions())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetSubscriptionByIdNotFound() throws Exception {
        when(subscriptionService.getSubscriptionById(99L))
                .thenReturn(null);

        mockMvc.perform(get("/api/subscriptions/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateSubscriptionNotFound() throws Exception {
        Subscription subscription = createValidSubscription();

        when(subscriptionService.updateSubscription(
                eq(99L), any(Subscription.class)))
                .thenReturn(null);

        mockMvc.perform(put("/api/subscriptions/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subscription)))
                .andExpect(status().isNotFound());

        verify(subscriptionService)
                .updateSubscription(eq(99L), any(Subscription.class));
    }

    @Test
    void testDeleteSubscriptionNotFound() throws Exception {
        when(subscriptionService.getSubscriptionById(99L))
                .thenReturn(null);

        mockMvc.perform(delete("/api/subscriptions/99"))
                .andExpect(status().isNotFound());

        verify(subscriptionService, never()).deleteSubscription(99L);
    }
}
