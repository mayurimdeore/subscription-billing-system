
package com.subscription.billing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.subscription.billing.entity.Invoice;
import com.subscription.billing.entity.Payment;
import com.subscription.billing.exception.BusinessRuleException;
import com.subscription.billing.repository.InvoiceRepository;
import com.subscription.billing.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void createPayment_shouldRejectPaymentWhenTotalExceedsInvoiceAmount() {

        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setAmount(1000.0);

        Payment payment = new Payment();
        payment.setAmount(200.0);
        payment.setStatus("completed");
        payment.setInvoice(invoice);

        when(invoiceRepository.findById(1L))
                .thenReturn(Optional.of(invoice));

        when(paymentRepository.getTotalCompletedPaymentsByInvoiceId(1L))
                .thenReturn(900.0);

        assertThrows(
                BusinessRuleException.class,
                () -> paymentService.createPayment(payment));

        verify(paymentRepository, never()).save(any(Payment.class));
       
    }
    

@Test
void createPayment_shouldSaveValidPaymentAndUpdateInvoiceStatus() {

    Invoice invoice = new Invoice();
    invoice.setId(2L);
    invoice.setAmount(1000.0);

    Payment payment = new Payment();
    payment.setAmount(300.0);
    payment.setStatus("completed");
    payment.setInvoice(invoice);

    when(invoiceRepository.findById(2L))
            .thenReturn(Optional.of(invoice));

    when(paymentRepository.getTotalCompletedPaymentsByInvoiceId(2L))
    .thenReturn(0.0, 300.0);

    when(paymentRepository.save(payment))
            .thenReturn(payment);

    Payment result = paymentService.createPayment(payment);

    assertNotNull(result);
    assertEquals("PARTIALLY_PAID", invoice.getStatus());

    verify(paymentRepository).save(payment);
    verify(invoiceRepository).save(invoice);
}


@Test
void createPayment_shouldReturnNullWhenInvoiceDoesNotExist() {

    Invoice invoice = new Invoice();
    invoice.setId(99L);

    Payment payment = new Payment();
    payment.setAmount(200.0);
    payment.setStatus("completed");
    payment.setInvoice(invoice);

    when(invoiceRepository.findById(99L))
            .thenReturn(Optional.empty());

    Payment result = paymentService.createPayment(payment);

    assertNull(result);

    verify(paymentRepository, never()).save(any(Payment.class));
}



@Test
void getPaymentById_shouldReturnPaymentWhenFound() {

    Payment payment = new Payment();
    payment.setId(10L);
    payment.setAmount(300.0);
    payment.setStatus("completed");

    when(paymentRepository.findById(10L))
            .thenReturn(Optional.of(payment));

    Payment result = paymentService.getPaymentById(10L);

    assertNotNull(result);
    assertEquals(10L, result.getId());
    assertEquals(300.0, result.getAmount());

    verify(paymentRepository).findById(10L);
}


@Test
void getPaymentById_shouldReturnNullWhenPaymentDoesNotExist() {

    when(paymentRepository.findById(999L))
            .thenReturn(Optional.empty());

    Payment result = paymentService.getPaymentById(999L);

    assertNull(result);

    verify(paymentRepository).findById(999L);
}



@Test
void getAllPayments_shouldReturnAllPayments() {

    Payment payment1 = new Payment();
    payment1.setId(1L);
    payment1.setAmount(300.0);

    Payment payment2 = new Payment();
    payment2.setId(2L);
    payment2.setAmount(500.0);

    when(paymentRepository.findAll())
            .thenReturn(java.util.Arrays.asList(payment1, payment2));

    java.util.List<Payment> result = paymentService.getAllPayments();

    assertEquals(2, result.size());
    assertEquals(300.0, result.get(0).getAmount());
    assertEquals(500.0, result.get(1).getAmount());

    verify(paymentRepository).findAll();
}


@Test
void updatePayment_shouldReturnNullWhenPaymentDoesNotExist() {

    Payment payment = new Payment();

    when(paymentRepository.findById(999L))
            .thenReturn(Optional.empty());

    Payment result = paymentService.updatePayment(999L, payment);

    assertNull(result);

    verify(paymentRepository).findById(999L);
    verify(invoiceRepository, never()).findById(anyLong());
    verify(paymentRepository, never()).save(any(Payment.class));
}

@Test
void updatePayment_shouldReturnNullWhenNewInvoiceDoesNotExist() {

    Invoice oldInvoice = new Invoice();
    oldInvoice.setId(1L);
    oldInvoice.setAmount(1000.0);

    Payment existingPayment = new Payment();
    existingPayment.setId(10L);
    existingPayment.setAmount(200.0);
    existingPayment.setStatus("pending");
    existingPayment.setInvoice(oldInvoice);

    Invoice newInvoice = new Invoice();
    newInvoice.setId(99L);

    Payment updatedPayment = new Payment();
    updatedPayment.setAmount(300.0);
    updatedPayment.setStatus("completed");
    updatedPayment.setInvoice(newInvoice);

    when(paymentRepository.findById(10L))
            .thenReturn(Optional.of(existingPayment));

    when(invoiceRepository.findById(99L))
            .thenReturn(Optional.empty());

    Payment result = paymentService.updatePayment(10L, updatedPayment);

    assertNull(result);

    verify(paymentRepository, never()).save(any(Payment.class));
}


@Test
void updatePayment_shouldUpdateExistingPaymentSuccessfully() {

    Invoice invoice = new Invoice();
    invoice.setId(1L);
    invoice.setAmount(1000.0);

    Payment existingPayment = new Payment();
    existingPayment.setId(10L);
    existingPayment.setAmount(200.0);
    existingPayment.setStatus("pending");
    existingPayment.setInvoice(invoice);

    Payment updatedPayment = new Payment();
    updatedPayment.setAmount(300.0);
    updatedPayment.setStatus("completed");
    updatedPayment.setPaymentMethod("UPI");
    updatedPayment.setInvoice(invoice);

    when(paymentRepository.findById(10L))
            .thenReturn(Optional.of(existingPayment));

    when(invoiceRepository.findById(1L))
            .thenReturn(Optional.of(invoice));

    when(paymentRepository.getTotalCompletedPaymentsByInvoiceId(1L))
            .thenReturn(0.0, 300.0);

    when(paymentRepository.save(existingPayment))
            .thenReturn(existingPayment);

    Payment result = paymentService.updatePayment(10L, updatedPayment);

    assertNotNull(result);
    assertEquals(300.0, result.getAmount());
    assertEquals("completed", result.getStatus());
    assertEquals("UPI", result.getPaymentMethod());
    assertEquals("PARTIALLY_PAID", invoice.getStatus());

    verify(paymentRepository).save(existingPayment);
    verify(invoiceRepository).save(invoice);
}

@Test
void deletePayment_shouldDeleteExistingPayment() {

    Invoice invoice = new Invoice();
    invoice.setId(1L);
    invoice.setAmount(1000.0);

    Payment payment = new Payment();
    payment.setId(10L);
    payment.setAmount(300.0);
    payment.setStatus("completed");
    payment.setInvoice(invoice);

    when(paymentRepository.findById(10L))
            .thenReturn(Optional.of(payment));

    when(paymentRepository.getTotalCompletedPaymentsByInvoiceId(1L))
            .thenReturn(0.0);

    paymentService.deletePayment(10L);

    verify(paymentRepository).delete(payment);
    verify(invoiceRepository).save(invoice);

    assertEquals("PENDING", invoice.getStatus());
}


@Test
void deletePayment_shouldDoNothingWhenPaymentDoesNotExist() {

    when(paymentRepository.findById(999L))
            .thenReturn(Optional.empty());

    paymentService.deletePayment(999L);

    verify(paymentRepository).findById(999L);
    verify(paymentRepository, never()).delete(any(Payment.class));
    verify(invoiceRepository, never()).save(any(Invoice.class));
}


@Test
void getAllPayments_shouldReturnEmptyListWhenNoPaymentsExist() {

    when(paymentRepository.findAll())
            .thenReturn(java.util.Collections.emptyList());

    java.util.List<Payment> result = paymentService.getAllPayments();

    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(paymentRepository).findAll();
}



@Test
void createPayment_shouldSetInvoiceStatusToPaidWhenFullyPaid() {

    Invoice invoice = new Invoice();
    invoice.setId(20L);
    invoice.setAmount(1000.0);

    Payment payment = new Payment();
    payment.setAmount(1000.0);
    payment.setStatus("completed");
    payment.setInvoice(invoice);

    when(invoiceRepository.findById(20L))
            .thenReturn(Optional.of(invoice));
    when(paymentRepository.getTotalCompletedPaymentsByInvoiceId(20L))
            .thenReturn(0.0, 1000.0);
    when(paymentRepository.save(payment)).thenReturn(payment);

    Payment result = paymentService.createPayment(payment);

    assertNotNull(result);
    assertEquals("PAID", invoice.getStatus());
    verify(invoiceRepository).save(invoice);
}


@Test
void createPayment_shouldSetInvoiceStatusToPendingWhenPaymentIsNotCompleted() {

    Invoice invoice = new Invoice();
    invoice.setId(21L);
    invoice.setAmount(1000.0);

    Payment payment = new Payment();
    payment.setAmount(200.0);
    payment.setStatus("pending");
    payment.setInvoice(invoice);

    when(invoiceRepository.findById(21L))
            .thenReturn(Optional.of(invoice));
    when(paymentRepository.getTotalCompletedPaymentsByInvoiceId(21L))
            .thenReturn(0.0, 0.0);
    when(paymentRepository.save(payment)).thenReturn(payment);

    Payment result = paymentService.createPayment(payment);

    assertNotNull(result);
    assertEquals("PENDING", invoice.getStatus());
}


@Test
void createPayment_shouldAllowPaymentExactlyEqualToRemainingBalance() {

    Invoice invoice = new Invoice();
    invoice.setId(22L);
    invoice.setAmount(1000.0);

    Payment payment = new Payment();
    payment.setAmount(200.0);
    payment.setStatus("completed");
    payment.setInvoice(invoice);

    when(invoiceRepository.findById(22L))
            .thenReturn(Optional.of(invoice));
    when(paymentRepository.getTotalCompletedPaymentsByInvoiceId(22L))
            .thenReturn(800.0, 1000.0);
    when(paymentRepository.save(payment)).thenReturn(payment);

    Payment result = paymentService.createPayment(payment);

    assertNotNull(result);
    assertEquals("PAID", invoice.getStatus());
}

@Test
void updatePayment_shouldRejectUpdateWhenTotalExceedsInvoiceAmount() {

    Invoice invoice = new Invoice();
    invoice.setId(23L);
    invoice.setAmount(1000.0);

    Payment existingPayment = new Payment();
    existingPayment.setId(30L);
    existingPayment.setAmount(200.0);
    existingPayment.setStatus("pending");
    existingPayment.setInvoice(invoice);

    Payment updatedPayment = new Payment();
    updatedPayment.setAmount(300.0);
    updatedPayment.setStatus("completed");
    updatedPayment.setInvoice(invoice);

    when(paymentRepository.findById(30L))
            .thenReturn(Optional.of(existingPayment));
    when(invoiceRepository.findById(23L))
            .thenReturn(Optional.of(invoice));
    when(paymentRepository.getTotalCompletedPaymentsByInvoiceId(23L))
            .thenReturn(800.0);

    assertThrows(BusinessRuleException.class,
            () -> paymentService.updatePayment(30L, updatedPayment));

    verify(paymentRepository, never()).save(any(Payment.class));
}


@Test
void updatePayment_shouldUpdateOldInvoiceStatusWhenInvoiceChanges() {

    Invoice oldInvoice = new Invoice();
    oldInvoice.setId(24L);
    oldInvoice.setAmount(1000.0);

    Invoice newInvoice = new Invoice();
    newInvoice.setId(25L);
    newInvoice.setAmount(500.0);

    Payment existingPayment = new Payment();
    existingPayment.setId(31L);
    existingPayment.setAmount(200.0);
    existingPayment.setStatus("completed");
    existingPayment.setInvoice(oldInvoice);

    Payment updatedPayment = new Payment();
    updatedPayment.setAmount(100.0);
    updatedPayment.setStatus("completed");
    updatedPayment.setInvoice(newInvoice);

    when(paymentRepository.findById(31L))
            .thenReturn(Optional.of(existingPayment));
    when(invoiceRepository.findById(25L))
            .thenReturn(Optional.of(newInvoice));
    when(paymentRepository.getTotalCompletedPaymentsByInvoiceId(25L))
            .thenReturn(0.0, 100.0);
    when(paymentRepository.getTotalCompletedPaymentsByInvoiceId(24L))
            .thenReturn(0.0);
    when(paymentRepository.save(existingPayment))
            .thenReturn(existingPayment);

    Payment result = paymentService.updatePayment(31L, updatedPayment);

    assertNotNull(result);
    assertEquals(newInvoice, result.getInvoice());
    assertEquals("PENDING", oldInvoice.getStatus());
    assertEquals("PARTIALLY_PAID", newInvoice.getStatus());

    verify(invoiceRepository).save(oldInvoice);
    verify(invoiceRepository).save(newInvoice);
}


@Test
void deletePayment_shouldSetInvoiceStatusToPartiallyPaidWhenBalanceRemains() {

    Invoice invoice = new Invoice();
    invoice.setId(26L);
    invoice.setAmount(1000.0);

    Payment payment = new Payment();
    payment.setId(32L);
    payment.setAmount(300.0);
    payment.setStatus("completed");
    payment.setInvoice(invoice);

    when(paymentRepository.findById(32L))
            .thenReturn(Optional.of(payment));
    when(paymentRepository.getTotalCompletedPaymentsByInvoiceId(26L))
            .thenReturn(500.0);

    paymentService.deletePayment(32L);

    assertEquals("PARTIALLY_PAID", invoice.getStatus());
    verify(paymentRepository).delete(payment);
    verify(invoiceRepository).save(invoice);
}


@Test
void createPayment_shouldRejectPaymentWhenTotalExceedsInvoiceAmountCaseInsensitive() {

    Invoice invoice = new Invoice();
    invoice.setId(27L);
    invoice.setAmount(1000.0);

    Payment payment = new Payment();
    payment.setAmount(200.0);
    payment.setStatus("COMPLETED");
    payment.setInvoice(invoice);

    when(invoiceRepository.findById(27L))
            .thenReturn(Optional.of(invoice));
    when(paymentRepository.getTotalCompletedPaymentsByInvoiceId(27L))
            .thenReturn(900.0);

    assertThrows(BusinessRuleException.class,
            () -> paymentService.createPayment(payment));

    verify(paymentRepository, never()).save(any(Payment.class));
}


@Test
void getPaymentById_shouldReturnCorrectPaymentAmount() {

    Payment payment = new Payment();
    payment.setId(40L);
    payment.setAmount(750.0);

    when(paymentRepository.findById(40L))
            .thenReturn(Optional.of(payment));

    Payment result = paymentService.getPaymentById(40L);

    assertNotNull(result);
    assertEquals(750.0, result.getAmount());
    verify(paymentRepository).findById(40L);
}


}
