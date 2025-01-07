package com.cba.midweek;

public class OrderConsumer extends Thread{

    private final ConcurrentOrderProcessor orderProcessor;
    private volatile boolean running = true;

    public OrderConsumer(ConcurrentOrderProcessor orderProcessor) {
        this.orderProcessor = orderProcessor;
    }

    @Override
    public void run() {
        while (running) {
            try {
                synchronized (orderProcessor) {
                    while (orderProcessor.isEmpty()) {
                        orderProcessor.wait(); // Wait for orders
                    }
                    if (!running) {
                        break; // Exit if stopped
                    }
                    Order order = orderProcessor.processOrder();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Customer Exiting after Placing the Order");
            }
        }
    }

    public void stopConsumer() {
        running = false;
        this.interrupt();
    }
}
