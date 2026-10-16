
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
import com.subscription.billing.entity.Subscription;
import com.subscription.billing.service.InvoiceService;

@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InvoiceService invoiceService;

    private Invoice createValidInvoice() {
        Subscription subscription = new Subscription();
        subscription.setId(1L);

        return new Invoice(
                "INV-001",
                500.0,
                "2026-10-16",
                "PAID",
                subscription
        );
    }

    @Test
    void testGetAllInvoices() throws Exception {
        when(invoiceService.getAllInvoices())
                .thenReturn(Arrays.asList(createValidInvoice()));

        mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk());

        verify(invoiceService).getAllInvoices();
    }

    @Test
    void testGetAllInvoicesEmpty() throws Exception {
        when(invoiceService.getAllInvoices())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetInvoiceByIdNotFound() throws Exception {
        when(invoiceService.getInvoiceById(99L))
                .thenReturn(null);

        mockMvc.perform(get("/api/invoices/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateInvoiceNotFound() throws Exception {
        Invoice invoice = createValidInvoice();

        when(invoiceService.updateInvoice(eq(99L), any(Invoice.class)))
                .thenReturn(null);

        mockMvc.perform(put("/api/invoices/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invoice)))
                .andExpect(status().isNotFound());

        verify(invoiceService).updateInvoice(eq(99L), any(Invoice.class));
    }

    @Test
    void testDeleteInvoiceNotFound() throws Exception {
        when(invoiceService.getInvoiceById(99L))
                .thenReturn(null);

        mockMvc.perform(delete("/api/invoices/99"))
                .andExpect(status().isNotFound());

        verify(invoiceService, never()).deleteInvoice(99L);
    }
}
