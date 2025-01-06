package au.com.cba.CoffeeShopManagement_api.repositories;

import au.com.cba.CoffeeShopManagement_api.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStatus(String status);
}