package com.pavan.workshop.foodie.bean;

import java.util.ArrayList;

public class Order extends  StandardDetails {
	private String[] itemIds;
	private int orderTotal;
   private String customerName;
   
	public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
	
	public String[] getItemIds() {
	return itemIds;
}
public void setItemIds(String[] itemIds) {
	this.itemIds = itemIds;
}
	public int getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(int orderTotal) {
		this.orderTotal = orderTotal;
	}
	
}
