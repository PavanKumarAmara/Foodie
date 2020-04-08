package com.pavan.workshop.foodie.bean;

import java.util.ArrayList;

public class Customer extends Person implements OrderStats{
	public int totalOrders(){
		return 0;
	}

	@Override
	public ArrayList<Order> recentOrders(int count) {
		return null;
	}

	@Override
	public int totalSale() {
		return 0;
	}
}
