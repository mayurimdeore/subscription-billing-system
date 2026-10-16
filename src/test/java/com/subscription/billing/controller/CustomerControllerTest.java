
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
import com.subscription.billing.entity.Customer;
import com.subscription.billing.service.CustomerService;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    void testGetAllCustomers() throws Exception {
        Customer customer = new Customer();
        when(customerService.getAllCustomers())
                .thenReturn(Arrays.asList(customer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk());

        verify(customerService).getAllCustomers();
    }

    @Test
    void testGetAllCustomersEmpty() throws Exception {
        when(customerService.getAllCustomers())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        when(customerService.getCustomerById(99L))
                .thenReturn(null);

        mockMvc.perform(get("/api/customers/99"))
                .andExpect(status().isNotFound());
    }



@Test
void testUpdateCustomerNotFound() throws Exception {
    Customer customer = new Customer(
            "Mayuri",
            "mayuri@example.com",
            "9876543210"
    );

    when(customerService.updateCustomer(eq(99L), any(Customer.class)))
            .thenReturn(null);

    mockMvc.perform(put("/api/customers/99")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(customer)))
            .andExpect(status().isNotFound());

    verify(customerService).updateCustomer(eq(99L), any(Customer.class));
}


    @Test
    void testDeleteCustomerNotFound() throws Exception {
        when(customerService.getCustomerById(99L))
                .thenReturn(null);

        mockMvc.perform(delete("/api/customers/99"))
                .andExpect(status().isNotFound());

        verify(customerService, never()).deleteCustomer(99L);
    }
}
