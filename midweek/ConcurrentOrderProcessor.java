package com.cba.midweek;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConcurrentOrderProcessor {

    private final BlockingQueue<Order> orderQueue;


    public ConcurrentOrderProcessor(int capacity) {
        this.orderQueue = new LinkedBlockingQueue<>(capacity);
    }

    public void processOrder(Order order) throws InterruptedException {
        try {
            orderQueue.put(order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            System.out.println("Customer Logging Out after Placing the Order : " + order.getOrderID());
        }
    }

    public Order processOrder() throws InterruptedException {
        return orderQueue.take();
    }

    public boolean isEmpty() {
        return orderQueue.isEmpty();
    }
}
