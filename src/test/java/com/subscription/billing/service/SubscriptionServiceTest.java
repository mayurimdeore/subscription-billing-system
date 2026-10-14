
package com.subscription.billing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.subscription.billing.entity.Customer;
import com.subscription.billing.entity.Subscription;
import com.subscription.billing.entity.SubscriptionPlan;
import com.subscription.billing.exception.BusinessRuleException;
import com.subscription.billing.repository.CustomerRepository;
import com.subscription.billing.repository.SubscriptionPlanRepository;
import com.subscription.billing.repository.SubscriptionRepository;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private Customer customer;
    private SubscriptionPlan plan;
    private Subscription subscription;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);

        plan = new SubscriptionPlan();
        plan.setId(2L);

        subscription = new Subscription();
        subscription.setStartDate(LocalDate.of(2026, 1, 1));
        subscription.setEndDate(LocalDate.of(2026, 12, 31));
        subscription.setStatus("ACTIVE");
        subscription.setCustomer(customer);
        subscription.setSubscriptionPlan(plan);
    }
    

@Test
void getAllSubscriptions_shouldReturnAllSubscriptions() {
    Subscription secondSubscription = new Subscription();

    when(subscriptionRepository.findAll())
            .thenReturn(Arrays.asList(subscription, secondSubscription));

    var result = subscriptionService.getAllSubscriptions();

    assertEquals(2, result.size());
    assertSame(subscription, result.get(0));
    verify(subscriptionRepository).findAll();
}

@Test
void getAllSubscriptions_shouldReturnEmptyListWhenNoSubscriptionsExist() {
    when(subscriptionRepository.findAll())
            .thenReturn(Collections.emptyList());

    var result = subscriptionService.getAllSubscriptions();

    assertTrue(result.isEmpty());
    verify(subscriptionRepository).findAll();
}

@Test
void getSubscriptionById_shouldReturnSubscriptionWhenFound() {
    when(subscriptionRepository.findById(1L))
            .thenReturn(Optional.of(subscription));

    Subscription result = subscriptionService.getSubscriptionById(1L);

    assertNotNull(result);
    assertSame(subscription, result);
    verify(subscriptionRepository).findById(1L);
}

@Test
void getSubscriptionById_shouldReturnNullWhenNotFound() {
    when(subscriptionRepository.findById(99L))
            .thenReturn(Optional.empty());

    Subscription result = subscriptionService.getSubscriptionById(99L);

    assertNull(result);
    verify(subscriptionRepository).findById(99L);
}


@Test
void createSubscription_shouldSaveValidSubscription() {
    when(customerRepository.findById(1L))
            .thenReturn(Optional.of(customer));
    when(subscriptionPlanRepository.findById(2L))
            .thenReturn(Optional.of(plan));
    when(subscriptionRepository.save(subscription))
            .thenReturn(subscription);

    Subscription result = subscriptionService.createSubscription(subscription);

    assertNotNull(result);
    assertSame(subscription, result);
    assertSame(customer, result.getCustomer());
    assertSame(plan, result.getSubscriptionPlan());
    verify(subscriptionRepository).save(subscription);
}

@Test
void createSubscription_shouldReturnNullWhenCustomerDoesNotExist() {
    when(customerRepository.findById(1L))
            .thenReturn(Optional.empty());
    when(subscriptionPlanRepository.findById(2L))
            .thenReturn(Optional.of(plan));

    Subscription result = subscriptionService.createSubscription(subscription);

    assertNull(result);
    verify(subscriptionRepository, never()).save(any(Subscription.class));
}

@Test
void createSubscription_shouldReturnNullWhenPlanDoesNotExist() {
    when(customerRepository.findById(1L))
            .thenReturn(Optional.of(customer));
    when(subscriptionPlanRepository.findById(2L))
            .thenReturn(Optional.empty());

    Subscription result = subscriptionService.createSubscription(subscription);

    assertNull(result);
    verify(subscriptionRepository, never()).save(any(Subscription.class));
}

@Test
void createSubscription_shouldThrowExceptionWhenEndDateIsBeforeStartDate() {
    subscription.setStartDate(LocalDate.of(2026, 12, 31));
    subscription.setEndDate(LocalDate.of(2026, 1, 1));

    assertThrows(BusinessRuleException.class,
            () -> subscriptionService.createSubscription(subscription));

    verify(subscriptionRepository, never()).save(any(Subscription.class));
    verifyNoInteractions(customerRepository, subscriptionPlanRepository);
}


@Test
void updateSubscription_shouldUpdateExistingSubscription() {
    when(subscriptionRepository.findById(10L))
            .thenReturn(Optional.of(subscription));
    when(customerRepository.findById(1L))
            .thenReturn(Optional.of(customer));
    when(subscriptionPlanRepository.findById(2L))
            .thenReturn(Optional.of(plan));
    when(subscriptionRepository.save(subscription))
            .thenReturn(subscription);

    Subscription result =
            subscriptionService.updateSubscription(10L, subscription);

    assertNotNull(result);
    assertSame(subscription, result);
    assertEquals("ACTIVE", result.getStatus());
    verify(subscriptionRepository).save(subscription);
}

@Test
void updateSubscription_shouldReturnNullWhenSubscriptionDoesNotExist() {
    when(subscriptionRepository.findById(99L))
            .thenReturn(Optional.empty());

    Subscription result =
            subscriptionService.updateSubscription(99L, subscription);

    assertNull(result);
    verify(subscriptionRepository, never()).save(any(Subscription.class));
}

@Test
void updateSubscription_shouldReturnNullWhenCustomerDoesNotExist() {
    when(subscriptionRepository.findById(10L))
            .thenReturn(Optional.of(subscription));
    when(customerRepository.findById(1L))
            .thenReturn(Optional.empty());
    when(subscriptionPlanRepository.findById(2L))
            .thenReturn(Optional.of(plan));

    Subscription result =
            subscriptionService.updateSubscription(10L, subscription);

    assertNull(result);
    verify(subscriptionRepository, never()).save(any(Subscription.class));
}

@Test
void updateSubscription_shouldReturnNullWhenPlanDoesNotExist() {
    when(subscriptionRepository.findById(10L))
            .thenReturn(Optional.of(subscription));
    when(customerRepository.findById(1L))
            .thenReturn(Optional.of(customer));
    when(subscriptionPlanRepository.findById(2L))
            .thenReturn(Optional.empty());

    Subscription result =
            subscriptionService.updateSubscription(10L, subscription);

    assertNull(result);
    verify(subscriptionRepository, never()).save(any(Subscription.class));
}

@Test
void updateSubscription_shouldThrowExceptionWhenEndDateIsBeforeStartDate() {
    subscription.setStartDate(LocalDate.of(2026, 12, 31));
    subscription.setEndDate(LocalDate.of(2026, 1, 1));

    assertThrows(BusinessRuleException.class,
            () -> subscriptionService.updateSubscription(10L, subscription));

    verifyNoInteractions(subscriptionRepository,
            customerRepository, subscriptionPlanRepository);
}

@Test
void updateSubscription_shouldUpdateDatesAndStatus() {
    Subscription existing = new Subscription();
    existing.setStartDate(LocalDate.of(2025, 1, 1));
    existing.setEndDate(LocalDate.of(2025, 6, 30));
    existing.setStatus("EXPIRED");

    subscription.setStartDate(LocalDate.of(2026, 2, 1));
    subscription.setEndDate(LocalDate.of(2026, 8, 31));
    subscription.setStatus("ACTIVE");

    when(subscriptionRepository.findById(10L))
            .thenReturn(Optional.of(existing));
    when(customerRepository.findById(1L))
            .thenReturn(Optional.of(customer));
    when(subscriptionPlanRepository.findById(2L))
            .thenReturn(Optional.of(plan));
    when(subscriptionRepository.save(existing))
            .thenReturn(existing);

    Subscription result =
            subscriptionService.updateSubscription(10L, subscription);

    assertNotNull(result);
    assertEquals(LocalDate.of(2026, 2, 1), result.getStartDate());
    assertEquals(LocalDate.of(2026, 8, 31), result.getEndDate());
    assertEquals("ACTIVE", result.getStatus());
    verify(subscriptionRepository).save(existing);
}


@Test
void deleteSubscription_shouldDeleteById() {
    subscriptionService.deleteSubscription(10L);

    verify(subscriptionRepository).deleteById(10L);
}

@Test
void deleteSubscription_shouldCallRepositoryForMissingId() {
    subscriptionService.deleteSubscription(99L);

    verify(subscriptionRepository).deleteById(99L);
}


@Test
void createSubscription_shouldAcceptEndDateEqualToStartDate() {
    subscription.setStartDate(LocalDate.of(2026, 5, 10));
    subscription.setEndDate(LocalDate.of(2026, 5, 10));

    when(customerRepository.findById(1L))
            .thenReturn(Optional.of(customer));
    when(subscriptionPlanRepository.findById(2L))
            .thenReturn(Optional.of(plan));
    when(subscriptionRepository.save(subscription))
            .thenReturn(subscription);

    Subscription result = subscriptionService.createSubscription(subscription);

    assertNotNull(result);
    verify(subscriptionRepository).save(subscription);
}

@Test
void updateSubscription_shouldAcceptEndDateEqualToStartDate() {
    subscription.setStartDate(LocalDate.of(2026, 5, 10));
    subscription.setEndDate(LocalDate.of(2026, 5, 10));

    when(subscriptionRepository.findById(10L))
            .thenReturn(Optional.of(subscription));
    when(customerRepository.findById(1L))
            .thenReturn(Optional.of(customer));
    when(subscriptionPlanRepository.findById(2L))
            .thenReturn(Optional.of(plan));
    when(subscriptionRepository.save(subscription))
            .thenReturn(subscription);

    Subscription result =
            subscriptionService.updateSubscription(10L, subscription);

    assertNotNull(result);
    verify(subscriptionRepository).save(subscription);
}

@Test
void createSubscription_shouldNotSaveWhenBothCustomerAndPlanAreMissing() {
    when(customerRepository.findById(1L))
            .thenReturn(Optional.empty());
    when(subscriptionPlanRepository.findById(2L))
            .thenReturn(Optional.empty());

    Subscription result = subscriptionService.createSubscription(subscription);

    assertNull(result);
    verify(subscriptionRepository, never()).save(any(Subscription.class));
}

@Test
void updateSubscription_shouldNotSaveWhenBothCustomerAndPlanAreMissing() {
    when(subscriptionRepository.findById(10L))
            .thenReturn(Optional.of(subscription));
    when(customerRepository.findById(1L))
            .thenReturn(Optional.empty());
    when(subscriptionPlanRepository.findById(2L))
            .thenReturn(Optional.empty());

    Subscription result =
            subscriptionService.updateSubscription(10L, subscription);

    assertNull(result);
    verify(subscriptionRepository, never()).save(any(Subscription.class));
}

}
