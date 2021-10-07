package com.techelevator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;


public class VendingMachine
{
    public ArrayList<VendingItem> inventory = new ArrayList<VendingItem>();


    public void parseInventory()
    {
        File inventoryTemp = new File("vendingmachine.csv");;

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(inventoryTemp));
            String invLocation;
            String invItemName;
            BigDecimal invPrice;
            String line = reader.readLine();
            while (line != null)
            {
                String[] invSegments = line.split("\\|");
                invLocation = invSegments[0];
                invItemName = invSegments[1];
                invPrice = new BigDecimal(invSegments[2]);

                VendingItem object = new VendingItem(invLocation,invItemName,invPrice);
                inventory.add(object);
                line = reader.readLine();
            }
        } catch (IOException e)
        {
            System.out.println("There was an error " + e.toString());
        }
        System.out.println("BreakLine");
    }


    public void printVendingContents()
    {
        String previousId = inventory.get(0).getLocationId();
        for(VendingItem item: inventory)
        {

        }

    }
}
