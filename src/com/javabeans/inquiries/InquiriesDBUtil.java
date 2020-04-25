package com.javabeans.inquiries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class InquiriesDBUtil {
	private static InquiriesDBUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/java_beans_db";
	
	public static InquiriesDBUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new InquiriesDBUtil();
		}

		return instance;
	}

	private InquiriesDBUtil() throws Exception
	{
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException
	{
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public void sendInquiry(Inquiries inquiry) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			String sql = "INSERT INTO inquiries (first_name, last_name, email, message, date_received) "
					+ "VALUES (?, ?, ?, ?, ?)";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, inquiry.getFirst_name());
			myStmt.setString(2, inquiry.getLast_name());
			myStmt.setString(3, inquiry.getEmail());
			myStmt.setString(4, inquiry.getMessage());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			myStmt.setString(5, dtf.format(now));
			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}
	}
	
	public Inquiries getUser() throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT first_name, last_name, email FROM users WHERE user_id = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, 1);
			myRs = myStmt.executeQuery();
			Inquiries user = null;
			
			if (myRs.next()) {
				String first_name = myRs.getString("first_name");
				String last_name = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				user = new Inquiries(first_name, last_name, email);
			} else {
				throw new Exception("User does not exist.");
			}
			return user;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	private Connection getConnection() throws Exception {
		Connection theConn = dataSource.getConnection();
		return theConn;
	}
	
	private void close(Connection theConn, Statement theStmt) {
		try {
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
	
	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {
		try {
			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}
			
			if (theRs != null) {
				theRs.close();
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
