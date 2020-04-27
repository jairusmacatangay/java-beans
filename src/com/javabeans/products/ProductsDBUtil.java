package com.javabeans.products;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ProductsDBUtil {
	
	private static ProductsDBUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/java_beans_db";
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public static ProductsDBUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new ProductsDBUtil();
		}
		return instance;
	}
	
	private ProductsDBUtil() throws Exception {
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public List<Products> getProducts() throws Exception {
		List<Products> products = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();
			String sql = "SELECT product_id, product_category, product_name, "
					+ "description, price, product_image FROM products";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				int product_id = myRs.getInt("product_id");
				String product_category = myRs.getString("product_category");
				String product_name = myRs.getString("product_name");
				String description = myRs.getString("description");
				float price = myRs.getFloat("price");
				String product_image = myRs.getString("product_image");
				Products tempProduct = new Products(product_id, product_category,
						product_name, description, price, product_image);
				products.add(tempProduct);
			}
			return products;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
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

			if (myRs.next()) {
				theProduct.setProduct_id(myRs.getInt(1));
				theProduct.setProduct_category(myRs.getString(2));
				theProduct.setProduct_name(myRs.getString(3));
				theProduct.setDescription(myRs.getString(4));
				theProduct.setPrice(myRs.getFloat(5));
				theProduct.setProduct_image(myRs.getString(6));
				return theProduct;
			}
		} catch (Exception exc) {
			throw new Exception("Could not find product: " + exc.getMessage());
		} finally {
			close(myConn, myStmt, myRs);
		}
		return null;
	}
	
	

	
	public void addProduct(Products theProduct) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into products (product_category, product_name, "
					+ "description, price, product_image, date_created) values (?, ?, ?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, theProduct.getProduct_category());
			myStmt.setString(2, theProduct.getProduct_name());
			myStmt.setString(3, theProduct.getDescription());
			myStmt.setFloat(4, theProduct.getPrice());
			
			InputStream in = theProduct.getImage().getInputStream();
			
			File f = new File("/Users/Jairus Macatangay/Desktop/java-beans/WebContent/resources/img/products_img/"
					+ theProduct.getImage().getSubmittedFileName());
			f.createNewFile();
			
			FileOutputStream out = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int length;
			
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			
			out.close();
			in.close();
			
			myStmt.setString(5, f.getName()); 
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			myStmt.setString(6, dtf.format(now));
			
			myStmt.execute();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Error: ", ex);
			addErrorMessage(ex);
		} finally {
			close(myConn, myStmt);
		}
	}
	
	public void deleteProduct(int productId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();
			String sql = "delete from products where product_id = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, productId);
			myStmt.execute();
		} finally {
			close(myConn, myStmt);
		}
	}
		
	public Products getProduct(int product_id) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();
			String sql = "SELECT product_id, product_category, product_name,"
					+ " description, price, product_image FROM products "
					+ "WHERE product_id = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, product_id);
			myRs = myStmt.executeQuery();
			
			Products theProduct = null;
			
			if (myRs.next()) {
				int id = myRs.getInt("product_id");
				String product_category = myRs.getString("product_category");
				String product_name = myRs.getString("product_name");
				String description = myRs.getString("description");
				float price = myRs.getFloat("price");
				String product_image = myRs.getString("product_image");
				
				theProduct = new Products(id, product_category, product_name, description, price, product_image);
			} else {
				throw new Exception("Could not find product id: " + product_id);
			}
			return theProduct;
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Error: ", ex);
			addErrorMessage(ex);
			return null;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public void updateProduct(Products theProduct) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			String sql = "UPDATE products SET product_category = ?, "
					+ "product_name = ?, description = ?, price = ?, "
					+ "product_image = ?, date_last_modified = ? WHERE product_id = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, theProduct.getProduct_category());
			myStmt.setString(2, theProduct.getProduct_name());
			myStmt.setString(3, theProduct.getDescription());
			myStmt.setFloat(4, theProduct.getPrice());
			
			InputStream in = theProduct.getImage().getInputStream();
			
			File f = new File("/Users/Jairus Macatangay/Desktop/java-beans/WebContent/resources/img/products_img/"
					+ theProduct.getImage().getSubmittedFileName());
			f.createNewFile();
			
			FileOutputStream out = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int length;
			
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			
			out.close();
			in.close();
			
			myStmt.setString(5, f.getName()); 
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			myStmt.setString(6, dtf.format(now));
			myStmt.setInt(7, theProduct.getProduct_id());
			myStmt.execute();
		} finally {
			close(myConn, myStmt);
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
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}