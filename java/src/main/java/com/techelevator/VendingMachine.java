package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine
{
    public LinkedHashMap<String,VendingItem> inventor = new LinkedHashMap<String, VendingItem>();
    private Scanner scanner = new Scanner(System.in);
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

    public void getCustomerMoney()
    {
        boolean validResponse = false;
        while (!validResponse) {
            System.out.print("Please enter your money: ");
            String moneyInputString = scanner.nextLine();
            if (isNumeric(moneyInputString)) {
                userBalance = userBalance.add(new BigDecimal(moneyInputString));
                userBalance.setScale(2, RoundingMode.HALF_UP);
                break;
            }
        }
        System.out.println(ANSI_GREEN + "$" + userBalance + ANSI_RESET + ", Great! Let's get some snacks!");
    }

    public void itemSelectionProccess()
    {
        System.out.print("Which item do you want >>> ");
        String userInput = scanner.nextLine();
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
                    logSale(startingBal);
                }
                else
                {
                    System.out.println("Item is SOLD OUT!");
                }
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

    public void cashOut() {
        BigDecimal cashOutBalance = userBalance;
        BigDecimal quarter = new BigDecimal(.25);
        quarter = quarter.setScale(2, RoundingMode.FLOOR);
        BigDecimal dime = new BigDecimal(.10);
        dime = dime.setScale(2, RoundingMode.FLOOR);
        BigDecimal nickel = new BigDecimal(.05);
        nickel = nickel.setScale(2, RoundingMode.FLOOR);
        BigDecimal zeroBalance = new BigDecimal(0);
        BigDecimal quartersToGive = new BigDecimal(0);
        BigDecimal dimesToGive = new BigDecimal(0);
        BigDecimal nickelsToGive = new BigDecimal(0);
        if(userBalance.compareTo(zeroBalance) == 1)
        {
            if(userBalance.compareTo(quarter) == 1)
            {
                quartersToGive = getQuarters(quartersToGive, quarter);
                dimesToGive = getDimes(dimesToGive, dime);
                nickelsToGive = getNickles(nickelsToGive, nickel);
            }
            else if (userBalance.compareTo(dime) == 1)
            {
                dimesToGive = getDimes(dimesToGive, dime);
                nickelsToGive = getNickles(nickelsToGive, nickel);
            }
            else if (userBalance.compareTo(nickel) == 1)
            {
                nickelsToGive = getNickles(nickelsToGive, nickel);
            }
        }
        System.out.println("Keep the change ya filthy animal! Your Change is: " + ANSI_GREEN + "$" + cashOutBalance + ANSI_RESET);
        System.out.println("In " + quartersToGive + " Quarters | " + dimesToGive + " Dimes | and " + nickelsToGive + " Nickels.");
        userBalance = new BigDecimal(0.00);
    }

    private BigDecimal getQuarters(BigDecimal quartersToGive, BigDecimal quarter) {
        quartersToGive = userBalance.divide(quarter);
        quartersToGive = quartersToGive.setScale(0, RoundingMode.DOWN);
        userBalance = userBalance.subtract(quartersToGive.multiply(quarter));
        return quartersToGive;
    }

    private BigDecimal getDimes(BigDecimal dimesToGive, BigDecimal dime) {
        dimesToGive = userBalance.divide(dime);
        dimesToGive = dimesToGive.setScale(0, RoundingMode.DOWN);
        userBalance = userBalance.subtract(dimesToGive.multiply(dime));
        return dimesToGive;
    }

    private BigDecimal getNickles(BigDecimal nickelsToGive, BigDecimal nickel) {
        nickelsToGive = userBalance.divide(nickel);
        nickelsToGive = nickelsToGive.setScale(0, RoundingMode.DOWN);
        userBalance = userBalance.subtract(nickelsToGive.multiply(nickel));
        return nickelsToGive;
    }

    public void logSale(BigDecimal startBalance)
    {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime presentTime = LocalDateTime.now();
        try {

            FileWriter newPrint = new FileWriter("Log.txt", true);
            newPrint.write("\n" + timeFormat.format(presentTime) + " " + startBalance  + " " + userBalance);

            newPrint.close();
        } catch (IOException e) {}
    }

    public boolean isNumeric(String moneyInput) {
        try {
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
        int longestItemTypeLength = 0;
        for(Map.Entry<String, VendingItem> item : inventor.entrySet())
        {
            if(item.getValue().getItemName().length() > longestItemLength)
            {
                longestItemLength = item.getValue().getItemName().length();
            }
            if(item.getValue().getItemType().length() > longestItemTypeLength)
            {
                longestItemTypeLength = item.getValue().getItemType().length();
            }
        }

        for(Map.Entry<String, VendingItem> item : inventor.entrySet())
        {
            String itemName = item.getValue().getItemName() + (" ").repeat(longestItemLength - item.getValue().getItemName().length());
            String itemType = item.getValue().getItemType() + (" ").repeat(longestItemTypeLength - item.getValue().getItemType().length());
            if(item.getKey().charAt(0) == previousId)
            {
                System.out.print("("+ item.getKey() + ") " + itemName + ANSI_GREEN + " $" + item.getValue().getPrice() + ANSI_RESET + ANSI_BLUE + "  Stock: " + item.getValue().getInStockAmount() + ANSI_RESET + " " );
            }
            else
            {
                System.out.print("\n("+ item.getKey() + ") " + itemName + ANSI_GREEN + " $" + item.getValue().getPrice() + ANSI_RESET + ANSI_BLUE + "  Stock: " + item.getValue().getInStockAmount() + ANSI_RESET + " " );
            }
            previousId = item.getKey().charAt(0);
        }
        System.out.println(" ");

    }
}
