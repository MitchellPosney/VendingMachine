package com.techelevator;

public class VendingItem {
    private String location;
    private String itemName;
    private double price;

    public VendingItem (String location, String itemName, double price) {
        this.location = location;
        this.itemName = itemName;
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
