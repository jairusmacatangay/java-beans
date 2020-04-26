package com.javabeans.feedback;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Feedbacks {
	
	private int feedback_id;
	private int user_id;
	private int product_id;
	private String feedback;
	private Date date_created;
	
	//users table
	private String first_name;
	private String last_name;
	
	
	public Feedbacks() {
		
	}
	
	public Feedbacks(int feedback_id, int user_id, int product_id, String feedback, Date date_created) {
		this.feedback_id = feedback_id;
		this.user_id = user_id;
		this.product_id = product_id;
		this.feedback = feedback;
		this.date_created = date_created;
	}

	public int getFeedback_id() {
		return feedback_id;
	}

	public void setFeedback_id(int feedback_id) {
		this.feedback_id = feedback_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	
}
