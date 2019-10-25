package com.revature;

import java.sql.SQLException;

import com.revature.util.ConnectionUtil;
import com.revature.views.MainMenu;
import com.revature.views.View;

public class Application {
	Process currentUser;
	public static void main(String[] args) throws SQLException {
//		ConnectionUtil.getConnection();
		View currentView = new MainMenu();
		while (currentView != null) {
			currentView = currentView.process();
		}
		System.out.println("Goodbye!");
	}
}
