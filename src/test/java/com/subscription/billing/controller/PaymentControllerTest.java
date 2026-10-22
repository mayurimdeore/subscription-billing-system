
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
import com.subscription.billing.entity.Invoice;
import com.subscription.billing.entity.Payment;
import com.subscription.billing.service.PaymentService;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    private Payment createValidPayment() {
        Invoice invoice = new Invoice();
        invoice.setId(1L);

        return new Payment(
                500.0,
                "2026-10-16",
                "UPI",
                "completed",
                invoice
        );
    }

    @Test
    void testGetAllPayments() throws Exception {
        when(paymentService.getAllPayments())
                .thenReturn(Arrays.asList(createValidPayment()));

        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk());

        verify(paymentService).getAllPayments();
    }

    @Test
    void testGetAllPaymentsEmpty() throws Exception {
        when(paymentService.getAllPayments())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetPaymentByIdNotFound() throws Exception {
        when(paymentService.getPaymentById(99L))
                .thenReturn(null);

        mockMvc.perform(get("/api/payments/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePaymentNotFound() throws Exception {
        Payment payment = createValidPayment();

        when(paymentService.updatePayment(eq(99L), any(Payment.class)))
                .thenReturn(null);

        mockMvc.perform(put("/api/payments/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isNotFound());

        verify(paymentService).updatePayment(eq(99L), any(Payment.class));
    }

    @Test
    void testDeletePaymentNotFound() throws Exception {
        when(paymentService.getPaymentById(99L))
                .thenReturn(null);

        mockMvc.perform(delete("/api/payments/99"))
                .andExpect(status().isNotFound());

        verify(paymentService, never()).deletePayment(99L);
    }
    
    @Test
    void testCreatePaymentWithInvalidStatus() throws Exception {
        Payment payment = createValidPayment();
        payment.setStatus("SUCCESS");

        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isBadRequest());

        verify(paymentService, never())
                .createPayment(any(Payment.class));
    }
}
