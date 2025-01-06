package au.com.cba.CoffeeShopManagement_api.services;

import au.com.cba.CoffeeShopManagement_api.models.Orders;
import au.com.cba.CoffeeShopManagement_api.models.Payment;
import au.com.cba.CoffeeShopManagement_api.dtos.PaymentRequestDTO;
import au.com.cba.CoffeeShopManagement_api.repositories.OrderRepository;
import au.com.cba.CoffeeShopManagement_api.repositories.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Payment processPayment(PaymentRequestDTO paymentRequest) {
        try {
            // Validate the order
            Optional<Orders> orderOpt = orderRepository.findById(paymentRequest.getOrderId());
            if (!orderOpt.isPresent()) {
                throw new RuntimeException("Order not found");
            }

            Orders order = orderOpt.get();

            // Check if the payment amount matches the order total
            if (!order.getTotalAmount().equals(paymentRequest.getAmount())) {
                throw new RuntimeException("Payment amount does not match order total");
            }

            // Create and save the payment
            Payment payment = new Payment();
            payment.setOrderId(paymentRequest.getOrderId());
            payment.setAmount(paymentRequest.getAmount());
            payment.setPaymentType(paymentRequest.getPaymentType());
            payment.setStatus("successful");

            return paymentRepository.save(payment);
        } catch (Exception e) {
            // Log the exception and rethrow it
            System.err.println("Exception occurred while processing payment: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Payment> getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    public List<Payment> getAllPayments(Optional<String> status) {
        if (status.isPresent()) {
            return paymentRepository.findByStatus(status.get());
        } else {
            return paymentRepository.findAll();
        }
    }
}
