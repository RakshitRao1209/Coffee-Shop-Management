package com.cba.midweek;

import java.util.List;
import java.util.stream.Collectors;

public class DiscountCalculator {
    public static List<SpecialtyItem> applyDiscounts(List<SpecialtyItem> specialtyItems) {
        return specialtyItems.stream()
                .map(item -> {
                    double discountedPrice = item.getPrice() - (item.getPrice() * item.getDiscount() / 100);
                    item.setDiscount(discountedPrice);
                    return item;
                })
                .collect(Collectors.toList());
    }
}
