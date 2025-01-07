package com.cba.midweek;

public class SpecialtyItem extends MenuItem{

    private double discount;

    //Constructors
    public SpecialtyItem(String name, double price, int itemID, double discount) {
        super(name, price, itemID);
        this.discount = discount;
    }

    //Methods
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "SpecialtyItem{" +
                "name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", itemID=" + getItemID() +
                ", discount=" + discount +
                '}';
    }
}
