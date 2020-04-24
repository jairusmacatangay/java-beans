package com.javabeans.products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;


public class ProductsDBUtil {
	
	private static ProductsDBUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/java_beans_db";
	
	public static ProductsDBUtil getInstance() throws Exception
	{
		if(instance == null)
		{
			instance = new ProductsDBUtil();
		}
		
		return instance;
	}
	
	private ProductsDBUtil() throws Exception
	{
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException
	{
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public List<Products> getProducts() throws Exception
	{
		List<Products> products = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try
		{
			myConn = getConnection();
			String sql = "select product_id, product_category, product_name, description, price from products";
			
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			//process results
			while (myRs.next())
			{
				//get data from result set row
				int id = myRs.getInt("product_id");
				String category = myRs.getString("product_category");
				String name = myRs.getString("product_name");
				String description = myRs.getString("description");
				float price = myRs.getFloat("price");
				
				//create product object
				Products tempProduct = new Products(id, name, category, price, description);
				
				//add it to the list of products
				products.add(tempProduct);
			}
		}
		finally
		{
			//clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
		
		return products;
	}
	
	public void addProduct(Products theProduct) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			
			String sql = "insert into products (product_category, product_name, description, price) values (?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			//set params
			myStmt.setString(1, theProduct.getCategory());
			myStmt.setString(2, theProduct.getName());
			myStmt.setString(3, theProduct.getDescription());
			myStmt.setFloat(4, theProduct.getPrice()); 
			//myStmt.setString(5, datetime);
			
			myStmt.execute();
		}
		finally 
		{
			//close(myConn, myStmt);
		}
		
	}
	
	public void deleteProduct(int productId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from products where product_id = ?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, productId);
			
			myStmt.execute();
		}
		finally 
		{
			//close (myConn, myStmt);
		}		
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
