
package com.subscription.billing.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subscription.billing.entity.SubscriptionPlan;
import com.subscription.billing.service.SubscriptionPlanService;

@WebMvcTest(SubscriptionPlanController.class)
class SubscriptionPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubscriptionPlanService subscriptionPlanService;

    private SubscriptionPlan createValidPlan() {
        return new SubscriptionPlan("Premium", 999.0, 365);
    }

    @Test
    void testGetAllPlans() throws Exception {
        when(subscriptionPlanService.getAllPlans())
                .thenReturn(Arrays.asList(createValidPlan()));

        mockMvc.perform(get("/api/plans"))
                .andExpect(status().isOk());

        verify(subscriptionPlanService).getAllPlans();
    }

    @Test
    void testGetAllPlansEmpty() throws Exception {
        when(subscriptionPlanService.getAllPlans())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/plans"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetPlanByIdNotFound() throws Exception {
        when(subscriptionPlanService.getPlanById(99L))
                .thenReturn(null);

        mockMvc.perform(get("/api/plans/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePlanNotFound() throws Exception {
        SubscriptionPlan plan = createValidPlan();

        when(subscriptionPlanService.updatePlan(
                eq(99L), any(SubscriptionPlan.class)))
                .thenReturn(null);

        mockMvc.perform(put("/api/plans/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plan)))
                .andExpect(status().isNotFound());

        verify(subscriptionPlanService)
                .updatePlan(eq(99L), any(SubscriptionPlan.class));
    }

    @Test
    void testDeletePlanNotFound() throws Exception {
        when(subscriptionPlanService.getPlanById(99L))
                .thenReturn(null);

        mockMvc.perform(delete("/api/plans/99"))
                .andExpect(status().isNotFound());

        verify(subscriptionPlanService, never()).deletePlan(99L);
    }
}
