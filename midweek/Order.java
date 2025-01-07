package com.cba.midweek;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int orderID;
    private Customer customer;
    List<MenuItem> items;
    private double totalAmount;
    private LocalDate orderDate;
    //constructor

    public Order(int orderID, Customer customer) {
        this.orderID = orderID;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
        this.orderDate = LocalDate.now();
    }

    //methods


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void addItem(MenuItem item) {
        this.items.add(item);
        calculateTotalAmount();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    private void calculateTotalAmount() {
        this.totalAmount = items.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    // Method to validate the order
    public void validateOrder() throws InvalidOrderException {
        if (items.isEmpty()) {
            throw new InvalidOrderException("Order cannot be empty.");
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", customer=" + customer.getName() +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                '}';
    }

}
