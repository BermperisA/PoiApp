package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import server.WebPOIService;
import server.WebPOIServiceImplService;
import server.WebPOIServiceImplServiceLocator;

public class Service {
	
	private static WebPOIServiceImplServiceLocator service = null;
	private static WebPOIService server=null;
	
	public static String ip;
	public Service(){
		
		setService();			
	}
	
	
	
	private static void setService(){
		if (service == null){
			
			Properties prop = new Properties();
			InputStream fis= null;
			 
	    	try {
	    	fis= new FileInputStream("settings.properties");
	        //load a properties file
	    	prop.load(fis);
	    		
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	        } finally{
	        	try{
	        	fis.close();
	        	}catch (IOException ioex){
	        		ioex.printStackTrace();
	        	}
	        }
	    	String url = prop.getProperty("url");
	    	String servicename = prop.getProperty("servicename");
	    	ip = prop.getProperty("ip");
	    	
	    	
			try {
		    	service = new WebPOIServiceImplServiceLocator(url, new QName(
					    "http://server/", servicename));
				server = service.getWebPOIServiceImplPort();
				
			} catch (ServiceException e) {				
				e.printStackTrace();
			}
		}

		
	}
	
	
	public static WebPOIService getService(){
		if (server == null)
			setService();
		
		return server;
		
	}

}
