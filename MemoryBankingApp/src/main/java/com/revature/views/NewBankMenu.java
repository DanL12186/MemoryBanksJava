package com.revature.views;

import com.revature.util.ScannerUtil;

//create a new checking or savings account
public class NewBankMenu {

	public void printMenu() {
		System.out.println("1) Create a New Stack (Checking Account)"); 
		System.out.println("2) Create a New Heap (Savings Account)"); 
		System.out.println("3) Browse Your Memory Locations");
		System.out.println("0) Log out");
	}
	
	public View process() {
		printMenu();

		int selection = ScannerUtil.getInput(4);
		switch (selection) {
			case 0: return null;
			case 1: new ProcessMenu().viewAllProcesses(); //view your accounts 
			        return null; //needs return null ? 
			case 2: return null; //create account ProcessMenu().createAccount();
			case 3: return new ProcessMenu();//.createProcess();
			default: return null;
		}
	}
}
