package com.techelevator.view;

import com.techelevator.VendingMachine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetSoundTests {

    VendingMachine vendingMachine;

    @Before
    public void soundTest() {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void testChips(){
        String input = "Chip";
        String result = vendingMachine.getSound(input);
        Assert.assertEquals("Chips should result in '\"Crunch Crunch, Yum!\"'","Crunch Crunch, Yum!", result);
    }

    @Test
    public void testCandy(){
        String input = "Candy";
        String result = vendingMachine.getSound(input);
        Assert.assertEquals("Chips should result in '\"Munch Munch, Yum!\"'","Munch Munch, Yum!", result);
    }

    @Test
    public void testDrink() {
        String input = "Drink";
        String result = vendingMachine.getSound(input);
        Assert.assertEquals("Chips should result in '\"Glug Glug, Yum!\"'", "Glug Glug, Yum!", result);
    }

    @Test
    public void testGum() {
        String input = "Gum";
        String result = vendingMachine.getSound(input);
        Assert.assertEquals("Chips should result in '\"Chew Chew, Yum!\"'", "Chew Chew, Yum!", result);
    }

    @Test
    public void testNull() {
        String input = "Red Hot Door Knob";
        String result = vendingMachine.getSound(input);
        Assert.assertEquals("Chips should result in '\"THIS SNACK IS KINDA SUS\"'", "THIS SNACK IS KINDA SUS", result);
    }


}
