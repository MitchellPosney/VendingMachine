package com.techelevator.view;

import com.techelevator.VendingMachine;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class ItemSelectionProccessTests {

    VendingMachine vendingMachine;

    @Before
    public void selectItemTests() {
        vendingMachine = mock(VendingMachine.class);
    }

    @Test
    public void buyA2() {
        doNothing().when(vendingMachine).itemSelectionProccess();
    }
}
