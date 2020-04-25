package com.javabeans.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Users {

	//User variables
	private int user_id;
	private String user_type;
	private String email;
	private String password = "";
	private String first_name;
	private String middle_name;
	private String last_name;
	private String telephone_no;
	private String mobile_no;
	private Date DOB;
	private String gender;
	private String bldg_no;
	private String street;
	private String city;
	private String barangay;
	private String province;
	private String zip_code;
	
	//password length
	private String passwordResult;
	
	//confirm password
	private String confirmPassword = "";
	private String confirmPasswordResult;
	
	//List Options for Gender Type
	List<String>genderOptions;
	
	//order_details, orders, products table for transaction history
	private int ref_no; //order_details
	private int quantity; //order_details
	private int order_id; //orders
	private Date date_ordered; //orders
	private String product_name; //products
	private float price; //products
	
	
	public Users() {
		genderOptions = new ArrayList<>();
		
		genderOptions.add("Male");
		genderOptions.add("Female");
		
	}
	
	public Users(int user_id, String user_type, String email, String password, String first_name, 
			String middle_name, String last_name, String telephone_no, String mobile_no, Date DOB, String gender, 
			String bldg_no, String street, String city, String barangay, String province, String zip_code) 
	{
		this.user_id = user_id;
		this.user_type = user_type;
		this.email = email;
		this.password = password;
		this.first_name = first_name;
		this.middle_name = middle_name;
		this.last_name = last_name;
		this.telephone_no = telephone_no;
		this.mobile_no = mobile_no;
		this.DOB = DOB;
		this.gender = gender;
		this.bldg_no = bldg_no;
		this.street = street;
		this.city = city;
		this.barangay = barangay;
		this.province = province;
		this.zip_code = zip_code;
	}
	
	public Users(int ref_no, int order_id, String product_name, float price, int quantity, Date date_ordered) {
		this.ref_no = ref_no;
		this.order_id = order_id;
		this.product_name = product_name;
		this.price = price;
		this.quantity = quantity;
		this.date_ordered = date_ordered;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getTelephone_no() {
		return telephone_no;
	}

	public void setTelephone_no(String telephone_no) {
		this.telephone_no = telephone_no;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public List<String> getGenderOptions() {
		return genderOptions;
	}

	public void setGenderOptions(List<String> genderOptions) {
		this.genderOptions = genderOptions;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}


	public String getPasswordResult() {
		
		if(!password.isEmpty())
		{
			
			if(password.length() < 7) {
				passwordResult = "Password strength is poor";
			}
			else if(password.length() < 10)	{
				passwordResult = "Password strength is medium";
			}
			else {
				passwordResult = "Password strength is strong";
			}
		}
		else		
			passwordResult = "";
			
		return passwordResult;

	}

	public void setPasswordResult(String passwordResult) {
		this.passwordResult = passwordResult;
	}

	public String getConfirmPasswordResult() {
		
		if(!confirmPassword.isEmpty())
		{
			if(confirmPassword.equals(password)){
				confirmPasswordResult = "Confirm Password matches with Password!";
			}
			else {
				confirmPasswordResult = "Confirm Password does not match with Password!";
			}
		}
		else {
			confirmPasswordResult = "";
		}
		
		return confirmPasswordResult;
	}

	public void setConfirmPasswordResult(String confirmPasswordResult) {
		this.confirmPasswordResult = confirmPasswordResult;
	}
	
	//Order_Details, Orders, Products table for Transaction History

	public int getRef_no() {
		return ref_no;
	}

	public void setRef_no(int ref_no) {
		this.ref_no = ref_no;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public Date getDate_ordered() {
		return date_ordered;
	}

	public void setDate_ordered(Date date_ordered) {
		this.date_ordered = date_ordered;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
}
