package com.techelevator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class VendingMachine
{
    public LinkedHashMap<String,VendingItem> inventor = new LinkedHashMap<String, VendingItem>();

    public Double userBalance = 5.30;


    public VendingMachine()
    {
        File inventoryTemp = new File("vendingmachine.csv");
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(inventoryTemp));
            String line = reader.readLine();
            while (line != null)
            {
                String[] invSegments = line.split("\\|");
                inventor.put(invSegments[0] ,new VendingItem(invSegments[0],invSegments[1],new BigDecimal(invSegments[2])));


                line = reader.readLine();
            }
        } catch (IOException e)
        {
        }
    }

    public void purchase()
    {

        String input = "A1";
        if(inventor.containsKey(input))
        {
            if(userBalance - inventor.get(input).getPrice().doubleValue() >= 0)
            {
                userBalance = userBalance - inventor.get(input).getPrice().doubleValue();
                System.out.println("You choose " + inventor.get(input).getItemName());
                System.out.println("Your change is " + userBalance);
            }

        }
    }




    public void printVendingContents()
    {

        char previousId = inventor.get("A1").getLocationId().charAt(0);
        int longestItemLength = 0;
        for(Map.Entry<String, VendingItem> item : inventor.entrySet())
        {
            if(item.getValue().getItemName().length() > longestItemLength)
            {
                longestItemLength = item.getValue().getItemName().length();
            }
        }

        for(Map.Entry<String, VendingItem> item : inventor.entrySet())
        {
            String itemName = item.getValue().getItemName() + (" ").repeat(longestItemLength - item.getValue().getItemName().length());
            if(item.getValue().getLocationId().charAt(0) == previousId)
            {
                System.out.print("|"+ item.getValue().getLocationId() + " " + itemName + " $" + item.getValue().getPrice()  +"  InStock: " + item.getValue().getInStockAmount() + " " );
            }
            else
            {
                System.out.print("\n|"+ item.getValue().getLocationId() + " " + itemName + " $" + item.getValue().getPrice()  +"  InStock: " + item.getValue().getInStockAmount() + " " );
            }
            previousId = item.getValue().getLocationId().charAt(0);
        }

    }







}
