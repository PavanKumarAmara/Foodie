package com.pavan.workshop.foodie.bean;

import java.util.HashMap;

import com.pavan.workshop.foodie.helper.ValidationHelper;

public class LoginDetails {
	private String userName;
	private String password;
	private boolean  isPasswordSet;
	
	public boolean isPasswordSet() {
		return isPasswordSet;
	}
	public void setPasswordSet(boolean isPasswordSet) {
		this.isPasswordSet = isPasswordSet;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public HashMap<String, String> getUserNameAndpassword() {
		return userNameAndpassword;
	}
	public void setUserNameAndpassword(HashMap<String, String> userNameAndpassword) {
		this.userNameAndpassword = userNameAndpassword;
	}
	private HashMap<String, String> userNameAndpassword=null;
	private void LoginDetails(){};
	public static LoginDetails loginDetails=null;
	
	public static LoginDetails getLoginDetails(){
		if(loginDetails==null){
			loginDetails=new LoginDetails();
		}
		return loginDetails;
	}
	public boolean validatePassword(String key,String value){
		return ValidationHelper.validateKeyValue(userNameAndpassword, key, value);
	}
}
