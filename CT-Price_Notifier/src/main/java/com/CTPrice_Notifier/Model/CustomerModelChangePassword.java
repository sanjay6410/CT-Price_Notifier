package com.CTPrice_Notifier.Model;

public class CustomerModelChangePassword {

	private String email;
	private String newPassword;
	private String oldPassword;
	public CustomerModelChangePassword() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerModelChangePassword(String email, String newPassword, String oldPassword) {
		super();
		this.email = email;
		this.newPassword = newPassword;
		this.oldPassword = oldPassword;
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
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	
}
