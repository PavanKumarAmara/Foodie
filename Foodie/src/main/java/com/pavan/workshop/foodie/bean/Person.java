package com.pavan.workshop.foodie.bean;

public class Person extends StandardDetails{
	private String name;
	private String Address;
	private String mobileNo;
    enum gender {MALE,FEMALE}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	};
}
