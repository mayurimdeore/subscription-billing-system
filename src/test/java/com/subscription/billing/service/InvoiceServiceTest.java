
package com.subscription.billing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.subscription.billing.entity.Invoice;
import com.subscription.billing.entity.Subscription;
import com.subscription.billing.repository.InvoiceRepository;
import com.subscription.billing.repository.SubscriptionRepository;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    private Subscription subscription;
    private Invoice invoice;

    @BeforeEach
    void setUp() {
        subscription = new Subscription();
        subscription.setId(1L);

        invoice = new Invoice();
        invoice.setInvoiceNumber("INV-001");
        invoice.setAmount(1000.0);
        invoice.setInvoiceDate("2026-01-15");
        invoice.setStatus("PENDING");
        invoice.setSubscription(subscription);
    }
    

@Test
void testGetAllInvoicesReturnsList() {
    when(invoiceRepository.findAll())
            .thenReturn(Arrays.asList(invoice));

    List<Invoice> result = invoiceService.getAllInvoices();

    assertEquals(1, result.size());
    assertEquals("INV-001", result.get(0).getInvoiceNumber());
    verify(invoiceRepository, times(1)).findAll();
}

@Test
void testGetAllInvoicesReturnsEmptyList() {
    when(invoiceRepository.findAll())
            .thenReturn(Collections.emptyList());

    List<Invoice> result = invoiceService.getAllInvoices();

    assertTrue(result.isEmpty());
    verify(invoiceRepository, times(1)).findAll();
}

@Test
void testGetInvoiceByIdFound() {
    when(invoiceRepository.findById(1L))
            .thenReturn(Optional.of(invoice));

    Invoice result = invoiceService.getInvoiceById(1L);

    assertNotNull(result);
    assertEquals("INV-001", result.getInvoiceNumber());
    assertEquals(1000.0, result.getAmount());
    verify(invoiceRepository, times(1)).findById(1L);
}

@Test
void testGetInvoiceByIdNotFound() {
    when(invoiceRepository.findById(99L))
            .thenReturn(Optional.empty());

    Invoice result = invoiceService.getInvoiceById(99L);

    assertNull(result);
    verify(invoiceRepository, times(1)).findById(99L);
}


@Test
void testUpdateInvoiceNotFound() {
    when(invoiceRepository.findById(99L))
            .thenReturn(Optional.empty());

    Invoice result = invoiceService.updateInvoice(99L, invoice);

    assertNull(result);
    verify(invoiceRepository, never()).save(any(Invoice.class));
}

@Test
void testUpdateInvoiceWhenSubscriptionNotFound() {
    when(invoiceRepository.findById(1L))
            .thenReturn(Optional.of(invoice));
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.empty());

    Invoice result = invoiceService.updateInvoice(1L, invoice);

    assertNull(result);
    verify(invoiceRepository, never()).save(any(Invoice.class));
}

@Test
void testDeleteInvoiceSuccessfully() {
    invoiceService.deleteInvoice(1L);

    verify(invoiceRepository, times(1)).deleteById(1L);
}

@Test
void testDeleteInvoiceWithDifferentId() {
    invoiceService.deleteInvoice(99L);

    verify(invoiceRepository, times(1)).deleteById(99L);
}

@Test
void testCreateInvoicePreservesInvoiceNumber() {
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.of(subscription));
    when(invoiceRepository.save(invoice))
            .thenReturn(invoice);

    Invoice result = invoiceService.createInvoice(invoice);

    assertEquals("INV-001", result.getInvoiceNumber());
}

@Test
void testCreateInvoicePreservesAmount() {
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.of(subscription));
    when(invoiceRepository.save(invoice))
            .thenReturn(invoice);

    Invoice result = invoiceService.createInvoice(invoice);

    assertEquals(1000.0, result.getAmount());
}

@Test
void testCreateInvoicePreservesInvoiceDate() {
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.of(subscription));
    when(invoiceRepository.save(invoice))
            .thenReturn(invoice);

    Invoice result = invoiceService.createInvoice(invoice);

    assertEquals("2026-01-15", result.getInvoiceDate());
}

@Test
void testCreateInvoicePreservesStatus() {
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.of(subscription));
    when(invoiceRepository.save(invoice))
            .thenReturn(invoice);

    Invoice result = invoiceService.createInvoice(invoice);

    assertEquals("PENDING", result.getStatus());
}

@Test
void testUpdateInvoiceChangesInvoiceNumber() {
    Invoice existingInvoice = new Invoice();
    existingInvoice.setInvoiceNumber("INV-OLD");
    existingInvoice.setAmount(500.0);
    existingInvoice.setInvoiceDate("2025-12-15");
    existingInvoice.setStatus("PENDING");

    invoice.setInvoiceNumber("INV-NEW");

    when(invoiceRepository.findById(1L))
            .thenReturn(Optional.of(existingInvoice));
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.of(subscription));
    when(invoiceRepository.save(existingInvoice))
            .thenReturn(existingInvoice);

    Invoice result = invoiceService.updateInvoice(1L, invoice);

    assertEquals("INV-NEW", result.getInvoiceNumber());
}

@Test
void testUpdateInvoiceChangesAmount() {
    Invoice existingInvoice = new Invoice();
    existingInvoice.setInvoiceNumber("INV-OLD");
    existingInvoice.setAmount(500.0);
    existingInvoice.setInvoiceDate("2025-12-15");
    existingInvoice.setStatus("PENDING");

    invoice.setAmount(2500.0);

    when(invoiceRepository.findById(1L))
            .thenReturn(Optional.of(existingInvoice));
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.of(subscription));
    when(invoiceRepository.save(existingInvoice))
            .thenReturn(existingInvoice);

    Invoice result = invoiceService.updateInvoice(1L, invoice);

    assertEquals(2500.0, result.getAmount());
}

@Test
void testUpdateInvoiceChangesDate() {
    Invoice existingInvoice = new Invoice();
    existingInvoice.setInvoiceNumber("INV-OLD");
    existingInvoice.setAmount(500.0);
    existingInvoice.setInvoiceDate("2025-12-15");
    existingInvoice.setStatus("PENDING");

    invoice.setInvoiceDate("2026-02-20");

    when(invoiceRepository.findById(1L))
            .thenReturn(Optional.of(existingInvoice));
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.of(subscription));
    when(invoiceRepository.save(existingInvoice))
            .thenReturn(existingInvoice);

    Invoice result = invoiceService.updateInvoice(1L, invoice);

    assertEquals("2026-02-20", result.getInvoiceDate());
}

@Test
void testUpdateInvoiceChangesStatus() {
    Invoice existingInvoice = new Invoice();
    existingInvoice.setInvoiceNumber("INV-OLD");
    existingInvoice.setAmount(500.0);
    existingInvoice.setInvoiceDate("2025-12-15");
    existingInvoice.setStatus("PENDING");

    invoice.setStatus("PAID");

    when(invoiceRepository.findById(1L))
            .thenReturn(Optional.of(existingInvoice));
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.of(subscription));
    when(invoiceRepository.save(existingInvoice))
            .thenReturn(existingInvoice);

    Invoice result = invoiceService.updateInvoice(1L, invoice);

    assertEquals("PAID", result.getStatus());
}

@Test
void testCreateInvoiceUsesSubscriptionFoundInRepository() {
    Subscription savedSubscription = new Subscription();
    savedSubscription.setId(2L);

    subscription.setId(1L);
    invoice.setSubscription(savedSubscription);

    when(subscriptionRepository.findById(2L))
            .thenReturn(Optional.of(subscription));
    when(invoiceRepository.save(invoice))
            .thenReturn(invoice);

    Invoice result = invoiceService.createInvoice(invoice);

    assertNotNull(result);
    assertEquals(1L, result.getSubscription().getId());
    verify(subscriptionRepository, times(1)).findById(2L);
}

@Test
void testCreateInvoiceSuccessfully() {
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.of(subscription));
    when(invoiceRepository.save(invoice))
            .thenReturn(invoice);

    Invoice result = invoiceService.createInvoice(invoice);

    assertNotNull(result);
    assertEquals("INV-001", result.getInvoiceNumber());
    assertEquals(1000.0, result.getAmount());
    assertEquals(subscription, result.getSubscription());

    verify(subscriptionRepository, times(1)).findById(1L);
    verify(invoiceRepository, times(1)).save(invoice);
}

@Test
void testUpdateInvoiceSuccessfully() {
    Invoice existingInvoice = new Invoice();
    existingInvoice.setInvoiceNumber("INV-OLD");
    existingInvoice.setAmount(500.0);
    existingInvoice.setInvoiceDate("2025-12-15");
    existingInvoice.setStatus("PENDING");

    when(invoiceRepository.findById(1L))
            .thenReturn(Optional.of(existingInvoice));
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.of(subscription));
    when(invoiceRepository.save(existingInvoice))
            .thenReturn(existingInvoice);

    Invoice result = invoiceService.updateInvoice(1L, invoice);

    assertNotNull(result);
    assertEquals("INV-001", result.getInvoiceNumber());
    assertEquals(1000.0, result.getAmount());
    assertEquals("2026-01-15", result.getInvoiceDate());
    assertEquals(subscription, result.getSubscription());
}

@Test
void testCreateInvoiceWhenSubscriptionNotFound() {
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.empty());

    Invoice result = invoiceService.createInvoice(invoice);

    assertNull(result);
    verify(invoiceRepository, never()).save(any(Invoice.class));
}

}
