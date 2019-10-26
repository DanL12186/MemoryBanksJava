package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	public static Connection getConnection() {
		// First time we will use a literal user and password
		// and we will refactor to use environment variables for safety
		// in practice you should NEVER use literals
		
		// Note: JDBC url has a specific format
		//  jdbc:database-type://network-location:port/internal-database
		
		String pgUrl = "jdbc:postgresql://localhost:5432/postgres"; 
		try {
			return DriverManager.getConnection(pgUrl, 
				   System.getenv("NAME"), // set env vars for username + password; e.g. SET ENV[USERNAME] = "memory_manager"
				   System.getenv("PWORD")); //url = System.getE | same as above, use System.getEnv('key') instead of string literals
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error: Unable to connect to database.");
			return null;
		}//
	}
	
}
