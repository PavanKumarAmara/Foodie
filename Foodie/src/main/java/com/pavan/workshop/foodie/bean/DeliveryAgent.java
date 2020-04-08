package com.pavan.workshop.foodie.bean;

import java.util.ArrayList;

public class DeliveryAgent  extends Person implements OrderStats{

	@Override
	public int totalOrders() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Order> recentOrders(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int totalSale() {
		// TODO Auto-generated method stub
		return 0;
	}

}
