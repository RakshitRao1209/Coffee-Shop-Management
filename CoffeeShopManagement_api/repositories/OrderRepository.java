package au.com.cba.CoffeeShopManagement_api.repositories;

import au.com.cba.CoffeeShopManagement_api.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
