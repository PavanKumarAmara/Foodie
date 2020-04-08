package com.pavan.workshop.foodie.bean;

import java.util.ArrayList;

public interface OrderStats {
	int totalOrders();
	ArrayList<Order> recentOrders(int count);
	int totalSale();
}
