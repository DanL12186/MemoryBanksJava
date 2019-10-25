package com.revature.views;

import com.revature.models.Process;
import com.revature.util.ScannerUtil;

public class AccountTransferMenu implements View {
	Process currentProcess; 
	
	public AccountTransferMenu() {
		//empty constructor
	}
	
	//instantiate currentUser 
	public AccountTransferMenu(Process process) {
		currentProcess = process;
	}
	
	public void printMenu() {
		System.out.println("1) Make a Withdrawal"); 
		System.out.println("2) Make a deposit");
		System.out.println("3) Make a transfer");
		System.out.println("0) Main Menu");
	}
	
	public View process() {
		printMenu();

		int selection = ScannerUtil.getInput(4);
		switch (selection) {
			case 0: return null;
			case 1: return null; //view your accounts 
			case 2: return null; //create account ProcessMenu().createAccount();
			case 3: return null;//.createProcess();
			default: return null;
		}
	}
}
