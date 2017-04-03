package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;




public class Connect {
	
	private static Connection con = null;
	
	
	public Connect(){
			
			setConnection();			
	}		
	
	
	public static Connection getConnection(){
		
		if (con== null){
			
			setConnection();
		}
		return con;
				
	}
	
	public static synchronized void setConnection() {
		
		if (con== null){  //singleton fasi
			
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
	    	
	    	String usrnm = prop.getProperty("username");
	    	String pass = prop.getProperty("password");
	    	String ip = prop.getProperty("ip");
	    	String database = prop.getProperty("database");
			
			String connectionUrl = "jdbc:mysql://" + ip + "/" + database +"?user=" + usrnm +"&password=" + pass;			
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            try {
	                con = DriverManager.getConnection(connectionUrl);
	                if (con != null) 
	             	   System.out.println("Connection Created Successfully !");
	            } catch (SQLException ex) {
	                System.out.println("Failed to create the database connection."); 
	            }
	        } catch (ClassNotFoundException ex) {
	            System.out.println("Driver not found."); 
	        }	       
	    }
		else 
			System.out.println("There is a connection already running!");
	}
	
	
	public static void closecon ( ) {
		 try{
	         if(con!=null) {
	            con.close();
	            con = null;
	            System.out.println("Connection Closed Successfully !");
	           
	         }
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
		
	}
}
