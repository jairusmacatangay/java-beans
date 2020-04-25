package com.javabeans.orders;

import java.util.ArrayList;
import java.util.List;
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

import com.javabeans.users.Users;

public class OrdersDBUtil {

	private static OrdersDBUtil instance;
	private static DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/java_beans_db";
	
	public static OrdersDBUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new OrdersDBUtil();
		}

		return instance;
	}
	
	private OrdersDBUtil() throws Exception {
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
			
	public List<Orders> getCart(Users theUser) throws Exception {
		List<Orders> cart = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {

			myConn = getConnection();
			String sql = "SELECT o.ref_no, o.order_id, o.product_id, o.user_id,"
					+ " o.quantity, o.total_amount, p.product_category, "
					+ "p.product_name, p.price, p.product_image FROM "
					+ "order_details o INNER JOIN products p ON o.product_id = "
					+ "p.product_id WHERE o.order_id = 0 AND o.user_id = ?";

			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, theUser.getUser_id());
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				int ref_no = myRs.getInt(1);
				int order_id = myRs.getInt(2);
				int product_id = myRs.getInt(3);
				int user_id = myRs.getInt(4);
				int quantity = myRs.getInt(5);
				float total_amount = myRs.getFloat(6);
				String product_category = myRs.getString(7);
				String product_name = myRs.getString(8);
				float price = myRs.getFloat(9);
				String product_image = myRs.getString(10);

				Orders tempOrder = new Orders(ref_no, order_id, product_id, user_id, quantity, total_amount,
						product_category, product_name, price, product_image);

				cart.add(tempOrder);
			}

			return cart;

		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public Orders getOrderSummValues(Users theUser) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT SUM(total_amount) FROM order_details WHERE order_id = 0 and user_id = ?";
			
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, theUser.getUser_id());
			myRs = myStmt.executeQuery();
			
			Orders order = null;
			
			if (myRs.next()) {
				float total_amount = myRs.getFloat(1);
				float gross = total_amount * 0.88f;
				float vat = total_amount * 0.12f;
				float shippingFee = total_amount * 0.02f;
				float overallTotal = total_amount * 1.02f;
				
				order = new Orders(total_amount, gross, vat, shippingFee, overallTotal);
			} else {
				throw new Exception("Cart is empty.");
			}
			return order;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public void deleteCartItem(int ref_no) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			String sql = "DELETE FROM order_details WHERE ref_no = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, ref_no);
			myStmt.execute();
		} finally {
			//close(myConn, myStmt);
		}
	}	
	
	public Orders_Users getUserDetails(Users theUser) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT first_name, middle_name, last_name, email, "
					+ "mobile_no, bldg_no, street, city, barangay, province, "
					+ "zip_code FROM users WHERE user_id = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, theUser.getUser_id());
			myRs = myStmt.executeQuery();
			
			Orders_Users user = null;
			
			if (myRs.next()) {
				String first_name = myRs.getString("first_name");
				String middle_name = myRs.getString("middle_name");
				String last_name = myRs.getString("last_name");
				String email = myRs.getString("email");
				String mobile_no = myRs.getString("mobile_no");
				String bldg_no = myRs.getString("bldg_no");
				String street = myRs.getString("street");
				String city = myRs.getString("city");
				String barangay = myRs.getString("barangay");
				String province = myRs.getString("province");
				String zip_code = myRs.getString("zip_code");
				
				user = new Orders_Users(first_name, middle_name, last_name, email, 
						mobile_no, bldg_no, street, city, barangay, province, 
						zip_code);
			} else {
				throw new Exception("User does not exist.");
			}
			return user;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public void addOrder() throws Exception {
		Connection conn = null;
		PreparedStatement prepStmt = null;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO orders (status, date_ordered) VALUES (?, ?)";
			prepStmt = conn.prepareStatement(sql);
			prepStmt.setString(1, "Pending");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			prepStmt.setString(2, dtf.format(now));
			prepStmt.execute();
		} finally {
			close(conn, prepStmt);
		}
	}
	
	public int getRecentOrderID() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet myRs = null;
		int order_id = 0;
		
		try {
			conn = getConnection();
			
			String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
			stmt = conn.createStatement();
			myRs = stmt.executeQuery(sql);
			
			if (myRs.next()) {
				order_id = myRs.getInt("order_id");
			}
		} finally {
			close(conn, stmt, myRs);
		}
		return order_id;
	}
	
	public void updateCart(int order_id, Users theUser) throws Exception {
		Connection conn = null;
		PreparedStatement prepStmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "UPDATE order_details SET order_id = ? WHERE order_id = 0 AND user_id = ?";
			prepStmt = conn.prepareStatement(sql);
			prepStmt.setInt(1, order_id);
			prepStmt.setInt(2, theUser.getUser_id());
			prepStmt.execute();
		} finally {
			close(conn, prepStmt);
		}
	}
	
	public void placeOrder(Users theUser) throws Exception {
		int order_id = 0;
		
		addOrder();
		order_id = getRecentOrderID();
		updateCart(order_id, theUser);
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
	
	private void close(Connection theConn, PreparedStatement theStmt) {
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
}
