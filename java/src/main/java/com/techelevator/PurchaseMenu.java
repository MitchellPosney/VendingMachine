package com.techelevator;

import java.math.BigDecimal;


public class PurchaseMenu {





    public void purchaseProcess() {



    }


    public static boolean isNumeric(String moneyInput) {
        double doubleValue;
        if(moneyInput == null || moneyInput.equals("") || moneyInput.equals("0")) {
            System.out.println(moneyInput + " is not a proper monetary value.");
            return false;
        }

        try {
            doubleValue = Double.parseDouble(moneyInput);
            return true;
        } catch (NumberFormatException e) {}
        return false;
    }

}
