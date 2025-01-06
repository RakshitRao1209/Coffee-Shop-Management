package au.com.cba.CoffeeShopManagement_api.controllers;

import au.com.cba.CoffeeShopManagement_api.dtos.OrderRequestDTO;
import au.com.cba.CoffeeShopManagement_api.models.Orders;
import au.com.cba.CoffeeShopManagement_api.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Orders> placeOrder(@RequestBody OrderRequestDTO orderRequest) {
        Orders order = orderService.placeOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    @GetMapping("{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Orders order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders(@RequestParam(required = false) String status) {
        List<Orders> orders;
        if (status != null) {
            orders = orderService.getOrdersByStatus(status);
        } else {
            orders = orderService.getAllOrders();
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("{id}/status")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Orders updatedOrder = orderService.updateOrderStatus(id, status);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}

