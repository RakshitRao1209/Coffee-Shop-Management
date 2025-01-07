package com.cba.midweek;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private int customerID;
    List<Order> orderHistory;

    //Constructor

    public Customer(String name, int customerID) {
        this.name = name;
        this.customerID = customerID;
        this.orderHistory = new ArrayList<>();
    }

    //Methods


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

   public void addOrder(Order order){
        this.orderHistory.add(order);
   }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", customerID=" + customerID +
                ", orderHistory=" + orderHistory +
                '}';
    }
}
