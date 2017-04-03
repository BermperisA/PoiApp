package main;
import GUI.dbgui;

import javax.xml.ws.Endpoint; 
import javax.jws.WebMethod;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.util.Properties;

import server.WebPOIServiceImpl;


public class MyMain {

	
	    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {  
    	
    	
		Properties prop = new Properties();
		FileOutputStream fis= null;
		 
    	try {
    	fis= new FileOutputStream("settings.properties");
        //load a properties file
    	prop.setProperty("username", "sdi0900248");
		prop.setProperty("password", "51923420");
		prop.setProperty("database", "sdi0900248");			
		prop.setProperty("ip", "195.134.65.254 ");
		prop.setProperty("port", "3306");
		prop.setProperty("R", "1");
		prop.setProperty("T", "120");
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
		
		
    	//Opening Connections
		
		new Connect();
		new dbgui();
	
                                
		
        Endpoint.publish("http://"+  "192.168.1.154"+ ":" +  9999 +"/WebPOIService/", new WebPOIServiceImpl()); 
        //http://0.0.0.0:9999/WebPOIService/WebPOIService?WSDL  
        
       
       
 
        
    }
}
