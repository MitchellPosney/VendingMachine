package com.techelevator.view;

import com.techelevator.VendingMachine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GetQuartersTests {

    VendingMachine vendingMachine;
    BigDecimal quarter = new BigDecimal(.25);
    BigDecimal dime = new BigDecimal(.10);
    BigDecimal nickel = new BigDecimal(.05);


    @Before
    public void quarterTests() {
        vendingMachine = new VendingMachine();
        quarter = quarter.setScale(2, RoundingMode.FLOOR);
        dime = dime.setScale(2, RoundingMode.FLOOR);
        nickel = nickel.setScale(2, RoundingMode.FLOOR);
        /*
        BigDecimal zeroBalance = new BigDecimal(0);
        BigDecimal quartersToGive = new BigDecimal(0);
        BigDecimal dimesToGive = new BigDecimal(0);
        BigDecimal nickelsToGive = new BigDecimal(0);

         */
    }

    @Test
    public void quartersOf5() {
        vendingMachine.userBalance = new BigDecimal(5);
        BigDecimal quarterOutput = vendingMachine.getQuarters(new BigDecimal(0), quarter);
        Assert.assertEquals(new BigDecimal(20), quarterOutput);
    }

    @Test
    public void quartersOf595() {
        vendingMachine.userBalance = new BigDecimal(5.95);
        BigDecimal quarterOutput = vendingMachine.getQuarters(new BigDecimal(23), quarter);
        Assert.assertEquals(new BigDecimal(20), quarterOutput);
    }
}
