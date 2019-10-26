package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.MemoryBank;
import com.revature.util.ConnectionUtil;

public class MemoryBankDao {
	//called by ProcessMenu.createProcess() 
	//persists a new process (user) to database
	public void persistMemoryBank(MemoryBank bankObject) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO processes (heap_bytes_available, stack_bytes_available) VALUES(?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, bankObject.getStackBytesAvailable());
			statement.setInt(2, bankObject.getHeapBytesAvailable());
			
			statement.executeUpdate();
		} catch(SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	    //make either a checking or savings
		//not converted fully to account from process yet
	public static void persistAccount(int heap, int stack, int processId) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO memory_banks (heap_bytes_available, stack_bytes_available) VALUES(?, ?) RETURNING bank_id";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, heap);
			statement.setInt(2, stack);

			System.out.println("");

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				int bankId = result.getInt("bank_id");
				String sql2 = "INSERT INTO process_banks (bank_id, process_id) VALUES(?, ?)";

				PreparedStatement statement2 = connection.prepareStatement(sql2);

				statement2.setInt(1, bankId);
				statement2.setInt(2, processId);

				statement2.executeUpdate();
			}

			System.out.println("Memory space created!");
			System.out.println("");

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	public void depositOrWithdraw(int bankId, int amount) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "UPDATE memory_banks SET heap_bytes_available = heap_bytes_available + ? WHERE bank_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, amount);
			statement.setInt(2, bankId);

			statement.executeUpdate();
			

		} catch (SQLException err) {
			err.printStackTrace();
		}
	}
	
	//grab all banks of a particular process and return them in a list
	public List<MemoryBank> getProcessesAccounts(int processId) throws SQLException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM memory_banks JOIN process_banks USING(bank_id) WHERE process_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, processId);

			ResultSet response = statement.executeQuery();
			List<MemoryBank> accounts = new ArrayList<>();

			while (response.next()) {
				MemoryBank acc = extractAccount(response);
				accounts.add(acc);
			}

			return accounts;

		} catch (SQLException err) {
			err.printStackTrace();
		}
		System.out.println("Process # " + processId + " not found");
		throw new SQLException();
	}
	
	public int transfer(int amount, int withdrawAccId, int depositAccId) {
		Connection connection = ConnectionUtil.getConnection();
		try {
			connection.setAutoCommit(false);

			String withdrawSQL = "UPDATE memory_banks SET heap_bytes_available = heap_bytes_available - ? WHERE bank_id = ? RETURNING heap_bytes_available";
			String depositSQL  = "UPDATE memory_banks SET heap_bytes_available = heap_bytes_available + ? WHERE bank_id = ?";
			
			PreparedStatement withdrawStatement = connection.prepareStatement(withdrawSQL);
			PreparedStatement depositStatement  = connection.prepareStatement(depositSQL);

			//Set and execute deposit statement
			depositStatement.setInt(1, amount);
			depositStatement.setInt(2, depositAccId);
			depositStatement.executeUpdate();

			//Set and execute withdraw statement
			withdrawStatement.setInt(1, amount);
			withdrawStatement.setInt(2, withdrawAccId);
			withdrawStatement.execute();

			//Get result set from RETURNING clause
			ResultSet res = withdrawStatement.getResultSet();
			
			System.out.println("res: " + res);
			if (res.next()) {
				int balance = res.getInt("heap_bytes_available");
				connection.commit();
				connection.close();
				return balance;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLException");
			try {
				connection.rollback();
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return 0;
	}
		
	private MemoryBank extractAccount(ResultSet res) throws SQLException {
		int bankId = res.getInt("bank_id");
		int heapBytes  = res.getInt("heap_bytes_available");
		int stackBytes = res.getInt("stack_bytes_available");


		MemoryBank bank = new MemoryBank(heapBytes, stackBytes, bankId);
		
		return bank;
	}
}

