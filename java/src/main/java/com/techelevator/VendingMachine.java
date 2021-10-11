package com.techelevator;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class VendingMachine
{
    public LinkedHashMap<String,VendingItem> inventor = new LinkedHashMap<String, VendingItem>();
    public LinkedHashMap<String,String> itemSounds = new LinkedHashMap<String, String>();
    public BigDecimal userBalance = new BigDecimal(0.00);
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

    public VendingMachine()
    {
        // Gets stock of items from Inventory File
        try
        {
            File inventoryTemp = new File("vendingmachine.csv");
            BufferedReader reader = new BufferedReader(new FileReader(inventoryTemp));
            String line = reader.readLine();
            while (line != null)
            {
                String[] invSegments = line.split("\\|");
                inventor.put(invSegments[0] ,new VendingItem(invSegments[1],new BigDecimal(invSegments[2]),invSegments[3]));
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            System.out.println("Vending Machine File Could Not Be Found Shutting Down...");
            System.exit(0);
        }
        //Gets the item sounds
        try
        {
            File inventoryTemp = new File("ItemSound.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inventoryTemp));
            String line = reader.readLine();
            while (line != null)
            {
                String[] invSegments = line.split("\\|");
                itemSounds.put(invSegments[0], invSegments[1]);
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            System.out.println("Item Sound File Could Not Be Found Shutting Down...");
            System.exit(0);
        }
    }

    public boolean getCustomerMoney(String moneyInputString)
    {
        if (isNumeric(moneyInputString))
        {
            BigDecimal startingBal = userBalance;
            userBalance = userBalance.add(new BigDecimal(moneyInputString));
            logData(startingBal,"FEED MONEY");
            userBalance.setScale(2, RoundingMode.HALF_UP);
            System.out.println(ANSI_GREEN + "$" + userBalance + ANSI_RESET + ", Great! Let's get some snacks!");
            return true;
        }
        return false;
    }

    public boolean itemSelectionProcess(String userInput)
    {
        userInput = userInput.toUpperCase(Locale.ROOT);
        if(inventor.containsKey(userInput))
        {
            if(userBalance.doubleValue() - inventor.get(userInput).getPrice().doubleValue() > 0.00)
            {
                if(inventor.get(userInput).getInStockAmount() > 0)
                {
                    BigDecimal startingBal = userBalance;
                    BigDecimal itemPrice = inventor.get(userInput).getPrice();
                    userBalance = userBalance.subtract(itemPrice);
                    inventor.get(userInput).itemIsPurchased();
                    System.out.println("You choose " + inventor.get(userInput).getItemName() + " at" + ANSI_RED + " $" + inventor.get(userInput).getPrice() + ANSI_RESET);
                    System.out.println(getSound(inventor.get(userInput).getItemType()));
                    System.out.println(ANSI_GREEN + "Your New Balance is $" + userBalance + ANSI_RESET);
                    logData(startingBal,inventor.get(userInput).getItemName());
                    return true;
                }
                else
                {
                    System.out.println("Item is SOLD OUT!");
                }
            }
            else
            {
                System.out.println("Not Enough Money");
                return true;
            }
        }
        else
        {
            System.out.println("Not A Valid Location");
        }
        return false;
    }

    public String getSound(String itemType)
    {
       if(itemSounds.containsKey(itemType))
       {
           return itemSounds.get(itemType);
       }
       else
       {
           return "THIS SNACK IS KINDA SUS";
       }
    }

    public void cashOut() {
        BigDecimal cashOutBalance = userBalance;
        BigDecimal changeBalance = userBalance;
        System.out.println("Keep the change ya filthy animal! Your Change is: " + ANSI_GREEN + "$" + userBalance + ANSI_RESET);

        BigInteger[] changeDue = {new BigInteger("0"), new BigInteger("0"), new BigInteger("0"), new BigInteger("0")};
        //Quarters
        changeDue[0] = (changeBalance.divide(new BigDecimal("0.25"))).toBigInteger();
        changeBalance = changeBalance.subtract(new BigDecimal("0.25").multiply(new BigDecimal(changeDue[0])));
        //Dimes
        changeDue[1] = changeBalance.remainder(new BigDecimal("0.25")).divide(BigDecimal.valueOf(0.10)).toBigInteger();
        changeBalance = changeBalance.subtract(new BigDecimal("0.10").multiply(new BigDecimal(changeDue[1])));
        //Nickels
        changeDue[2] = changeBalance.remainder(new BigDecimal("0.10")).divide(BigDecimal.valueOf(0.05)).toBigInteger();
        changeBalance = changeBalance.subtract(new BigDecimal("0.05").multiply(new BigDecimal(changeDue[2])));
        //Pennies
        while (changeBalance.compareTo(BigDecimal.valueOf(0.01)) == 1 || changeBalance.compareTo(BigDecimal.valueOf(0.01)) == 0)
        {
            changeBalance = changeBalance.subtract(BigDecimal.valueOf(0.01));
            changeDue[3] = changeDue[3].add(new BigInteger("1"));
        }
        System.out.println("In " + changeDue[0] + " Quarters | " + changeDue[1] + " Dimes | " + changeDue[2] + " Nickels | " + changeDue[3] + " Pennies");
        userBalance = new BigDecimal(0.00);
        logData(cashOutBalance,"GIVE CHANGE");
    }

    public void logData(BigDecimal startBalance, String itemName)
    {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        LocalDateTime presentTime = LocalDateTime.now();
        startBalance =  startBalance.setScale(2, RoundingMode.DOWN);
        try
        {
            FileWriter newPrint = new FileWriter("Log.txt", true);
            newPrint.write("\n>" + timeFormat.format(presentTime) + " (" + itemName + ") $" + startBalance  + " $" + userBalance);
            newPrint.close();
        } catch (IOException e) {}
    }

    public boolean isNumeric(String moneyInput)
    {
        try
        {
            if(new BigInteger(moneyInput).compareTo(BigInteger.valueOf(0)) == 1)
            {
                try
                {
                    BigDecimal maxBigDecimalSize = new BigDecimal(moneyInput).add(userBalance);
                }
                catch (Exception e)
                {
                    System.out.println("You have reached the limit spend some money first");
                    return true;
                }
                return true;
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println(moneyInput + " is not a proper value.");
        }
        return false;
    }

    public void printVendingContents()
    {
        char previousId = 'A';
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
            System.out.print(((item.getKey().charAt(0) == previousId)? "" : "\n")
                    + "("+ item.getKey() + ") " + itemName + ANSI_GREEN + " $" + item.getValue().getPrice() + ANSI_RESET + ANSI_BLUE + "  Stock: "
                    + ((item.getValue().getInStockAmount() == 0)? ANSI_RESET + ANSI_RED + "SOLD OUT " + ANSI_RESET : item.getValue().getInStockAmount() + ANSI_RESET + "        "));
            previousId = item.getKey().charAt(0);
        }
        System.out.println(" ");
    }
}