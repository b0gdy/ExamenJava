package com.example.examenjava.repository;

import com.example.examenjava.domain.Payment;
import com.example.examenjava.domain.Status;
import com.example.examenjava.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findById(Integer integer);

    List<Payment> findPaymentByType(Type type);

    List<Payment> findPaymentByStatus(Status status);

    List<Payment> findPaymentByTypeAndStatus (Type type, Status status);

}
