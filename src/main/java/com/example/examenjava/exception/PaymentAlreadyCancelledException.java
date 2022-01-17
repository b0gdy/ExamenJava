package com.example.examenjava.exception;

public class PaymentAlreadyCancelledException extends RuntimeException {

    public PaymentAlreadyCancelledException() {

        super("The payment is already cancelled");

    }

}
