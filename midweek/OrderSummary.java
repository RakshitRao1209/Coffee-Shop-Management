package com.cba.midweek;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OrderSummary {

    public static List<Order> filterOrdersByDate(List<Order> orders, LocalDate startDate, LocalDate endDate) {
        return orders.stream()
                .filter(order -> !order.getOrderDate().isBefore(startDate) && !order.getOrderDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    public static List<Order> filterOrdersByTotalAmount(List<Order> orders, double minAmount, double maxAmount) {
        return orders.stream()
                .filter(order -> order.getTotalAmount() >= minAmount && order.getTotalAmount() <= maxAmount)
                .collect(Collectors.toList());
    }

    public static void displayOrderSummary(List<Order> orders) {
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Customer: " + order.getCustomer().getName());
            System.out.println("Date: " + order.getOrderDate());
            System.out.println("Total Amount: $" + order.getTotalAmount());
            System.out.println("Items: ");
            for (MenuItem item : order.getItems()) {
                System.out.println(" - " + item.getName() + ": $" + item.getPrice());
            }
            System.out.println();
        }
    }
}
