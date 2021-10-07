package com.techelevator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class VendingMachine
{
    public ArrayList<VendingItem> inventory = new ArrayList<VendingItem>();

    public void parseInventory()
    {
        File inventoryTemp = new File("vendingmachine.csv");
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(inventoryTemp));
            String line = reader.readLine();
            while (line != null)
            {
                String[] invSegments = line.split("\\|");
                inventory.add(new VendingItem(invSegments[0],invSegments[1],new BigDecimal(invSegments[2])));
                line = reader.readLine();
            }
        } catch (IOException e)
        {
        }
        System.out.println("BreakLine");
        printVendingContents();
    }


    public void printVendingContents()
    {
        char previousId = inventory.get(0).getLocationId().charAt(0);
        int longestItemlength = 0;
        for(VendingItem item : inventory)
        {
            if(item.getItemName().length() > longestItemlength)
            {
                longestItemlength = item.getItemName().length();
            }
        }

        for(VendingItem item : inventory)
        {
            String itemName = item.getItemName() + (" ").repeat(longestItemlength - item.getItemName().length());
            if(item.getLocationId().charAt(0) == previousId)
            {
                System.out.print("|"+ item.getLocationId() + " " + itemName + " $" + item.getPrice()  +"  InStock: " + item.getInStockAmount() + " " );
            }
            else
            {
                System.out.print("\n|"+ item.getLocationId() + " " + itemName + " $" + item.getPrice()  +"  InStock: " + item.getInStockAmount() + " " );
            }
            previousId = item.getLocationId().charAt(0);
        }

    }
}
