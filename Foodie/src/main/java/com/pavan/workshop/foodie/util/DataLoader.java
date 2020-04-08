package com.pavan.workshop.foodie.util;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.pavan.workshop.foodie.bean.DataObject;
import com.pavan.workshop.foodie.bean.LoginDetails;
import com.pavan.workshop.foodie.global.GlobalConstants;
import com.pavan.workshop.foodie.global.GlobalVariables;
import com.pavan.workshop.foodie.helper.FileHelper;

public class DataLoader {
	public static void  loadConfigurationData(){
		GlobalVariables.strings=FileHelper.loadFileToProperties(GlobalConstants.applicationPath+File.separator+"Config/Strings.properties");
		GlobalVariables.specs=FileHelper.loadFileToProperties(GlobalConstants.applicationPath+File.separator+"Config/Specification.properties");
	}

	public static void loadSeedData() {
		System.out.println(GlobalConstants.applicationPath);
		LoginDetails.getLoginDetails().loginDetails=(LoginDetails)loadData(GlobalConstants.applicationPath+File.separator+"DataStore/LoginDetails.json",LoginDetails.class);
		DataObject.getDataObject().data=(DataObject)loadData(GlobalConstants.applicationPath+File.separator+"DataStore/DataObject.json",DataObject.class);

	}

	private static <T> Object loadData(String filePath, Class<T> classToLoad) {
		ObjectMapper om=new ObjectMapper();
		File file=new File(filePath);
		try {
			return om.readValue(file, classToLoad);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void wirteData() {
		writeDataTofile(GlobalConstants.applicationPath+File.separator+"DataStore/LoginDetails.json",LoginDetails.getLoginDetails());
		writeDataTofile(GlobalConstants.applicationPath+File.separator+"DataStore/DataObject.json",DataObject.getDataObject());
	}

	private static void writeDataTofile(String filePath,Object data) {
		ObjectMapper om=new ObjectMapper();
		File file=new File(filePath);
		try {
			om.writeValue(file, data);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


}
