package com.techelevator;

import java.math.BigDecimal;

public class VendingItem
{
    private String itemName;
    private BigDecimal price;
    private int inStockAmount;
    private String itemType;

    public VendingItem (String itemName, BigDecimal price, String itemType)
    {
        this.itemName = itemName;
        this.price = price;
        this.inStockAmount = 5;
        this.itemType = itemType;
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

    public void itemIsPurchased() {
        inStockAmount--;
    }

    public String getItemType()
    {
        return itemType;
    }
}
