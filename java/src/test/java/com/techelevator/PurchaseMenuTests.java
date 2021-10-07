package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class PurchaseMenuTests {

    PurchaseMenu purchaseMenu;




    @Before
    public void purchaseMenuTest() {
        purchaseMenu = new PurchaseMenu();
    }

    @Test
    public void enteredRock() {
        boolean isItCorrect = PurchaseMenu.isNumeric("Rock");
        Assert.assertEquals(false, isItCorrect);
    }

    @Test
    public void entered3454() {
        boolean isItCorrect = PurchaseMenu.isNumeric("34.54");
        Assert.assertEquals(true, isItCorrect);
    }

    @Test
    public void entered0() {
        boolean isItCorrect = PurchaseMenu.isNumeric("0");
        Assert.assertEquals(false, isItCorrect);
    }

    @Test
    public void entered564337() {
        boolean isItCorrect = PurchaseMenu.isNumeric("564337");
        Assert.assertEquals(true, isItCorrect);
    }


}
