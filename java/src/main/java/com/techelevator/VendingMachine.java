package com.techelevator;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class VendingMachine
{
    public LinkedHashMap<String,VendingItem> inventor = new LinkedHashMap<String, VendingItem>();
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
        } catch (IOException e)
        {
            System.out.println("Vending Machine File Could Not Be Found Shutting Down...");
            System.exit(0);
        }
    }

    public boolean getCustomerMoney(String moneyInputString)
    {
        if (isNumeric(moneyInputString))
        {
            BigDecimal startingBal = userBalance;
            userBalance = userBalance.add(new BigDecimal(moneyInputString));
            logSale(startingBal,"FEED MONEY");
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
                if(inventor.get(userInput).getInStockAmount() != 0)
                {
                    BigDecimal startingBal = userBalance;
                    BigDecimal itemPrice = inventor.get(userInput).getPrice();
                    userBalance = userBalance.subtract(itemPrice);
                    inventor.get(userInput).itemIsPurchased();
                    System.out.println("You choose " + inventor.get(userInput).getItemName() + " at" + ANSI_RED + " $" + inventor.get(userInput).getPrice() + ANSI_RESET);
                    System.out.println(getSound(inventor.get(userInput).getItemType()));
                    System.out.println(ANSI_GREEN + "Your New Balance is $" + userBalance + ANSI_RESET);
                    logSale(startingBal,inventor.get(userInput).getItemName());
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
        switch (itemType)
        {
            case "Chip":
                return "Crunch Crunch, Yum!";
            case "Candy":
                return "Munch Munch, Yum!";
            case "Drink":
                return "Glug Glug, Yum!";
            case "Gum":
                return "Chew Chew, Yum!";
            default:
                return "THIS SNACK IS KINDA SUS";
        }
    }

    public void cashOut()
    {
        BigDecimal cashOutBalance = userBalance;
        System.out.println("Keep the change ya filthy animal! Your Change is: " + ANSI_GREEN + "$" + userBalance + ANSI_RESET);
        int cents = (int) Math.round(userBalance.doubleValue()*100);
        int[] changeDue = {cents/25,(cents%=25)/10, (cents%=10)/5, cents%5};
        System.out.println("In " + changeDue[0] + " Quarters | " + changeDue[1] + " Dimes | " + changeDue[2] + " Nickels." + " | " + changeDue[3] + " Pennies");
        userBalance = new BigDecimal(0.00);
        logSale(cashOutBalance,"GIVE CHANGE");
    }

    public void logSale(BigDecimal startBalance, String itemName)
    {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        LocalDateTime presentTime = LocalDateTime.now();
        startBalance =  startBalance.setScale(2, RoundingMode.DOWN);
        try {
            FileWriter newPrint = new FileWriter("Log.txt", true);
            newPrint.write("\n>" + timeFormat.format(presentTime) + " (" + itemName + ") $" + startBalance  + " $" + userBalance);
            newPrint.close();
        } catch (IOException e) {}
    }

    public boolean isNumeric(String moneyInput) {
        try
        {
            if(Integer.parseInt(moneyInput) > 0)
            {
                return true;
            }
        } catch (NumberFormatException e)
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
            if(item.getKey().charAt(0) == previousId)
            {
                System.out.print("("+ item.getKey() + ") " + itemName + ANSI_GREEN + " $" + item.getValue().getPrice() + ANSI_RESET + ANSI_BLUE + "  Stock: " +   ((item.getValue().getInStockAmount() == 0)? ANSI_RESET + ANSI_RED + "SOLD OUT " + ANSI_RESET :    item.getValue().getInStockAmount() + ANSI_RESET + "        "));            }
            else
            {
                System.out.print("\n("+ item.getKey() + ") " + itemName + ANSI_GREEN + " $" + item.getValue().getPrice() + ANSI_RESET + ANSI_BLUE + "  Stock: " + ((item.getValue().getInStockAmount() == 0)? ANSI_RESET + ANSI_RED + "SOLD OUT " + ANSI_RESET :    item.getValue().getInStockAmount() + ANSI_RESET + "        "));
            }
            previousId = item.getKey().charAt(0);
        }
        System.out.println(" ");
    }
}
