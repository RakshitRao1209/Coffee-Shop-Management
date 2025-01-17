package com.cba.midweek;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CashPayment implements Payment {

    @Override
    public void processPayment(double amount) throws PaymentException {
        // Simulate cash payment processing
        if (amount <= 0) {
            throw new PaymentException("Invalid payment amount.");
        }
        System.out.println("Cash payment of $" + amount + " is Successful");
        Logger.log("Cash payment of $" + amount + " is Successful");
    }

    @Override
    public void generateReceipt(Order order,Payment payment) {
        String receiptFile = "receipt_" + order.getOrderID() + ".txt";
        Logger.log("Generating receipt: " + receiptFile);
        try (FileWriter fw = new FileWriter(receiptFile);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println("Receipt for Order ID: " + order.getOrderID());
            Logger.log("Receipt for Order ID: " + order.getOrderID());
            pw.println("Customer: " + order.getCustomer().getName());
            Logger.log("Customer: " + order.getCustomer().getName());
            pw.println("Items Ordered:");
            Logger.log("Items Ordered:");
            for (MenuItem item : order.getItems()) {
                pw.println("- " + item.getName() + ": $" + item.getPrice());
                Logger.log("- " + item.getName() + ": $" + item.getPrice());
            }
            pw.println("Total Amount: $" + order.getTotalAmount());
            pw.println("Payment is Made Via Cash");
            Logger.log("Receipt generated: " + receiptFile);
        } catch (IOException e) {
            Logger.log("Failed to generate receipt: " + e.getMessage());
        }
    }
}
