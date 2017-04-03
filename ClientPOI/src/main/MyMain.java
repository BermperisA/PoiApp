package main;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import gui.Gui;



public class MyMain {

	 public static void main(String[] args) {
		 
		 	Properties prop = new Properties();
			FileOutputStream fis= null;
			 
	    	try {
	    	fis= new FileOutputStream("settings.properties");
	        //load a properties file
			prop.setProperty("T", "120");
			prop.setProperty("url", "http://192.168.10.119:9999/WebPOIService/WebPOIService?WSDL");
			prop.setProperty("ip", "192.168.10.119");
			prop.setProperty("servicename", "WebPOIServiceImplService");
	    	prop.store(fis, null);
	    		
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	        } finally{
	        	try{
	        	fis.close();
	        	}catch (IOException ioex){
	        		ioex.printStackTrace();
	        	}
	        }
		 
	       
		    new Service();
	       	new Gui();	        
	       
	        
	     }	 

}
