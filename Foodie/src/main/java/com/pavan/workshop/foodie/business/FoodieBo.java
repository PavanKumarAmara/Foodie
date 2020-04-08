package com.pavan.workshop.foodie.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.pavan.workshop.foodie.bean.DataObject;
import com.pavan.workshop.foodie.bean.Item;
import com.pavan.workshop.foodie.bean.LoginDetails;
import com.pavan.workshop.foodie.bean.Order;
import com.pavan.workshop.foodie.exceptions.InvalidAction;
import com.pavan.workshop.foodie.exceptions.InvalidInputLength;
import com.pavan.workshop.foodie.exceptions.InvalidItems;
import com.pavan.workshop.foodie.exceptions.InvalidPassword;
import com.pavan.workshop.foodie.exceptions.InvalidRecentOrdersCount;
import com.pavan.workshop.foodie.global.GlobalVariables;
import com.pavan.workshop.foodie.helper.ValidationHelper;
import com.pavan.workshop.foodie.util.DataLoader;

public class FoodieBo {
	static Scanner sc=null;
	public static void main(String[] args) {
			startPreProcessing();
			displayHelp();
			int action=Integer.parseInt(getValueFromStandardInput("ACTIONS"));
			proceedWithAction(action);
		//	sc.nextLine();
			logout();
			startPostProcessing();
	}

	private static void proceedWithAction(int action) {
		switch(action){
			case 0:
				addItem("add");
				break;
			case 1:
				updateItem();
				break;
			case 2:
				deleteItem();
				break;
			case 3:
				viewItems();
				break;
			case 4:
				selectItems();
				break;
			case 5:
				payBill();
				break;
			case 6:
				fetchTotalNoOfOrders();
				break;
			case 7:
				fetchRecentOrders();
				break;
			case 8:
				fetchTotalSale();
				break;
		}
	}

	private static void addItem(String mode) {
		Item item=new Item();
		System.out.println("Enter Item Id..\n");
		item.setId(Integer.parseInt(getValueFromStandardInput("ItemId")));
		System.out.println("Enter Item Name..\n");
		item.setName(getValueFromStandardInput("ItemName"));
		System.out.println("Enter Item Price..\n");
		item.setPrice(Integer.parseInt(getValueFromStandardInput("ItemPrice")));
		DataObject data=DataObject.getDataObject();
		boolean isItemPresent=isItemPresent(data.getItem(),item.getId());
		if(mode.equals("update")){
			if(isItemPresent){
				updateItem(data.getItem(),item,"update");
				 System.out.println(mode+"...\tSuccess");
			}else{
				System.out.println("First Add item to update..");
			}
		}else if(mode.equals("delete")){
			if(isItemPresent){
				updateItem(data.getItem(),item,"delete");
				 System.out.println(mode+"...\tSuccess");
			}else{
				System.out.println("First Add item to delete..");
			}
		}
		else{
			if(!isItemPresent){
				data.getItem().add(item);
				}
		}
		 System.out.println("Enter any action or type exit to close application");
		getValueFromStandardInput("GENERIC");
	}

	private static void updateItem(ArrayList<Item> items, Item itemToUpdate,String mode) {
		int i=0;
		for(i=0;i< items.size();i++){
			if(items.get(i).getId()==itemToUpdate.getId()){
				break;
			}
		}
		items.remove(i);
		if(mode.equals("update")){
			items.add(itemToUpdate);
		}
	}

	private static void updateItem() {
		addItem("update");
	}

	private static void deleteItem() {
		addItem("delete");
	}

	private static void viewItems() {
		ArrayList<Item> items=DataObject.getDataObject().getItem();
		if(items!=null){
			for(Item item: items){
				System.out.println("Item Name : "+ item.getName()+"\tItem Cost : "+item.getPrice()+"\tItem Id : "+item.getId());
			}
		}
		else{
			System.out.println("No Items to display!");
		}
		 System.out.println("Enter any action or type  exit to close application");
		getValueFromStandardInput("GENERIC");
	}

	private static void selectItems() {
		 System.out.println("Enter Space Separated item ids to select. Maximum upto 3");
		 String itemIds=getValueFromStandardInput("ItemIds");
		 Order or=new Order();
		 or.setOrderTotal(getBill(itemIds));
		 String summary="Total Bill Is : "+or.getOrderTotal();
		 System.out.println(summary);
         or.setCustomerName(LoginDetails.getLoginDetails().getUserName());
         or.setId(DataObject.getDataObject().getOrders().size()+1);
         or.setItemIds(itemIds.split(" "));   
		 System.out.println("Proceed to order by typing order or exit to close application");
		 getValueFromStandardInput("order");
		 DataObject.getDataObject().getOrders().add(or);
			System.out.println("Order Successfull");
			System.out.println("Enter any action or exit to close application");
			getValueFromStandardInput("GENERIC");
	}



	private static void payBill() {
		System.out.println("gateways is not available.. Proceed with other actions");
		getValueFromStandardInput("GENERIC");		
	}

	private static void fetchTotalNoOfOrders() {
		if(LoginDetails.getLoginDetails().getUserName().equals("Admin")){
			System.out.println("Total no of orders recieved : "+DataObject.getDataObject().getOrders().size());
		}else{
			System.out.println("Total no of orders placed by you : "+getOrderCountByCustomerName(LoginDetails.getLoginDetails().getUserName()));
		}
		System.out.println("Enter any action or type exit to close application");
		getValueFromStandardInput("GENERIC");
	}

	private static int getOrderCountByCustomerName(String userName) {
		int count=0;
		for(Order order:DataObject.getDataObject().getOrders()){
			if(order.getCustomerName().equals(userName)){
				count++;
			}
		}
		return count;
	}

	private static void fetchRecentOrders() {
		System.out.println("Enter no of recent transactions that you want to view or type exit to close application");
		int upTo=Integer.parseInt(getValueFromStandardInput("upTo"));
			ArrayList<Order> orders;
			try {
				orders = getOrdersListUpto(upTo);
				System.out.println("Your recent orders are ");
				for(int i=0;i<orders.size();i++){
					System.out.println("Order id "+orders.get(i).getId());
				}
				System.out.println("Enter any action or type exit to close application");
				getValueFromStandardInput("GENERIC");
			} catch (InvalidRecentOrdersCount e) {
				System.out.println(e.getMessage());
				System.out.println("Enter any action or type exit to close application");
				getValueFromStandardInput("GENERIC");
			}
	}
	private static ArrayList<Order> getOrdersListUpto(int upTo) throws InvalidRecentOrdersCount {
		ArrayList<Order> recent=new ArrayList<>();
		if(LoginDetails.getLoginDetails().getUserName().equals("Admin")){
			int toatlCOunt=DataObject.getDataObject().getOrders().size();
			if(upTo>toatlCOunt){
				throw new InvalidRecentOrdersCount("Your Total Orders are only "+ toatlCOunt);
			}
			for(int j=0;j<upTo;j++){
				recent.add(DataObject.getDataObject().getOrders().get(toatlCOunt-j-1));
			}
		}else{
			int toatlCOunt=getOrderCountByCustomerName(LoginDetails.getLoginDetails().getUserName());
			if(upTo>toatlCOunt){
				throw new InvalidRecentOrdersCount("Your Total Orders are only "+ toatlCOunt);
			}
			ArrayList<Order>custOrders=getOrdersByCustomerName(LoginDetails.getLoginDetails().getUserName());
			for(int j=0;j<upTo;j++){
				recent.add(custOrders.get(toatlCOunt-j-1));
			}
		}
		
		return recent;
	}
	private static ArrayList<Order> getOrdersByCustomerName(String userName) {
		ArrayList<Order> orders=new ArrayList<Order>();
		for(Order order:DataObject.getDataObject().getOrders()){
			if(order.getCustomerName().equals(userName)){
				orders.add(order);
			}
		}
		return orders;
	}

	private static void fetchTotalSale() {
		if(LoginDetails.getLoginDetails().getUserName().equals("Admin")){
			System.out.println("Total no of orders recieved : "+DataObject.getDataObject().getOrders().size());
		}else{
			System.out.println("Total no of orders placed by you : "+getOrderCountByCustomerName(LoginDetails.getLoginDetails().getUserName()));
		}
		System.out.println("Enter any action or type exit to close application");
		getValueFromStandardInput("GENERIC");
	}

	private static void displayHelp() {
		System.out.println("You can view all the actions that can be performed in this application simply by giving actions command");
		System.out.println("Type actions to proceed or exit to close application ");
		getValueFromStandardInput("DISPLAY_ACTIONS");
	}

	private static void startPostProcessing() {
		DataLoader.wirteData();
	}

	private static void logout() {
		LoginDetails loginDetails=LoginDetails.getLoginDetails();
		loginDetails.setPassword(null);
		loginDetails.setUserName(null);
		loginDetails.setPasswordSet(false);
	}	
	private static void login() {
		System.out.println(GlobalVariables.strings.get("username"));
		LoginDetails loginDetails=LoginDetails.getLoginDetails();
		loginDetails.setUserName(getValueFromStandardInput("USERNAME"));
		   if(loginDetails.getUserNameAndpassword().containsKey(loginDetails.getUserName())){
			   loginDetails.setPasswordSet(true);
				System.out.println(GlobalVariables.strings.get("password"));
			   loginDetails.setPassword(getValueFromStandardInput("PASSWORD"));
		   }
		   else{
				System.out.println(GlobalVariables.strings.get("set-password"));
				  loginDetails.setPassword(getValueFromStandardInput("PASSWORD"));
				  loginDetails.getUserNameAndpassword().put(loginDetails.getUserName(),loginDetails.getPassword());
				  System.out.println("Login Details are saved!");
		   }
		System.out.println("Login Successfull");
	
	}

	private static String getValueFromStandardInput(String type) {
		String name= sc.nextLine();
		if(name.equals("exit")){
			exitApplication();
		}
			try {
				if("USERNAME".equals(type)){
					ValidationHelper.validateUserName(name);
				}
				else if("PASSWORD".equals(type)){
					ValidationHelper.validatePassword(name);
				}else if("DISPLAY_ACTIONS".equals(type)){
					if(name.equals("actions")){
						displayOptions();
					}else{
						System.out.println("Type actions to proceed further or exit to close application ");
						name=	getValueFromStandardInput(type);
					}
				}
				else if("ACTIONS".equals(type)){
					   ValidationHelper.validateActions(name);
				}else if("GENERIC".equals(type)){
					   ValidationHelper.validateActions(name);
					   proceedWithAction(Integer.parseInt(name));
				}else if("ItemIds".equals(type)){
					ValidationHelper.validateItemIds(name);
				}else if("order".equals(type)){
					if(!name.equals("order")){
						System.out.println("Re enter or type exit to close application");
						name=getValueFromStandardInput(type);
					}
				}else if("upTo".equals(type)){
					Integer.parseInt(name);
				}
			} catch (InvalidInputLength | InvalidPassword | InvalidAction | InvalidItems | NumberFormatException e) {
				System.out.println(e.getMessage());
				if(e instanceof NumberFormatException){
					System.out.println("Re enter Valid numebr or type exit to close application");
				}else{
					System.out.println("Re enter or type exit to close application");
				}
				name=getValueFromStandardInput(type);
		}
		return name;
	}

	private static void displayOptions() {
			System.out.println("Below are the actions that can be performed by you in this application");
			if(LoginDetails.getLoginDetails().getUserName().equals("Admin")){
				System.out.println("Add Item(0): provide item id, name and price\nUpdate Item(1): provide  item id, name and price\nDelete Item(2): provide  item id\n");
			}else{
				System.out.println("View Items(3)\nSelect Items(4): enter space seprated item ids not more than 5\nPay bill(5)");
			}
			System.out.println("Fetch Total No Of Orders(6)\nFetch Recent Orders(7)\n");
			System.out.println("Enter Code from above to proceed with respective action or type exit to close application");
	}

	private static void startPreProcessing() {
		sc=new Scanner(System.in);
		DataLoader.loadConfigurationData();
		DataLoader.loadSeedData();
		System.out.println(GlobalVariables.strings.get("welcome-message"));
		login();
	}
	
	public static void exitApplication(){
		System.out.println("Bye bye! Hope you had  a great feast!");
		logout();
		startPostProcessing();
		System.exit(0);
	}
	
	public static boolean isItemPresent(ArrayList<Item> items,int id){
		boolean isPresent=false;
		for(Item item:items){
			if(item.getId()==id){
				isPresent= true;
				break;
			}
		}
		return isPresent;
	}
	private static int getBill(String itemIds) {
		String[] itemIdsSufix=itemIds.split(" ");
//		String summary="Total Bill Is : ";
		int val=0;
		ArrayList<Item> items=DataObject.getDataObject().getItem();
		for(int i=0;i<itemIdsSufix.length;i++){
			for(Item item:items){
				if((item.getId()+"").equals(itemIdsSufix[i])){
					val+=item.getPrice();
					break;
				}
			}
		}
		return val;
	}
	
}
