package com.techelevator.view;

import com.techelevator.VendingMachine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;

public class LogTest {

    VendingMachine vendingMachine;
    File inventoryTemp = new File("log.txt");

    public void fileLineCleanup() {
        try {
            RandomAccessFile f = new RandomAccessFile("log.txt", "rw");
            long length = f.length() - 1;
            do {
                length -= 1;
                f.seek(length);
            } while (f.readByte() != 10);
            f.setLength(length + 1);
            f.close();
        } catch (IOException e) {}
    }

    @Before
    public void logStart() {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void feedMoney() {
        vendingMachine.getCustomerMoney("5");
         try {
                        try {
                            BufferedReader input = new BufferedReader(new FileReader(inventoryTemp));
                            String last = null;
                            String line = null;
                            while ((line = input.readLine()) != null) {
                                last = line;
                            }
                            Assert.assertTrue(last.contains("(FEED MONEY) $0.00 $5"));
                        } catch (IOException e) {
                    }
                } finally {
                fileLineCleanup();
         }
    }

    @Test
    public void giveChange() {
        vendingMachine.getCustomerMoney("5");
        vendingMachine.cashOut();
        try {
            try {
                BufferedReader input = new BufferedReader(new FileReader(inventoryTemp));
                String last = null;
                String line = null;
                while ((line = input.readLine()) != null) {
                    last = line;
                }
                Assert.assertTrue(last.contains("(GIVE CHANGE) $5.00 $0"));
            } catch (IOException e) {}
        } finally {
            fileLineCleanup();
            fileLineCleanup();
        }
    }

    @Test
    public void itemSelect() {
        vendingMachine.userBalance = new BigDecimal(500.00);
        vendingMachine.itemSelectionProcess("a2");
        try {
            try {
                BufferedReader input = new BufferedReader(new FileReader(inventoryTemp));
                String last = null;
                String line = null;
                while ((line = input.readLine()) != null) {
                    last = line;
                }
                Assert.assertTrue(last.contains("(Kevin McCallister Brick)"));
            } catch (IOException e) {}
        } finally {
            fileLineCleanup();
            fileLineCleanup();
        }
    }



}
