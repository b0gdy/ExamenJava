package com.example.examenjava.service;

import com.example.examenjava.domain.Payment;
import com.example.examenjava.domain.Status;
import com.example.examenjava.domain.Type;
import com.example.examenjava.exception.PaymentAlreadyCancelledException;
import com.example.examenjava.exception.PaymentNotFoundException;
import com.example.examenjava.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void create() {

        //arrange
        Payment payment = Payment.builder().type(Type.ONLINE).customer("customer1").amount(1.0).status(Status.NEW).build();
        Payment savedPayment = Payment.builder().id(1).type(Type.ONLINE).customer("customer1").amount(1.0).status(Status.NEW).build();

        when(paymentRepository.save(payment)).thenReturn(savedPayment);

        //act
        Payment result = paymentService.create(payment);

        //assert
        assertNotNull(result);
        assertEquals(savedPayment.getId(), result.getId());
        assertEquals(savedPayment.getType(), result.getType());
        assertEquals(savedPayment.getCustomer(), result.getCustomer());
        assertEquals(savedPayment.getAmount(), result.getAmount());
        assertEquals(savedPayment.getStatus(), result.getStatus());

        verify(paymentRepository).save(payment);

    }

    @Test
    void cancel() {

        //arrange
        Payment oldPayment = Payment.builder().id(1).type(Type.ONLINE).customer("customer1").amount(1.0).status(Status.NEW).build();
        Payment newPayment = Payment.builder().id(1).type(Type.ONLINE).customer("customer1").amount(1.0).status(Status.CANCELLED).build();
        Integer id = 1;

        when(paymentRepository.findById(id)).thenReturn(Optional.of(oldPayment));
        when(paymentRepository.save(newPayment)).thenReturn(newPayment);

        //act
        Payment result = paymentService.cancel(id);

        //assert
        assertNotNull(result);
        assertEquals(newPayment.getId(), result.getId());
        assertEquals(newPayment.getType(), result.getType());
        assertEquals(newPayment.getCustomer(), result.getCustomer());
        assertEquals(newPayment.getAmount(), result.getAmount());
        assertEquals(newPayment.getStatus(), result.getStatus());

        verify(paymentRepository).findById(id);
        verify(paymentRepository).save(newPayment);

    }

    @Test
    void cancelPaymentNotFoundException() {

        //arrange
        Payment oldPayment = Payment.builder().id(1).type(Type.ONLINE).customer("customer1").amount(1.0).status(Status.NEW).build();
        Payment newPayment = Payment.builder().id(2).type(Type.ONLINE).customer("customer1").amount(1.0).status(Status.CANCELLED).build();
        Integer id = 2;

        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        //act
        PaymentNotFoundException exception = assertThrows(PaymentNotFoundException.class, () -> paymentService.cancel(id));

        //assert
        assertNotNull(exception);
        assertEquals("The payment does not exist", exception.getMessage());

        verify(paymentRepository).findById(id);
        verify(paymentRepository, times(0)).save(newPayment);

    }

    @Test
    void cancelPaymentAlreadyCancelledException() {

        //arrange
        Payment oldPayment = Payment.builder().id(1).type(Type.ONLINE).customer("customer1").amount(1.0).status(Status.CANCELLED).build();
        Payment newPayment = Payment.builder().id(1).type(Type.ONLINE).customer("customer1").amount(1.0).status(Status.CANCELLED).build();
        Integer id = 1;

        when(paymentRepository.findById(id)).thenReturn(Optional.of(oldPayment));

        //act
        PaymentAlreadyCancelledException exception = assertThrows(PaymentAlreadyCancelledException.class, () -> paymentService.cancel(id));

        //assert
        assertNotNull(exception);
        assertEquals("The payment is already cancelled", exception.getMessage());

        verify(paymentRepository).findById(id);
        verify(paymentRepository, times(0)).save(newPayment);

    }

    @Test
    void getPaymentByTypeAndStatus() {

        //arrange
        Payment payment = Payment.builder().id(1).type(Type.ONLINE).customer("customer1").amount(1.0).status(Status.NEW).build();
        Type type = Type.ONLINE;
        Status status = Status.NEW;
        when(paymentRepository.findPaymentByTypeAndStatus(type, status)).thenReturn(List.of(payment));

        //act
        List<Payment> result = paymentService.get(type, status);

        //assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(payment, result.get(0));

        verify(paymentRepository).findPaymentByTypeAndStatus(type, status);
        verify(paymentRepository, never()).findPaymentByType(any());
        verify(paymentRepository, never()).findPaymentByStatus(any());
        verify(paymentRepository, never()).findAll();

    }

    @Test
    void getPaymentByType() {

        //arrange
        Payment payment = Payment.builder().id(1).type(Type.ONLINE).customer("customer1").amount(1.0).status(Status.NEW).build();
        Type type = Type.ONLINE;
        when(paymentRepository.findPaymentByType(type)).thenReturn(List.of(payment));

        //act
        List<Payment> result = paymentService.get(type, null);

        //assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(payment, result.get(0));

        verify(paymentRepository, never()).findPaymentByTypeAndStatus(any(), any());
        verify(paymentRepository).findPaymentByType(type);
        verify(paymentRepository, never()).findPaymentByStatus(any());
        verify(paymentRepository, never()).findAll();

    }

    @Test
    void getPaymentByStatus() {

        //arrange
        Payment payment = Payment.builder().id(1).type(Type.ONLINE).customer("customer1").amount(1.0).status(Status.NEW).build();
        Status status = Status.NEW;
        when(paymentRepository.findPaymentByStatus(status)).thenReturn(List.of(payment));

        //act
        List<Payment> result = paymentService.get(null, status);

        //assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(payment, result.get(0));

        verify(paymentRepository, never()).findPaymentByTypeAndStatus(any(), any());
        verify(paymentRepository, never()).findPaymentByType(any());
        verify(paymentRepository).findPaymentByStatus(status);
        verify(paymentRepository, never()).findAll();

    }

    @Test
    void getAll() {

        //arrange
        Payment payment = Payment.builder().id(1).type(Type.ONLINE).customer("customer1").amount(1.0).status(Status.NEW).build();
        when(paymentRepository.findAll()).thenReturn(List.of(payment));

        //act
        List<Payment> result = paymentService.get(null, null);

        //assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(payment, result.get(0));

        verify(paymentRepository, never()).findPaymentByTypeAndStatus(any(), any());
        verify(paymentRepository, never()).findPaymentByType(any());
        verify(paymentRepository, never()).findPaymentByStatus(any());
        verify(paymentRepository).findAll();

    }

}