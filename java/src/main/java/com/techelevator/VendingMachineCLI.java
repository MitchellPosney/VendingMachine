package com.techelevator;

import com.techelevator.view.Menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class VendingMachineCLI
{

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};

	private Menu menu;

	public VendingMachineCLI(Menu menu)
	{
		this.menu = menu;
	}

	public void run()
	{
		while (true)
		{
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS))
			{
				//new VendingMachine().printVendingContents();
				// display vending machine items
			}
			else if (choice.equals(MAIN_MENU_OPTION_PURCHASE))
			{
				// do purchase
			}
			else if(choice.equals(MAIN_MENU_OPTION_EXIT))
			{
				// do exit
			}

		}
	}

	public static void main(String[] args)
	{
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		new VendingMachine().parseInventory();

		cli.run();

	}

}
