package com.example.examenjava.controller;

import com.example.examenjava.domain.Payment;
import com.example.examenjava.domain.Status;
import com.example.examenjava.domain.Type;
import com.example.examenjava.dto.PaymentDto;
import com.example.examenjava.mapper.PaymentMapper;
import com.example.examenjava.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/payments")
@Validated
public class PaymentController {

    @Autowired
    PaymentMapper paymentMapper;

    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> create(@Valid @RequestBody PaymentDto request) {

        Payment payment = paymentService.create(paymentMapper.mapToEntity(request));
        return ResponseEntity.created(URI.create("/payments/" + payment.getId())).body(payment);

    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Payment> cancel(@PathVariable Integer id) {

        return ResponseEntity.ok(paymentService.cancel(id));

    }

    @GetMapping
    public List<Payment> get(@RequestParam(required = false) Type type,
                             @RequestParam(required = false) Status status) {

        return paymentService.get(type, status);

    }

}
