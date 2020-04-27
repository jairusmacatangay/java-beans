package com.javabeans.orders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.javabeans.orders.OrdersDBUtil;
import com.javabeans.orders.Orders;

public class OrdersDBUtil {
	
	private static OrdersDBUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/java_beans_db";
	
	public static OrdersDBUtil getInstance() throws Exception
	{
		if(instance == null)
		{
			instance = new OrdersDBUtil();
		}
		
		return instance;
	}
	
	private OrdersDBUtil() throws Exception
	{
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException
	{
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public List<Orders> getOrders() throws Exception
	{
		List<Orders> orders = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try
		{
			myConn = getConnection();
			String sql = "SELECT orders.order_id, products.product_name, products.price, quantity, orders.status, total_amount, "
					+ "users.first_name, users.last_name FROM (((order_details "
					+ "INNER JOIN orders ON order_details.order_id = orders.order_id) "
					+ "INNER JOIN users ON order_details.user_id = users.user_id) "
					+ "INNER JOIN products ON order_details.product_id = products.product_id)";
			
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next())
			{
				int orderId = myRs.getInt("order_id");
				String productName = myRs.getString("product_name");
				int price = myRs.getInt("price");
				int qty = myRs.getInt("quantity");
				String status = myRs.getString("status");
				int total = myRs.getInt("total_amount");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				
				Orders tempOrder = new Orders(orderId, productName, price, qty, status, total, firstName, lastName);
				
				orders.add(tempOrder);
			}
		}
		finally
		{
			close(myConn, myStmt, myRs);
		}
		
		return orders;
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
