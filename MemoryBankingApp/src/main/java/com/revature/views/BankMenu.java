//Application must support the following functionalities: 
//+Users should be able to create an account
//+Users should be able to log in to an account with a correct user name and password.
//+Users should be able to create checking/savings accounts under their account.
//     Accounts should support:
//+Standard single-user accounts
//Joint accounts (multiuser) with any number of users
//Users should be able to deposit to a specified account.
//Users should be able to withdraw from an account.
//Users should be able to conduct a balance check on an account
//Users should be able to transfer money from one account to another account.
//~Users should be able to close an account.
//+Users should be able to retrieve data between login sessions and application execution.

package com.revature.views;

import com.revature.dao.MemoryBankDao;
import com.revature.util.ScannerUtil;
import java.sql.SQLException;
import java.util.List;
import com.revature.dao.ProcessDao;
import com.revature.models.Process;
import com.revature.models.MemoryBank;

public class BankMenu implements View {
	Process currentProcess;

	private void printMenu() {
		System.out.println(currentProcess);
		System.out.println("------ Bank Menu ------");
		System.out.println("1. View Your Accounts");
		System.out.println("2. Create MemoryBank Account");
		System.out.println("3. Delete Yourself");
		System.out.println("0. Back");
		System.out.println("");
	}

	public BankMenu() {
		// empty constructor
	}

	// instantiate currentUser
	public BankMenu(Process process) {
		currentProcess = process;
	}

	public View process() {
		printMenu();

		int selection = ScannerUtil.getInput(3);

		switch (selection) {
		case 0:
			return new MainMenu();
		case 1:
			List<MemoryBank> res = null;
			try {
				res = new MemoryBankDao().getProcessesAccounts(currentProcess.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			showAll(res);
			return new AccountTransferMenu(currentProcess);

		case 2:
			createAccount(currentProcess);
			return new BankMenu(currentProcess);// create account ProcessMenu().createAccount()`;
		case 3:
			new ProcessDao().killAllThreads(currentProcess);
			return null;

		default:
			return null;
		}
	}

	public void showAll(List<MemoryBank> banks) {
		for (MemoryBank bank : banks) {
			int id = bank.getId();
			int stackBytes = bank.getStackBytesAvailable();
			int heapBytes = bank.getHeapBytesAvailable();
			int bytes = stackBytes > 0 ? stackBytes : heapBytes;

			String accountType = stackBytes > 0 ? "Stack" : "Heap ";
			
			System.out.println(accountType + " Account - Account #: " + id + " - " + accountType + " size: " + bytes + " bytes");
		}
	}

	public void createAccount(Process process) {
		System.out.println(process);
		System.out.println("========== Create New Heap or Stack =========");
		System.out.println("====|______________________________|____ ====");
		System.out.println("====||    -|   |=   -|   |-   -|   |-  ||====");
		System.out.println("====||    -|   |-   -|   |-   -|   |-  ||====");
		System.out.println("====||  c -|   |-   -|   |-   -|   |-  ||====");
		System.out.println("====||||||-|___|-|||-|___|-|||-|___|-__||====");

		System.out.println("");

		System.out.println("1. Create a New Heap (Savings Acount)");
		System.out.println("2. Create a New Stack (Checking Acount)");

		String userChoice = ScannerUtil.getStringInput();

		bootNewVM(userChoice, process.getId());
	}

	// create a new bank object and save it
	public MainMenu bootNewVM(String choice, int id) {
		if (choice.equals("1")) {
			createNewHeapOrStack("heap", id);
		} else {
			createNewHeapOrStack("stack", id);
		}
		return new MainMenu();
	}

	private MemoryBank createNewHeapOrStack(String heapOrStack, int id) {
		System.out.println("Enter number of bytes to enter into stack: ");

		int amount = ScannerUtil.getInput(2147483647);

		if (heapOrStack == "heap") {
			MemoryBankDao.persistAccount(amount, 0, id);
		} else {
			MemoryBankDao.persistAccount(0, amount, id);
		}

		MemoryBank memoryBank = new MemoryBank(amount, 0, id);

		return memoryBank;
	}
}
