package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Process;
import com.revature.util.ConnectionUtil;

public class ProcessDao {
	// called by ProcessMenu.createProcess()
	// persists a new process (user) to database
	public void persistProcess(Process processObject) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO processes (process_name, checksum, current_bytes_held) VALUES(?, ?, ?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, processObject.getProcessName());
			statement.setString(2, processObject.getChecksum());
			statement.setInt(3, (int) processObject.getCurrentBytesHeld());

			System.out.println("");

			statement.executeUpdate();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	// retrieves all records matching user's input (for name), and extracts it into
	// object
	public Process getProcess(String processName) {
		try (Connection connection = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM processes WHERE process_name = ?";
			PreparedStatement statement = connection.prepareStatement(sql);

			// set value in prepared sql statement ("?") to name of the input process
			statement.setString(1, processName);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				System.out.println("Attemping to register process...");
				return extractProcess(result);
			}
		} catch (SQLException e) {
			System.out.println("Ohnoes... SQLException :(");
			e.printStackTrace();
		}
		System.out.println("returning null");
		return null;
	}
	
	public void killAllThreads(Process process) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM processes WHERE process_id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);

			int id = process.getId();
			
			statement.setInt(1, id);

			statement.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQLException caught");
		}
		System.out.println("Waiting for all threads to resolve...");
		System.out.println("You have been removed from the stack!");
	}

	// lists all processes (users)
	public List<Process> getAllProcesses() {
		try (Connection connection = ConnectionUtil.getConnection()) {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM processes"; // users
			ResultSet resultSet = statement.executeQuery(query);

			List<Process> processes = new ArrayList<>();

			while (resultSet.next()) {
				Process process = extractProcess(resultSet);
				processes.add(process);
			}

			return processes;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Process> getProcessesByProcessName(String processName) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM processes WHERE LOWER(first_name) = LOWER(?)";
			PreparedStatement statement = connection.prepareStatement(sql);

			// Setting parameters - first ?, second ?, etc.
			statement.setString(1, processName);
//			statement.setString(2, secondParameter);

			ResultSet resultSet = statement.executeQuery();
			List<Process> processes = new ArrayList<>();

			while (resultSet.next()) {
				Process process = extractProcess(resultSet);
				processes.add(process);
			}

			return processes;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// converts a resultSet into Java Object
	private Process extractProcess(ResultSet resultSet) throws SQLException {
		System.out.println("");

		int currentBytesHeld = resultSet.getInt("current_bytes_held");
		String processName = resultSet.getString("process_name");
		String checksum = resultSet.getString("checksum");
		int id = resultSet.getInt("process_id");

		Process process = new Process(currentBytesHeld, processName, checksum, id);
		
		return process;
	}
}
