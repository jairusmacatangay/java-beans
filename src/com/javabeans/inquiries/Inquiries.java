package com.javabeans.inquiries;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Inquiries {
	//Inquiries Table
	private int inquiry_id;
	private String first_name;
	private String last_name;
	private String email;
	private String message;
	private String date_received;
	private String status;
	private int user_id;
	private String date_replied;
	
	//Mail Var
	public String subject;
	private String reply;
	
	public Inquiries() {
		
	}

	public Inquiries(String first_name, String last_name, String email) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
	}
	
	public Inquiries(int inquiry_id, String first_name, String last_name, String email, String date_received, String status) {
		this.inquiry_id = inquiry_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.date_received = date_received;
		this.status = status;
	}
	
	public Inquiries(int inquiry_id, String first_name, String last_name,
			String email, String message, String date_received, String status) {
		this.inquiry_id = inquiry_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.message = message;
		this.date_received = date_received;
		this.status = status;
	}
	
	
	public Inquiries(String first_name, String last_name, String email, String message, String date_received) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.message = message;
		this.date_received = date_received;
	}
	
	public int getInquiry_id() {
		return inquiry_id;
	}

	public void setInquiry_id(int inquiry_id) {
		this.inquiry_id = inquiry_id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate_received() {
		return date_received;
	}

	public void setDate_received(String date_received) {
		this.date_received = date_received;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getDate_replied() {
		return date_replied;
	}

	public void setDate_replied(String date_replied) {
		this.date_replied = date_replied;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
