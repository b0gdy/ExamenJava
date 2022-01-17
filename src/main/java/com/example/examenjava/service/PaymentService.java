package com.example.examenjava.service;

import com.example.examenjava.domain.Payment;
import com.example.examenjava.domain.Status;
import com.example.examenjava.domain.Type;
import com.example.examenjava.exception.PaymentAlreadyCancelledException;
import com.example.examenjava.exception.PaymentNotFoundException;
import com.example.examenjava.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public Payment create(Payment payment) {

        Payment savedPayment = paymentRepository.save(payment);
        return savedPayment;

    }

    public Payment cancel(Integer id) {

        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException());
        if(payment.getStatus().equals(Status.CANCELLED))
            throw new PaymentAlreadyCancelledException();
        payment.setStatus(Status.CANCELLED);
        return paymentRepository.save(payment);

    }

    public List<Payment> get(Type type, Status status) {

        if(type != null) {
            if(status != null) {
                return paymentRepository.findPaymentByTypeAndStatus(type, status);
            }
            return paymentRepository.findPaymentByType(type);
        }
        if (status != null) {
            return paymentRepository.findPaymentByStatus(status);
        }
        return paymentRepository.findAll();

    }

}
