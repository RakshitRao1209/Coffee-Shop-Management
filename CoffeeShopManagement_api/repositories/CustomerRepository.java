package au.com.cba.CoffeeShopManagement_api.repositories;

import au.com.cba.CoffeeShopManagement_api.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

