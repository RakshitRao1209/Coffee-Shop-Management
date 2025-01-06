package au.com.cba.CoffeeShopManagement_api.controllers;

import au.com.cba.CoffeeShopManagement_api.models.Orders;
import au.com.cba.CoffeeShopManagement_api.models.Payment;
import au.com.cba.CoffeeShopManagement_api.dtos.PaymentRequestDTO;
import au.com.cba.CoffeeShopManagement_api.repositories.OrderRepository;
import au.com.cba.CoffeeShopManagement_api.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        Orders order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getTotalAmount().compareTo(paymentRequest.getAmount()) != 0) {
            throw new RuntimeException("Payment amount does not match order total");
        }

        try {
            Payment payment = paymentService.processPayment(paymentRequest);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            // Log the exception and return an appropriate response
            System.err.println("Exception occurred while processing payment: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> paymentOpt = paymentService.getPaymentById(id);
        if (paymentOpt.isPresent()) {
            return ResponseEntity.ok(paymentOpt.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments(@RequestParam Optional<String> status) {
        List<Payment> payments = paymentService.getAllPayments(status);
        return ResponseEntity.ok(payments);
    }
}