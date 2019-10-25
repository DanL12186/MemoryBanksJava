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
		
		
	private MemoryBank extractAccount(ResultSet res) throws SQLException {
		int bankId = res.getInt("bank_id");
		int heapBytes  = res.getInt("heap_bytes_available");
		int stackBytes = res.getInt("stack_bytes_available");


		MemoryBank bank = new MemoryBank(heapBytes, stackBytes, bankId);
		
		return bank;
	}
}

		
//	//find join tables with a an id of process passed in
//	public List<MemoryBank> findProcessAccounts(Process process) {
//		try (Connection connection = ConnectionUtil.getConnection()) {
//			int id = process.getId();
//			String sql = "SELECT * process_banks WHERE process_id = ?";
//
//			PreparedStatement statement = connection.prepareStatement(sql);
//
//			statement.setInt(1, id);
//
//			ResultSet result = statement.executeQuery();
//			
////			List<MemoryBank> accounts = new ArrayList<>();
//			
//			List<ResultSet> banks;
//			//going to have to "convert" join tables to memory banks (accounts)
//			
//			while (result.next()) {
//				MemoryBank banks = getSpecificUsersAccounts(result);
//			}
//
//			return banks;
//			
//			while (result.next()) {
//				System.out.println(result);
//			}
//			
//		} catch (SQLException e) {
//			System.out.println("");
//		}
//	}
//	//SEEE HERE ------------------>>>>>>>>>>>>>>>>>>>>VVVVVVVVVVVVVVVVVVVVVVVV
//	//perfect example of getting account via join!!!
//	public List<ResultSet> getSpecificUsersAccounts(int processId) {
//		try (Connection connection = ConnectionUtil.getConnection()) {
//			String sql = "SELECT * FROM memory_bank JOIN process_banks USING(bank_id) WHERE process_id = ?";
//			
//			PreparedStatement statement = connection.prepareStatement(sql);
//			
//			statement.setInt(1, processId);
//
//			ResultSet result = statement.executeQuery();
//			
//			List<MemoryBank> accounts = new ArrayList<>();
//			
//			while (result.next()) {
//				System.out.println(result);
//				MemoryBank acc = getBankById(result);
//				accounts.add(acc);
//			}
//			
//			return accounts;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
//	public MemoryBank getBankById(ResultSet res) {
//		int id = res.getInt("bank_id");
//		
//		String sql = "SELECT * FROM memory_bank JOIN process_banks USING(bank_id) WHERE process_id = ?";
//		
//		PreparedStatement statement = connection.prepareStatement(sql);
//		
//		statement.setInt(1, processId);
//
//		ResultSet result = statement.executeQuery();
//
//		return 0;
//		
//	}


