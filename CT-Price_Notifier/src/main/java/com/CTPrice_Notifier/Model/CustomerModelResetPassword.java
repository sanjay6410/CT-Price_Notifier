package com.CTPrice_Notifier.Model;

public class CustomerModelResetPassword {

	private String email;
	private String newPassword;
	public CustomerModelResetPassword() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerModelResetPassword(String email, String newPassword) {
		super();
		this.email = email;
		this.newPassword = newPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
}
