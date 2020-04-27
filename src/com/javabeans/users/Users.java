package com.javabeans.users;

public class Users {
	
	private String firstName;
	private String lastName;
	private String city;
	private String gender;
	//private String dob;
	
	public Users(String firstName, String lastName, String city, String gender)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.gender = gender;
		//this.dob = dob;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/*public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}*/

}
