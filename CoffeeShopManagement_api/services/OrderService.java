package au.com.cba.CoffeeShopManagement_api.services;

import au.com.cba.CoffeeShopManagement_api.dtos.OrderRequestDTO;
import au.com.cba.CoffeeShopManagement_api.exceptions.OrderNotFoundException;
import au.com.cba.CoffeeShopManagement_api.models.*;
import au.com.cba.CoffeeShopManagement_api.models.Orders;
import au.com.cba.CoffeeShopManagement_api.repositories.CustomerRepository;
import au.com.cba.CoffeeShopManagement_api.repositories.MenuItemRepository;
import au.com.cba.CoffeeShopManagement_api.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Transactional
    public Orders placeOrder(OrderRequestDTO orderRequest) {
        Optional<Customer> customerOpt = customerRepository.findById(orderRequest.getCustomerId());
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Customer not found");
        }

        Customer customer = customerOpt.get();
        List<MenuItem> items = menuItemRepository.findAllById(orderRequest.getItemIds());

        double totalAmount = items.stream().mapToDouble(MenuItem::getMenuItemPrice).sum();

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setItems(items);
        order.setTotalAmount(totalAmount);
        order.setOrderStatus("pending");

        return orderRepository.save(order);
    }

    /*public Orders placeOrder(Long customerId, List<Long> itemIds) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        List<MenuItem> items = menuItemRepository.findAllById(itemIds);
        if (items.isEmpty()) {
            throw new InvalidOrderException("Order must contain at least one menu item");
        }

        double totalAmount = items.stream().mapToDouble(MenuItem::getMenuItemPrice).sum();

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setItems(items);
        order.setTotalAmount(totalAmount);
        order.setOrderStatus("pending");

        return orderRepository.save(order);
    }*/

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Orders> getOrdersByStatus(String status) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getOrderStatus().equalsIgnoreCase(status))
                .toList();
    }

    public Orders updateOrderStatus(Long id, String status) {
        Orders order = getOrderById(id);
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }
}

