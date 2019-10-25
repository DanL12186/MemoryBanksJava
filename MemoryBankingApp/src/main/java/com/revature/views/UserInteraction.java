package com.revature.views;

import com.revature.util.ScannerUtil;

public class UserInteraction {

	
    //gets and returns user's (process') name
	public static String getProcessName() {
		System.out.print("Please enter your name: ");
		String processName = ScannerUtil.getStringInput();
		return processName;
	}
	
	//gets user's "password"; asks for and receives input, returns their input
	public static String getChecksum() {
		System.out.print("Please enter your password: ");
		String checksum = ScannerUtil.getStringInput();
		return checksum;
	}

}
