package au.com.cba.CoffeeShopManagement_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuItemId;
    @NotNull
    private String menuItemName;
    @NotNull
    private double menuItemPrice;
    @NotNull
    private String menuItemType;

    private boolean active = true;

    public MenuItem() {
    }

    public MenuItem(long menuItemId, String menuItemName, double menuItemPrice, String menuItemType) {
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.menuItemPrice = menuItemPrice;
        this.menuItemType = menuItemType;
    }

    public long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public double getMenuItemPrice() {
        return menuItemPrice;
    }

    public void setMenuItemPrice(double menuItemPrice) {
        this.menuItemPrice = menuItemPrice;
    }

    public String getMenuItemType() {
        return menuItemType;
    }

    public void setMenuItemType(String menuItemType) {
        this.menuItemType = menuItemType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "menuItemId=" + menuItemId +
                ", menuItemName='" + menuItemName + '\'' +
                ", menuItemPrice=" + menuItemPrice +
                ", menuItemType='" + menuItemType + '\'' +
                '}';
    }
}

