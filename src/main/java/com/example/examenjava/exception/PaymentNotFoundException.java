package com.example.examenjava.exception;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException() {

        super("The payment does not exist");

    }

}
