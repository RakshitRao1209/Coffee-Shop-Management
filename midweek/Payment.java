package com.cba.midweek;

public interface Payment {

    void processPayment(double amount) throws PaymentException;
    void generateReceipt(Order order, Payment payment);
}
