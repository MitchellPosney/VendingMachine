package com.techelevator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine
{
    public LinkedHashMap<String,VendingItem> inventor = new LinkedHashMap<String, VendingItem>();
    private Scanner scanner = new Scanner(System.in);
    public BigDecimal userBalance;


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

    public BigDecimal getCustomerMoney()
    {
        boolean validResponse = false;
        BigDecimal customerMoney =  new BigDecimal(0);

        while (validResponse == false) {
            System.out.print("Please enter your money: ");
            String moneyInputString = scanner.nextLine();
            if (isNumeric(moneyInputString)) {
                customerMoney = new BigDecimal(moneyInputString);
                return customerMoney;
            }
        }
        System.out.println("$" + customerMoney + ", Great!\nLet's get some snacks!");
        return customerMoney;
    }

    public boolean isNumeric(String moneyInput) {
        try
        {
            if(moneyInput == null || moneyInput.equals("") || moneyInput.equals("0")) {
                return false;
            }
        } catch (NumberFormatException e) {}
        return true;
    }


    public void purchaseProcess()
    {
        System.out.println("Which item do you want");
        String userInput = scanner.nextLine();
        if(inventor.containsKey(userInput))
        {
            if(userBalance.doubleValue() - inventor.get(userInput).getPrice().doubleValue()  > 0.00)
            {
                double temp = userBalance.doubleValue();
                double temp2 = inventor.get(userInput).getPrice().doubleValue();
                userBalance = new BigDecimal(temp - temp2);
                System.out.println("You choose " + inventor.get(userInput).getItemName());
                System.out.println("Your change is " + userBalance);
            }
            else
            {
                System.out.println("Not Enough Money");
            }
        }
        else
        {
            System.out.println("Not A Valid Location");
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
                System.out.print("|"+ item.getValue().getLocationId() + " " + itemName + " $" + item.getValue().getPrice()  + "  InStock: " + item.getValue().getInStockAmount() + " " );
            }
            else
            {
                System.out.print("\n|"+ item.getValue().getLocationId() + " " + itemName + " $" + item.getValue().getPrice()  + "  InStock: " + item.getValue().getInStockAmount() + " " );
            }
            previousId = item.getValue().getLocationId().charAt(0);
        }

    }







}
