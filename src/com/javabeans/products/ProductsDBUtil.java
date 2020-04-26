package com.javabeans.products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class ProductsDBUtil {
	private static ProductsDBUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/java_beans_db";
	
	public static ProductsDBUtil getInstance() throws Exception {
		if(instance == null) {
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
	
	//retrieve all products ----- FOR TESTING ONLY(Products Catalog not my part, just for button)!!! -Bern
	public List<Products> getProducts() throws Exception {
		List<Products> products = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT product_id, product_name, price, product_image FROM products";
			
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			Products product;
			while(myRs.next()) {
				product = new Products();
				product.setProduct_id(myRs.getInt(1));
				product.setProduct_name(myRs.getString(2));
				product.setPrice(myRs.getFloat(3));
				product.setDescription(myRs.getString(4));
				
				products.add(product);
			}
			return products;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	//retrieve product details
	public Products getProductDetails(Products theProduct) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT * FROM products WHERE product_id = ?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, theProduct.getProduct_id());
			
			myRs = myStmt.executeQuery();
			
			if(myRs.next()) {
				theProduct.setProduct_id(myRs.getInt(1));
				theProduct.setProduct_category(myRs.getString(2));
				theProduct.setProduct_name(myRs.getString(3));
				theProduct.setDescription(myRs.getString(4));
				theProduct.setPrice(myRs.getFloat(5));
				theProduct.setProduct_image(myRs.getString(6));
				return theProduct;
			}
		}catch (Exception exc) {
			throw new Exception("Could not find product: " + exc.getMessage());
		}finally {
			close(myConn, myStmt, myRs);
		}
		return null;
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
