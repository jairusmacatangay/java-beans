package com.javabeans.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class UsersDBUtil {

	private static UsersDBUtil instance;
	private static DataSource dataSource;
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
	
	//register
	public void addUser(Users theUser) throws Exception
	{
		Connection myCon = null;
		PreparedStatement myStmt = null;
		
		try
		{
			myCon = getConnection();
			String sql = "INSERT INTO users (user_type, email, password, first_name, middle_name, last_name, "
					+ "telephone_no, mobile_no, DOB, gender, bldg_no, street, city, barangay, province, zip_code, date_created) "
					+ "VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			myStmt = myCon.prepareStatement(sql);
			
			//set the parameters
			myStmt.setString(1, "Customer");
			myStmt.setString(2, theUser.getEmail());
			myStmt.setString(3, theUser.getPassword());
			myStmt.setString(4, theUser.getFirst_name());
			myStmt.setString(5, theUser.getMiddle_name());
			myStmt.setString(6, theUser.getLast_name());
			myStmt.setString(7, theUser.getTelephone_no());
			myStmt.setString(8, theUser.getMobile_no());
			myStmt.setDate(9, new java.sql.Date(theUser.getDOB().getTime()));
			myStmt.setString(10, theUser.getGender());
			myStmt.setString(11, theUser.getBldg_no());
			myStmt.setString(12, theUser.getStreet());
			myStmt.setString(13, theUser.getCity());
			myStmt.setString(14, theUser.getBarangay());
			myStmt.setString(15, theUser.getProvince());
			myStmt.setString(16, theUser.getZip_code());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			myStmt.setString(17, dtf.format(now));
			
			myStmt.execute();
		} 
		finally
		{
			//close (myConn, myStmt);
		}
	}
	
	//login
	public Users validateUser(Users theUser) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT user_id, user_type, email, password FROM users WHERE email = ? AND password = ?";
			
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, theUser.getEmail());
			myStmt.setString(2, theUser.getPassword());
			myRs = myStmt.executeQuery();
			
			if(myRs.next()) {
				theUser.setUser_id(myRs.getInt(1));
				theUser.setUser_type(myRs.getString(2));
				return theUser;
			} 
		} catch (Exception ex){
			throw new Exception("Could not find user: " + ex.getMessage());
		} finally {
			close (myConn, myStmt, myRs);
		}
		return null;
	}
	
	//retrieve user details
	public Users getUserDetails(Users theUser) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql ="SELECT * FROM users WHERE user_id = ?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, theUser.getUser_id());
			
			myRs = myStmt.executeQuery();
			
			
			if(myRs.next()) {
				theUser.setUser_id(myRs.getInt(1));
				theUser.setUser_type(myRs.getString(2));
				theUser.setEmail(myRs.getString(3));
				theUser.setPassword(myRs.getString(4));
				theUser.setFirst_name(myRs.getString(5));;
				theUser.setMiddle_name(myRs.getString(6));
				theUser.setLast_name(myRs.getString(7));
				theUser.setTelephone_no(myRs.getString(8));
				theUser.setMobile_no(myRs.getString(9));
				theUser.setDOB(myRs.getDate(10));
				theUser.setGender(myRs.getString(11));
				theUser.setBldg_no(myRs.getString(12));
				theUser.setStreet(myRs.getString(13));
				theUser.setCity(myRs.getString(14));
				theUser.setBarangay(myRs.getString(15));
				theUser.setProvince(myRs.getString(16));
				theUser.setZip_code(myRs.getString(17));
				return theUser;
			} 
			
		}catch (Exception exc) {
			throw new Exception("Could not find user: " + exc.getMessage());
		}finally {
			close(myConn, myStmt, myRs);
		}
		return null;
	}
	
	public Users getUserDetailsForCheckout(Users theUser) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql ="SELECT * FROM users WHERE user_id = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, theUser.getUser_id());
			myRs = myStmt.executeQuery();
			
			if(myRs.next()) {
				theUser.setUser_id(myRs.getInt(1));
				theUser.setUser_type(myRs.getString(2));
				theUser.setEmail(myRs.getString(3));
				theUser.setPassword(myRs.getString(4));
				theUser.setFirst_name(myRs.getString(5));;
				theUser.setMiddle_name(myRs.getString(6));
				theUser.setLast_name(myRs.getString(7));
				theUser.setTelephone_no(myRs.getString(8));
				theUser.setMobile_no(myRs.getString(9));
				theUser.setDOB(myRs.getDate(10));
				theUser.setGender(myRs.getString(11));
				theUser.setBldg_no(myRs.getString(12));
				theUser.setStreet(myRs.getString(13));
				theUser.setCity(myRs.getString(14));
				theUser.setBarangay(myRs.getString(15));
				theUser.setProvince(myRs.getString(16));
				theUser.setZip_code(myRs.getString(17));
				return theUser;
			} 
		}catch (Exception exc) {
			throw new Exception("Could not find user: " + exc.getMessage());
		}finally {
			close(myConn, myStmt, myRs);
		}
		return null;
	}	
	
	//update user details
	public void updateUserDetails(Users theUser) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			String sql = "UPDATE users "
						+ "SET first_name=?, middle_name=?, last_name=?, telephone_no=?, mobile_no=?, DOB=?, "
						+ "gender=?, bldg_no=?, street=?, city=?, barangay=?, province=?, zip_code=?, date_last_modified=now()"
						+ "WHERE user_id=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, theUser.getFirst_name());
			myStmt.setString(2, theUser.getMiddle_name());
			myStmt.setString(3, theUser.getLast_name());
			myStmt.setString(4, theUser.getTelephone_no());
			myStmt.setString(5, theUser.getMobile_no());
			myStmt.setDate(6, new java.sql.Date(theUser.getDOB().getTime()));
			myStmt.setString(7, theUser.getGender());
			myStmt.setString(8, theUser.getBldg_no());
			myStmt.setString(9, theUser.getStreet());
			myStmt.setString(10, theUser.getCity());
			myStmt.setString(11, theUser.getBarangay());
			myStmt.setString(12, theUser.getProvince());
			myStmt.setString(13, theUser.getZip_code());
			myStmt.setInt(14, theUser.getUser_id());
			myStmt.execute();
		} finally {
			//
		}
	}
	
	public void updateUserDetailsForCheckout(Users theUser) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			String sql = "UPDATE users "
						+ "SET first_name=?, middle_name=?, last_name=?, telephone_no=?, mobile_no=?, DOB=?, "
						+ "gender=?, bldg_no=?, street=?, city=?, barangay=?, province=?, zip_code=?, date_last_modified=now()"
						+ "WHERE user_id=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, theUser.getFirst_name());
			myStmt.setString(2, theUser.getMiddle_name());
			myStmt.setString(3, theUser.getLast_name());
			myStmt.setString(4, theUser.getTelephone_no());
			myStmt.setString(5, theUser.getMobile_no());
			myStmt.setDate(6, new java.sql.Date(theUser.getDOB().getTime()));
			myStmt.setString(7, theUser.getGender());
			myStmt.setString(8, theUser.getBldg_no());
			myStmt.setString(9, theUser.getStreet());
			myStmt.setString(10, theUser.getCity());
			myStmt.setString(11, theUser.getBarangay());
			myStmt.setString(12, theUser.getProvince());
			myStmt.setString(13, theUser.getZip_code());
			myStmt.setInt(14, theUser.getUser_id());
			myStmt.execute();
		} finally {
			//
		}
	}
	
	//get transaction history list
	public List<Users> getTransactionHistory(Users theUser) throws Exception {
		List<Users>transactions = new ArrayList<>();
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT od.ref_no, o.order_id, p.product_name, p.price, od.quantity, o.date_ordered, od.user_id "
					+ "FROM order_details od "
					+ "INNER JOIN orders o ON od.order_id = o.order_id "
					+ "INNER JOIN products p ON od.product_id = p.product_id "
					+ "WHERE od.user_id=?";
			
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, theUser.getUser_id());
			myRs = myStmt.executeQuery();

			
			while(myRs.next()) {
				Users user = new Users();
				user.setRef_no(myRs.getInt(1));
				user.setOrder_id(myRs.getInt(2));
				user.setProduct_name(myRs.getString(3));
				user.setPrice(myRs.getFloat(4));
				user.setQuantity(myRs.getInt(5));
				user.setDate_ordered(myRs.getDate(6));
				user.setUser_id(myRs.getInt(7));
				
				transactions.add(user);
				
			}
			return transactions;
			
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	
	private  Connection getConnection() throws Exception {

		Connection theCon = dataSource.getConnection();
		
		return theCon;
	}
	
	private void close(Connection theCon, Statement theStmt, ResultSet theRs) {

		try 
		{
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theCon != null) {
				theCon.close();
			}
			
		} catch (Exception exc) 
		{
			exc.printStackTrace();
		}
	}
}
