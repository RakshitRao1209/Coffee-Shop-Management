package au.com.cba.CoffeeShopManagement_api.services;

import au.com.cba.CoffeeShopManagement_api.exceptions.CustomerNotFoundException;
import au.com.cba.CoffeeShopManagement_api.models.Customer;
import au.com.cba.CoffeeShopManagement_api.models.Orders;
import au.com.cba.CoffeeShopManagement_api.models.Orders;
import au.com.cba.CoffeeShopManagement_api.repositories.CustomerRepository;
import au.com.cba.CoffeeShopManagement_api.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    //private static List<Customer> customers = new ArrayList<>();
    //private int count=3;



    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        customer.setCustomerName(customerDetails.getCustomerName());
        customer.setCustomerEmail(customerDetails.getCustomerEmail());
        customer.setCustomerPhone(customerDetails.getCustomerPhone());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }

    public List<Orders> getCustomerOrders(Long customerId) {
        Customer customer = getCustomerById(customerId);
        return customer.getOrders();
    }
}

