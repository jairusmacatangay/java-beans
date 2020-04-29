package com.javabeans.inquiries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.javabeans.users.Users;

public class InquiriesDBUtil {
	private static InquiriesDBUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/java_beans_db";
	private Logger logger = Logger.getLogger(getClass().getName());
	
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
	
	public Inquiries getUser(Users theUser) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT first_name, last_name, email FROM users WHERE user_id = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, theUser.getUser_id());
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
	
	public List<Inquiries> getInquiries() throws Exception {
		List<Inquiries> inquiries = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT inquiry_id, first_name, last_name, email, date_received, status FROM inquiries";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			Inquiries tempInquiry = null;
			
			while (myRs.next()) {
				int inquiry_id = myRs.getInt(1);
				String first_name = myRs.getString(2);
				String last_name = myRs.getString(3);
				String email = myRs.getString(4);
				String date_received = myRs.getString(5);
				String status = myRs.getString(6);
				
				tempInquiry = new Inquiries(inquiry_id, first_name, last_name, email, date_received, status);
				inquiries.add(tempInquiry);
			}
			return inquiries;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public Inquiries getInquiry(int inquiry_id) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();
			String sql = "SELECT inquiry_id, first_name, last_name, email, message, "
					+ "date_received, status FROM inquiries WHERE inquiry_id = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, inquiry_id);
			myRs = myStmt.executeQuery();
			
			Inquiries inquiry = null;
			
			if (myRs.next()) {
				int id = myRs.getInt(1);
				String first_name = myRs.getString(2);
				String last_name = myRs.getString(3);
				String email = myRs.getString(4);
				String message = myRs.getString(5);
				String date_received = myRs.getString(6);
				String status = myRs.getString(7);
				
				inquiry = new Inquiries(id, first_name, last_name, email, message, date_received, status);
			}
			return inquiry;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	public void sendMail(String email, String subject, String reply) throws MessagingException {
		logger.info("Got inside of sendMail()");
		final String username = "place email here";
		final String password = "place password here";
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtps.port", "465");
		properties.put("mail.smtp.user", username);
		properties.put("mail.smtp.debug", "true");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");
		logger.info("properties put");
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		session.setDebug(true);
		
		logger.info("session");
		
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("javabeans@sample.com"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
		message.setSubject(subject);
		message.setText(reply);
		
		Transport t = session.getTransport("smtps");
		t.connect("smtp.gmail.com", Integer.valueOf("465"), username, password);
		t.sendMessage(message, message.getAllRecipients());
		t.close();
		logger.info("message sent");
	}
	
	public void sendReply(Inquiries inquiry) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			String sql = "UPDATE inquiries SET reply = ?, date_replied = ?, status = ? WHERE inquiry_id = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, inquiry.getReply());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			myStmt.setString(2, dtf.format(now));
			myStmt.setString(3, "Replied");
			myStmt.setInt(4, inquiry.getInquiry_id());
			myStmt.execute();
			logger.info("Reached end of execute query");
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Error: ", ex);
			addErrorMessage(ex);
		} finally {
			close(myConn, myStmt);
		}
		
		sendMail(inquiry.getEmail(), inquiry.getSubject(), inquiry.getReply());
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
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
