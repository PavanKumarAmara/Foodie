package com.pavan.workshop.foodie.helper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;

import com.pavan.workshop.foodie.bean.DataObject;
import com.pavan.workshop.foodie.bean.LoginDetails;
import com.pavan.workshop.foodie.business.FoodieBo;
import com.pavan.workshop.foodie.exceptions.InvalidAction;
import com.pavan.workshop.foodie.exceptions.InvalidInputLength;
import com.pavan.workshop.foodie.exceptions.InvalidItems;
import com.pavan.workshop.foodie.exceptions.InvalidPassword;
import com.pavan.workshop.foodie.global.GlobalConstants;
import com.pavan.workshop.foodie.global.GlobalVariables;

public class ValidationHelper {
	public static boolean validateKeyValue(HashMap<String,String> map,String key,String value){
		boolean result=Boolean.FALSE;
		if(map.containsKey(key)){
			if(map.get(key).equals(value)){
				result=Boolean.TRUE;
			}
		}
		return result;
	}
	public static void validateUserName(String name) throws InvalidInputLength{
		int userNameLength=Integer.parseInt(GlobalVariables.specs.getProperty("user-name-legngth"));
			validateLength(name,userNameLength);
	}
	private static void validateLength(String value,int length) throws InvalidInputLength {
			if(value.trim().length()>length || value.trim().length()<=0){
					throw new InvalidInputLength(GlobalVariables.strings.getProperty("invalid-length"));
			}
	}
	public static void validatePassword(String pwd) throws InvalidInputLength, InvalidPassword {
		int passwordLength=Integer.parseInt(GlobalVariables.specs.getProperty("password-length"));
		validateLength(pwd,passwordLength);
		if(LoginDetails.getLoginDetails().isPasswordSet()){
			validateAuthenticity(pwd);
		}
	}
	private static void validateAuthenticity(String pwd) throws InvalidPassword {
		if(!LoginDetails.getLoginDetails().getUserNameAndpassword().get(LoginDetails.getLoginDetails().getUserName()).equals(pwd)){
			throw new InvalidPassword(GlobalVariables.strings.getProperty("invalid-password"));
		}
	}
	public static void validateActions(String name) throws InvalidAction {
		int action=Integer.parseInt(name);
		if(action<0 || action>7){
			throw new InvalidAction(GlobalVariables.strings.getProperty("invalid-action"));
		}
	}
	public static void validateItemIds(String name) throws InvalidItems {
		boolean isValid=true;
		String items[]=name.split(" ");
		for(int i=0;i<items.length;i++){
			int id=Integer.parseInt(items[i]);
			if(!FoodieBo.isItemPresent(DataObject.getDataObject().getItem(), id)){
				isValid=false;
			}
			if(!isValid && items.length>5){
				throw new InvalidItems("one or more selected items are not in store");
			}
			
		}
	}
}
