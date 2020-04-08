package com.pavan.workshop.foodie.helper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.pavan.workshop.foodie.exceptions.UnHandledException;

public class FileHelper {
	public static Properties loadFileToProperties (String filePath){
		Properties props=null;
		try{
			FileReader reader=new FileReader(filePath);
			props=new Properties();
			props.load(reader);
		}catch(Exception e){

			throw new UnHandledException(e.getMessage());
		}
		return props;
		
	}
	public static String getApplicationPath(){
		try {
			return new File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
