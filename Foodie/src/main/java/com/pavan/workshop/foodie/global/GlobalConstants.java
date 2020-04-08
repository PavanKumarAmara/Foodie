package com.pavan.workshop.foodie.global;

import com.pavan.workshop.foodie.helper.FileHelper;

public class GlobalConstants {
	public static final String applicationPath=FileHelper.getApplicationPath();
	public enum options {LOGIN,LOGOUT,ORDER,ADD_IEM,DELETE_ITEM,UPDATE_ITEM,REPORTS,FETCH_TOTAL_ORDERS,
										FETCH_RECENT_ORDERS, FETCH_TOTAL_SALE};
}
