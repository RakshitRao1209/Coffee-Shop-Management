package com.cba.midweek;

public class OrderProducer extends Thread{
    private final ConcurrentOrderProcessor orderProcessor;
    private final Customer customer;
    private volatile boolean running = true;

    public OrderProducer(ConcurrentOrderProcessor orderProcessor, Customer customer) {
        this.orderProcessor = orderProcessor;
        this.customer = customer;
    }

    @Override
    public void run() {
        while (running) {
            try {
                synchronized (orderProcessor) {
                    Order order = new Order(customer.getOrderHistory().size() + 1, customer);
                    orderProcessor.processOrder(order);
                    orderProcessor.notify(); // Notify the consumer
                }
                Thread.sleep(1000); // Simulate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Customer Logged Out after Placing the Order");
            }
        }
    }

    public void stopProducer(){
        running = false;
        System.out.println("Customer Logged Out after Placing the Order");
    }
}
