package com.techelevator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;


public class VendingMachine
{

    public void printVendingContents()
    {
        File inventory = new File("vendingmachine.csv");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inventory));
            String previousInvLocation = null;
            String line = reader.readLine();
            while (line != null) {
                String[] invSegments = line.split("(\\|)");
                String invLocation = invSegments[0];
                String invItemName = (invSegments[1].length() < 20) ?  invSegments[1] += (" ").repeat(20 - invSegments[1].length()) : invSegments[1];
                BigDecimal invPrice = new BigDecimal(invSegments[2]);
                line = reader.readLine();

                if(previousInvLocation == null)
                {
                    previousInvLocation = invSegments[0];
                }
                if(previousInvLocation.charAt(0) == invLocation.charAt(0))
                {
                    System.out.print("| " + invLocation + " " + invItemName + " " + invPrice);
                    previousInvLocation = invSegments[0];
                }
                else
                {
                    System.out.print("\n" + "| " + invLocation + " " +invItemName + " " + invPrice);
                    previousInvLocation = invSegments[0];
                }
            }
        } catch (IOException e) {
            System.out.println("There was an error " + e.toString());
        }
    }

}
