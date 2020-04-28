package com.javabeans.dashboard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DashboardDBUtil {
	private static DashboardDBUtil instance;
	private static DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/java_beans_db";
	
	public static DashboardDBUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new DashboardDBUtil();
		}
		return instance;
	}
	
	private DashboardDBUtil() throws Exception {
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	

	public int getTotalUsers() throws Exception {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		int totalUsers = 0;
		
		try {
			myConn = getConnection();
			String sql = "SELECT COUNT(user_id) FROM users";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			if (myRs.next()) {
				totalUsers = myRs.getInt(1);
			}
			
			return totalUsers;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public int getTotalProducts() throws Exception {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		int totalProducts = 0;
		
		try {
			myConn = getConnection();
			String sql = "SELECT COUNT(product_id) FROM products";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			if (myRs.next()) {
				totalProducts = myRs.getInt(1);
			}
			
			return totalProducts;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public int getTotalOrders() throws Exception {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		int totalOrders = 0;
		
		try {
			myConn = getConnection();
			String sql = "SELECT COUNT(order_id) FROM orders";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			if (myRs.next()) {
				totalOrders = myRs.getInt(1);
			}
			
			return totalOrders;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public float getTotalEarnings() throws Exception {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		float totalEarnings = 0;
		
		try {
			myConn = getConnection();
			String sql = "SELECT SUM(`total_amount`) FROM `order_details` WHERE `order_id` != 0";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			if (myRs.next()) {
				totalEarnings = myRs.getFloat(1);
			}
			
			return totalEarnings;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public Dashboard getDashboardValues() throws Exception {
		Dashboard values = null;
		
		int totalUsers = getTotalUsers();
		int totalProducts = getTotalProducts();
		int totalOrders = getTotalOrders();
		float totalEarnings = getTotalEarnings();
		
		values = new Dashboard(totalUsers, totalProducts, totalOrders, totalEarnings);
		
		return values;
	}
	
	private static Connection getConnection() throws Exception {
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
