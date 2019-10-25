package com.revature.views;

import com.revature.util.ScannerUtil;

import java.sql.SQLException;

import com.revature.dao.ProcessDao;
import com.revature.models.Process;

public class MainMenu implements View {
	Process currentProcess;

	
	public MainMenu() {
		//empty constructor
	}
	
	//instantiate currentUser 
	public MainMenu(Process process) {
		currentProcess = process;
	}
	
	private ProcessDao processDao = new ProcessDao();

	public void printMenu() {
		System.out.println("============ Welcome to Your Bank ===========");
		System.out.println("====|______________________________|____ ====");
		System.out.println("====||    -|   |=   -|   |-   -|   |-  ||====");
		System.out.println("====||    -| 6 |-   -| 0 |-   -| 1 |-  ||====");
		System.out.println("====||    -|   |-   -|   |-   -|   |-  ||====");
		System.out.println("====||||||-|___|-|||-|___|-|||-|___|-__||====");
		System.out.println("---------------------------------------------");
		
		System.out.println("");
		
		System.out.println("0. Quit");
		System.out.println("1. (Log In) Launch Process");
		System.out.println("2. (Sign Up) Become a Verified Process");
	}

	// after this, a login menu should show up, and process should be able to log in.
	public View process() { // process not Process
		printMenu();

		int selection = ScannerUtil.getInput(3);

		switch (selection) {
		case 0:
			return null; // done
		case 1:
			Process process = login();
			return new BankMenu(process);
		case 2:
			process = signup();//return new BankMenu();
			return new BankMenu(process);
		default:
			return null;
		}
	}

	private Process login() {
		while (true) {
			String processName = UserInteraction.getProcessName();
			String checksum = UserInteraction.getChecksum();

			// Check database for matching process input
			Process currentProcess = new Process(processName, checksum);

			currentProcess = new ProcessDao().getProcess(processName);

			if (currentProcess != null && currentProcess.getChecksum().equals(checksum)) {
				System.out.println("Hey there, " + currentProcess.getProcessName() + "!");
				System.out.println("");
				return currentProcess;
			}
			System.out.println("Whoops! Username or password doesn't match or username doesn't exist...");
		}
	}
	
	//take user input for name, password and bytes, create a new Process object and save it
	public Process signup() {
		System.out.println("Enter process name: ");
		String processName = ScannerUtil.getStringInput();
		
		System.out.println("Enter your secret checksum: ");
		String checksum = new String(ScannerUtil.getStringInput());
		
		System.out.println("Enter number of bytes process currently uses: ");
		int currentBytesHeld = ScannerUtil.getInput(171792886);

		Process process = new Process(currentBytesHeld, processName, checksum);
		
		System.out.println("Thanks for registering, " + processName);

		processDao.persistProcess(process);
		
		process = new ProcessDao().getProcess(processName);
		
		new BankMenu(process);
		
		return process;
	}
}
