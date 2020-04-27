package com.javabeans.users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.javabeans.users.UsersDBUtil;

public class UsersDBUtil {
	
	private static UsersDBUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/java_beans_db";
	
	public static UsersDBUtil getInstance() throws Exception
	{
		if(instance == null)
		{
			instance = new UsersDBUtil();
		}
		
		return instance;
	}
	
	private UsersDBUtil() throws Exception
	{
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException
	{
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public List<Users> getUsers() throws Exception
	{
		List<Users> users = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try
		{
			myConn = getConnection();
			String sql = "select first_name, last_name, city, gender, DOB from users";
			
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next())
			{
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String city = myRs.getString("city");
				String gender = myRs.getString("gender");
				//String dob = myRs.getString("DOB");
				
				Users tempUser = new Users(firstName, lastName, city, gender);
				
				users.add(tempUser);
			}
		}
		finally
		{
			close(myConn, myStmt, myRs);
		}
		
		return users;
	}
	
	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();
		
		return theConn;
	}
	
	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
