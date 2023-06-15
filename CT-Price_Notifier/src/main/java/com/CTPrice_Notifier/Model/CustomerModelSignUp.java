package com.CTPrice_Notifier.Model;

public class CustomerModelSignUp {

	private String email;
	private String password;
	private String firstName;
	private String lasName;
	private String mobileNumber;
	public CustomerModelSignUp(String email, String password, String firstName, String lasName, String mobileNumber) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lasName = lasName;
		this.mobileNumber = mobileNumber;
	}
	public CustomerModelSignUp() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLasName() {
		return lasName;
	}
	public void setLasName(String lasName) {
		this.lasName = lasName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	
	
}
