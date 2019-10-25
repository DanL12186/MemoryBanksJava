package com.revature.test.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.postgresql.core.BaseStatement;

import com.revature.dao.UserDao;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

/**
 * Integration testing
 * ----------------------
 * Integration testing is the processing of testing an application's
 * integration with another application or service.
 * @author Mitch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDaoTests {

	private static UserDao userDao = new UserDao();

	// To be spied upon
	private static Connection conn;
	private static BaseStatement stmt;

	public UserDaoTests() throws SQLException {
		super();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Create connectoin object
		Connection connection = ConnectionUtil.getConnection();
		
		// Set schema to testing so that all operations
		// happen on the testing schema
		connection.setSchema("testing");		
		
		// Copy table that will be tested from public to
		// testing with no data
		connection.createStatement()
			.executeUpdate("CREATE TABLE if not exists testing.employees AS TABLE public.employees"
					+ " WITH NO DATA");
			
		// Insert a single record for testing purposes
		connection.createStatement()
			.executeUpdate("INSERT INTO testing.employees "
					+ "(first_name, salary, start_date) VALUES ('Test', 1, current_date)");
		
		// Create spy objects watching Statement and Connection
		Statement statement = Mockito.spy(connection.createStatement());
		stmt = (BaseStatement) statement;
		conn = Mockito.spy(connection);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// Drop created table after tests complete
		ConnectionUtil
			.getConnection()
			.createStatement()
			.executeUpdate("drop TABLE testing.employees");
		
		ConnectionUtil.testMode = false;
	}
	
	@Before
	public void setUp() throws SQLException {
		Mockito.reset(conn);
		Mockito.reset(stmt);
		userDao.setConnection(conn);
		Mockito.when(conn.createStatement()).thenReturn(stmt);
		
	}
	
	@Test
	public void getAllUsersTest() throws SQLException {
		List<User> users = userDao.getAllUsers();
		Mockito.verify(stmt).executeQuery(Mockito.anyString());
	}
}








