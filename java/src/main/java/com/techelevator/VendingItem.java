package com.techelevator;

import java.math.BigDecimal;

public class VendingItem
{
    private String locationId;
    private String itemName;
    private BigDecimal price;
    private int inStockAmount;

    public VendingItem (String locationId, String itemName, BigDecimal price)
    {
        this.locationId = locationId;
        this.itemName = itemName;
        this.price = price;
        this.inStockAmount = 5;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getInStockAmount() {
        return inStockAmount;
    }

}
