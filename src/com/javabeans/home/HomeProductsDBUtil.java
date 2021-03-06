package com.javabeans.home;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class HomeProductsDBUtil {
	private static HomeProductsDBUtil instance;
	private static DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/java_beans_db";
	
	public static HomeProductsDBUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new HomeProductsDBUtil();
		}
		return instance;
	}
	
	private HomeProductsDBUtil() throws Exception {
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public List<HomeProducts> getEquipment() throws Exception {
		List<HomeProducts> equipment = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT product_id, product_name, price, product_image FROM"
					+ " products WHERE product_category = 'Equipment' LIMIT 4";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				int product_id = myRs.getInt("product_id");
				String product_name = myRs.getString("product_name");
				float price = myRs.getFloat("price");
				String product_image = myRs.getString("product_image");
				HomeProducts tempEquipment = new HomeProducts(product_id, product_name, price, product_image);
				equipment.add(tempEquipment);
			}
			return equipment;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<HomeProducts> getCoffee() throws Exception {
		List<HomeProducts> coffee = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT product_id, product_name, price, product_image FROM"
					+ " products WHERE product_category = 'Coffee' LIMIT 4";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				int product_id = myRs.getInt("product_id");
				String product_name = myRs.getString("product_name");
				float price = myRs.getFloat("price");
				String product_image = myRs.getString("product_image");
				HomeProducts tempCoffee = new HomeProducts(product_id, product_name, price, product_image);
				coffee.add(tempCoffee);
			}
			return coffee;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<HomeProducts> getDrinkware() throws Exception {
		List<HomeProducts> drinkware = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT product_id, product_name, price, product_image FROM"
					+ " products WHERE product_category = 'Drinkware' LIMIT 4";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				int product_id = myRs.getInt("product_id");
				String product_name = myRs.getString("product_name");
				float price = myRs.getFloat("price");
				String product_image = myRs.getString("product_image");
				HomeProducts tempDrinkware = new HomeProducts(product_id, product_name, price, product_image);
				drinkware.add(tempDrinkware);
			}
			return drinkware;
		} finally {
			close(myConn, myStmt, myRs);
		}
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
