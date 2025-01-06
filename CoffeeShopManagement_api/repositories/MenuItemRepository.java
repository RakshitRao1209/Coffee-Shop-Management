package au.com.cba.CoffeeShopManagement_api.repositories;

import au.com.cba.CoffeeShopManagement_api.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
