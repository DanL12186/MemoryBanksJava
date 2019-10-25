package com.revature.util;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Helper class to manage the Scanner instance and expose methods
 * that will allow me to abstract the process of getting user input.
 */
public class ScannerUtil {

	static Scanner scanner = new Scanner(System.in);
	
	public static int getInput(int max) {
		int input = -1;
		
		while(input < 0 || input > max) {
			if (input != -1) {
				input = Math.abs(input);
			}
			
			System.out.println("Please enter an integer between 0 and " + max);
			
			//if user entered a non-integer value, ask them to enter a valid number
			if(!scanner.hasNextInt()) {
				System.out.println("Please enter a valid number!");
				scanner.nextLine();
				continue;
			}
			input = scanner.nextInt();			
		}
		
		return input;
	}

	public static String getStringInput() {
		String input = "";
		while(input.isEmpty()) {
			input = scanner.nextLine();
		}
		return input;
	}

}
