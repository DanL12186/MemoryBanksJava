package com.revature.views;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.MemoryBankDao;
import com.revature.models.MemoryBank;
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
		System.out.println("2) Make a Deposit");
		System.out.println("3) Make a Transfer");
		System.out.println("0) Back");
	}
	
	public View process() {
		printMenu();

		int selection = ScannerUtil.getInput(4);
		switch (selection) {
			case 1: new BankMenu(currentProcess).viewAccounts();
					printOptions("neg");
					return new BankMenu(currentProcess);
					
			case 2: new BankMenu(currentProcess).viewAccounts();
					printOptions("pos");
					return new BankMenu(currentProcess);
					
			case 3: new BankMenu(currentProcess).viewAccounts();
			        handleTransferInput();
			        return new BankMenu(currentProcess);
			        
			case 0: return new BankMenu(currentProcess);
			default: return null;
		}
	}
	
	public void printOptions(String sign) {
		System.out.println("Select an account by ID which you wish to withdraw or deposit to.");
		
		int acctId = ScannerUtil.getInput(1000);
		
		System.out.println("Select an amount to deposit or withdraw.");
		
		int amount = Math.abs(ScannerUtil.getInput(2000000));
		
		directUnidirectionalTransaction(acctId, amount, sign);
	}
	
	public void handleTransferInput() {
		System.out.println("Select an account by ID which you wish transfer from.");
		int fromId = ScannerUtil.getInput(1000);
		
		System.out.println("Select an account by ID which you wish transfer to.");
		int toId = ScannerUtil.getInput(1000);
		
		System.out.println("Select an amount of bytes to transfer.");
		int bytes = ScannerUtil.getInput((int) Math.pow(2, 31) -1);
		System.out.println(bytes+" " + fromId + " " + toId);
		
		new MemoryBankDao().transfer(bytes, fromId, toId);
	}
	
	public void directUnidirectionalTransaction(int bankId, int amount, String sign) {
		if (sign.equals("neg")) {
			amount = -amount;
		}
		
		new MemoryBankDao().depositOrWithdraw(bankId, amount);
		
		System.out.println(Math.abs(amount) + " bytes " + (sign.equals("neg") ? "withdrawn" : "deposited!"));
	}
	

}
