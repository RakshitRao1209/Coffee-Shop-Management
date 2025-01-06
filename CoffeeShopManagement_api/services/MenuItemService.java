package au.com.cba.CoffeeShopManagement_api.services;

import au.com.cba.CoffeeShopManagement_api.exceptions.MenuItemNotFoundException;
import au.com.cba.CoffeeShopManagement_api.models.MenuItem;
import au.com.cba.CoffeeShopManagement_api.repositories.MenuItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Menu item not found"));
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Transactional
    public MenuItem updateMenuItem(Long id, MenuItem menuItemDetails) {
        Optional<MenuItem> menuItemOpt = menuItemRepository.findById(id);
        if (!menuItemOpt.isPresent()) {
            throw new RuntimeException("Menu item not found");
        }

        MenuItem menuItem = menuItemOpt.get();
        menuItem.setMenuItemName(menuItemDetails.getMenuItemName());
        menuItem.setMenuItemPrice(menuItemDetails.getMenuItemPrice());
        menuItem.setMenuItemType(menuItemDetails.getMenuItemType());

        return menuItemRepository.save(menuItem);
    }

    /*public MenuItem updateMenuItem(Long id, MenuItem menuItemDetails) {
        MenuItem menuItem = getMenuItemById(id);
        menuItem.setMenuItemId(menuItemDetails.getMenuItemId());
        menuItem.setMenuItemPrice(menuItemDetails.getMenuItemPrice());
        menuItem.setMenuItemType(menuItemDetails.getMenuItemType());
        return menuItemRepository.save(menuItem);
    }*/

    @Transactional
    public void deleteMenuItem(Long id) {
        Optional<MenuItem> menuItemOpt = menuItemRepository.findById(id);
        if (!menuItemOpt.isPresent()) {
            throw new RuntimeException("Menu item not found");
        }

        MenuItem menuItem = menuItemOpt.get();
        menuItem.setActive(false);

        menuItemRepository.save(menuItem);
    }

    /*public void deleteMenuItem(Long id) {
        MenuItem menuItem = getMenuItemById(id);
        menuItemRepository.delete(menuItem);
    }*/
}
