package com.techelevator;

import com.techelevator.view.Menu;

import java.util.Scanner;

public class VendingMachineCLI
{

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_BLUE = "\u001B[34m";

	private Scanner scanner = new Scanner(System.in);
	private Menu menu;

	public static void main(String[] args)
	{
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

	public VendingMachineCLI(Menu menu)
	{
		this.menu = menu;
	}

	public void run()
	{
		VendingMachine vendingMachine = new VendingMachine();
		while (true)
		{
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS))
			{
				vendingMachine.printVendingContents();
				// display vending machine items
			}
			else if (choice.equals(MAIN_MENU_OPTION_PURCHASE))
			{
				boolean isTransactionFinished = false;
				do
				{
					System.out.println("\n(1) Feed Money\n(2) Select Product\n(3) Display Items\n(4) Finish Transaction\n\n" + ANSI_GREEN + "Remaining Balance: $" + vendingMachine.userBalance + ANSI_RESET);
					System.out.print("Please enter an option >>> ");
					choice = scanner.nextLine();

					switch (choice)
					{
						case "1":
							vendingMachine.getCustomerMoney();
							break;
						case "2":
							vendingMachine.printVendingContents();
							vendingMachine.itemSelectionProccess();
							break;
						case "3":
							vendingMachine.printVendingContents();
							break;


						case "4":
							vendingMachine.cashOut();
							isTransactionFinished = true;
							System.out.println("Thank for using the Home Alone Snack Machine");
							break;

						default:
						{
							System.out.println("Invalid Entry");
						}
					}
				}while(!isTransactionFinished);
			}
			else if(choice.equals(MAIN_MENU_OPTION_EXIT))
			{
				// do exit
			}
		}
	}
}
