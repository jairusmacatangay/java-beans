package com.javabeans.feedback;

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

public class FeedbacksDBUtil {
	private static FeedbacksDBUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/java_beans_db";

	public static FeedbacksDBUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new FeedbacksDBUtil();
		}
		return instance;
	}

	private FeedbacksDBUtil() throws Exception {
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}

	public List<Feedbacks> getFeedbacks(int product_id) throws Exception {

		List<Feedbacks> feedbacks = new ArrayList<>();

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();
			String sql = "SELECT u.first_name, u.last_name, f.date_created, f.feedback " + "FROM feedbacks f "
					+ "INNER JOIN users u ON u.user_id = f.user_id "
					+ "INNER JOIN products p ON p.product_id = f.product_id "
					+ "WHERE p.product_id = ? ORDER BY date_created DESC";

			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, product_id);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Feedbacks feedback = new Feedbacks();
				feedback.setFirst_name(myRs.getString(1));
				feedback.setLast_name(myRs.getString(2));
				feedback.setDate_created(myRs.getDate(3));
				feedback.setFeedback(myRs.getString(4));

				feedbacks.add(feedback);
			}
			return feedbacks;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	public void addFeedback(Feedbacks theFeedback) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();
			String sql = "INSERT INTO feedbacks (user_id, product_id, feedback, date_created) VALUE (?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, theFeedback.getUser_id());
			myStmt.setInt(2, theFeedback.getProduct_id());
			myStmt.setString(3, theFeedback.getFeedback());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			myStmt.setString(4, dtf.format(now));
			myStmt.execute();
		} finally {
			// clear text in the textarea
			theFeedback.setFeedback(null);
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
