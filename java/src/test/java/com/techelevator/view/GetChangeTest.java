package com.techelevator.view;

import com.techelevator.VendingMachine;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;


public class GetChangeTest {


    VendingMachine vendingMachine;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    LogTest logCleanup = new LogTest();

    @Before
    public void changeTests() {
        System.setOut(new PrintStream(outputStreamCaptor));
        vendingMachine = new VendingMachine();
    }

    @Test
    public void changeOf5() {
        vendingMachine.userBalance = new BigDecimal(5);
        vendingMachine.cashOut();
        Assert.assertTrue(outputStreamCaptor.toString().trim().contains("20 Quarters"));
    }

    @Test
    public void changeOf580() {
        vendingMachine.userBalance = new BigDecimal(5.90);
        vendingMachine.cashOut();
        Assert.assertTrue(outputStreamCaptor.toString().trim().contains("23 Quarters | 1 Dimes | 1 Nickels"));
    }

    @Test
    public void changeOf025() {
        vendingMachine.userBalance = new BigDecimal(.25);
        vendingMachine.cashOut();
        Assert.assertTrue(outputStreamCaptor.toString().trim().contains("1 Quarters"));
    }

    @Test
    public void changeOf010() {
        vendingMachine.userBalance = new BigDecimal(.10);
        vendingMachine.cashOut();
        Assert.assertTrue(outputStreamCaptor.toString().trim().contains("1 Dimes"));
    }

    @Test
    public void changeOf005() {
        vendingMachine.userBalance = new BigDecimal(.05);
        vendingMachine.cashOut();
        Assert.assertTrue(outputStreamCaptor.toString().trim().contains("1 Nickels"));
    }

    @Test
    public void changeOf076() {
        vendingMachine.userBalance = new BigDecimal(.76);
        vendingMachine.cashOut();
        Assert.assertTrue(outputStreamCaptor.toString().trim().contains("1 Pennies"));
    }

    @After
    public void tearDown() {
        logCleanup.fileLineCleanup();
    }





}
