package com.techelevator.view;

import com.techelevator.VendingMachine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoneyInputTests {

    VendingMachine vendingMachine;

    @Before
    public void getCustomerMoneyTest() {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void enteredMayo() {
        boolean isItCorrect = vendingMachine.isNumeric("Mayonnaise");
        Assert.assertEquals("No Patrick, Mayonaise is not an instrument.",false, isItCorrect);
    }

    @Test
    public void entered3454() {
        boolean isItCorrect = vendingMachine.isNumeric("34.54");
        Assert.assertEquals("Whole dollar values only.",false, isItCorrect);
    }

    @Test
    public void entered0() {
        boolean isItCorrect = vendingMachine.isNumeric("0");
        Assert.assertEquals("Inputting no money is not valid",false, isItCorrect);
    }

    @Test
    public void entered564337() {
        boolean isItCorrect = vendingMachine.isNumeric("564337");
        Assert.assertEquals("Six figure amounts should be vaild",true, isItCorrect);
    }

    @Test
    public void entered50Cent() {
        boolean isItCorrect = vendingMachine.isNumeric(".50");
        Assert.assertEquals(false, isItCorrect);
    }


}
