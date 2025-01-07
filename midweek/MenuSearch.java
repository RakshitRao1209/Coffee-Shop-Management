package com.cba.midweek;

import java.util.List;
import java.util.stream.Collectors;

public class MenuSearch {
    public static List<MenuItem> searchMenuItemsByKeyword(List<MenuItem> menuItems, String keyword) {
        return menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static List<MenuItem> filterMenuItemsByPriceRange(List<MenuItem> menuItems, double minPrice, double maxPrice) {
        return menuItems.stream()
                .filter(item -> item.getPrice() >= minPrice && item.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}
