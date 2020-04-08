package com.pavan.workshop.foodie.bean;

import java.util.ArrayList;

public class DataObject {
	private ArrayList<Customer> customers;
	private ArrayList<Order> orders;
	private ArrayList<Item> item;
	
	public ArrayList<Item> getItem() {
		return item;
	}

	public void setItem(ArrayList<Item> item) {
		this.item = item;
	}

	public static DataObject data;
	private DataObject(){}
	
	public static DataObject getDataObject(){
		if(data==null){
			data=new DataObject();
		}
		return data;
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
}
