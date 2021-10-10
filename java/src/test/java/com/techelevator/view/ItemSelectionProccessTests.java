package com.techelevator.view;

import com.techelevator.VendingMachine;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;


public class ItemSelectionProccessTests {


    VendingMachine vendingMachine;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    LogTest logCleanup = new LogTest();

    @Before
    public void startItemSelection() {
        System.setOut(new PrintStream(outputStreamCaptor));
        vendingMachine = new VendingMachine();
    }

    @Test
    public void buyA2() {
        try {
            vendingMachine.userBalance = new BigDecimal(500.00);
            Assert.assertTrue(vendingMachine.itemSelectionProcess("A2"));
        } finally {
            logCleanup.fileLineCleanup();
        }
    }

    @Test
    public void buya2() {
        try {
            vendingMachine.userBalance = new BigDecimal(500.00);
            Assert.assertTrue(vendingMachine.itemSelectionProcess("a2"));
        } finally {
            logCleanup.fileLineCleanup();
        }
    }

    @Test
    public void buy42() {
        Assert.assertFalse(vendingMachine.itemSelectionProcess("42"));
    }

    @Test
    public void buy6ItemsSoldOut() {
        try {
            System.setOut(new PrintStream(outputStreamCaptor));
            vendingMachine.userBalance = new BigDecimal(500.00);
            for (int i = 0; i < 6; i++) {
                vendingMachine.itemSelectionProcess("a2");
            }
            Assert.assertTrue(outputStreamCaptor.toString().trim().contains("Item is SOLD OUT!"));
        } finally {
            for (int i = 0; i < 5; i++) {
                logCleanup.fileLineCleanup();
            }
        }
    }

    @Test
    public void buyItemInsufficientFunds() {
        System.setOut(new PrintStream(outputStreamCaptor));
        vendingMachine.userBalance = new BigDecimal(1.00);
        vendingMachine.itemSelectionProcess("a2");
        Assert.assertTrue(outputStreamCaptor.toString().trim().contains("Not Enough Money"));
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }




}
