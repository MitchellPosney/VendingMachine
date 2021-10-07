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
				VendingMachine obj = new VendingMachine();
				obj.printVendingContents();
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
		parseInventory();
		cli.run();

	}


	/*
	This method will parse the inventory file and create all necessary Objects out of the VendingItem class.
	 */
	private static void parseInventory()
	{
		File inventory = new File("Inventory.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inventory));

			String line = reader.readLine();
			while (line != null) {
				//System.out.println(line);
				String[] invSegments = line.split("\\|");
				String invLocation = invSegments[0];
				String invItemName = invSegments[1];
				double invPrice = Double.parseDouble(invSegments[2]);

				line = reader.readLine();
			}

		} catch (IOException e) {
			System.out.println("There was an error " + e.toString());
		}
	}
}
