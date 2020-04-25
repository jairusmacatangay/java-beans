package com.javabeans.orders;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Users {
	
	private int user_id;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String email;
	private String mobile_no;
	private String bldg_no;
	private String street;
	private String city;
	private String barangay;
	private String province;
	private String zip_code;
	
	public Users(String first_name, String middle_name, String last_name, 
			String email, String mobile_no, String bldg_no, String street, 
			String city, String barangay, String province, String zip_code) {
		this.first_name = first_name;
		this.middle_name = middle_name;
		this.last_name = last_name;
		this.email = email;
		this.mobile_no = mobile_no;
		this.bldg_no = bldg_no;
		this.street = street;
		this.city = city;
		this.barangay = barangay;
		this.province = province;
		this.zip_code = zip_code;
	}
	
	public Users() {
		
	}
	
	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public String getFirst_name() {
		return first_name;
	}
	
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	public String getMiddle_name() {
		return middle_name;
	}
	
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
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
	
	public String getMobile_no() {
		return mobile_no;
	}
	
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	
	public String getBldg_no() {
		return bldg_no;
	}
	
	public void setBldg_no(String bldg_no) {
		this.bldg_no = bldg_no;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getBarangay() {
		return barangay;
	}
	
	public void setBarangay(String barangay) {
		this.barangay = barangay;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getZip_code() {
		return zip_code;
	}
	
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
}
