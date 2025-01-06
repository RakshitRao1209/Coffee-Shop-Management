package au.com.cba.CoffeeShopManagement_api.dtos;

import java.util.List;

public class OrderRequestDTO {
    private Long customerId;
    private List<Long> itemIds;

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }
}
