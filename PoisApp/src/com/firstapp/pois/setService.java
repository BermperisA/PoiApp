package com.firstapp.pois;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;





import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class setService extends Service {
	

		 private Timer timer=null;
		 private Poi mypoi;
		 private List<Poi> mypendingpoi;
		 private String service_message="";
	   	 private String[] tokens=null;
	  	 private Boolean isInternetPresent=null;
	  	 private  boolean wrongpoiflag=false;
	  	 private DataSource datasource;
	  	private static final String SOAP_ACTION = "http://server/setMonitorData";
		private static final String METHOD_NAME = "setMonitorData";
		private static final String NAMESPACE = "http://server/"; 
		
		  @Override
		  public int onStartCommand(Intent intent, int flags, int startId) {
		    //TODO do something useful
			  
		    return Service.START_STICKY;
		    
		  }

		  @Override
		  public IBinder onBind(Intent intent) {
		  //TODO for communication return IBinder implementation
		    return null;
		  }
		  
		  @Override
		    public void onCreate() {
			  String x,y,type,poiName;
		        // code to execute when the service is first created
		        super.onCreate();
		        
		       
		        
		        Log.i("MyService", "Service Started.");
		        mypoi=new Poi();		        
		        timer = new Timer();
		        timer.schedule(new ptask(), 0, 2000);
		        
		    }	
			


class ptask extends TimerTask {
    public void run() {
    	DataSource datasource;
    	Poi poiToBeInserted;
		String userFields = "";
		String poiFields = "";
		userFields = userFields.concat(LoginActivity.c_username+"#"+LoginActivity.c_password);
		Log.i("Task", "M E S A.");
		// build notification
		// the addAction re-use the same intent to keep the example short
		datasource = new DataSource(getApplicationContext());
        datasource.open();
        List <Poi> pendingPois= datasource.getAllPendingPois();
        for (Poi cn : pendingPois) {
            String log = "X: "+cn.getX()+" ,PoiName: " + cn.getPoiName() + " ,Y: " + cn.getY();
            // Writing Pois to log
            Log.d("Name: ", log);
            poiFields= poiFields.concat(cn.getX() +"#"+ cn.getY()+"#"+ cn.getType() +"#"+cn.getPoiName() );
            poiToBeInserted=new Poi(cn.getX(),cn.getY(),cn.getPoiName(),cn.getType());
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setType(PropertyInfo.STRING_CLASS);
            propertyInfo.setName("arg0");
            propertyInfo.setValue(userFields);
            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setType(PropertyInfo.STRING_CLASS);
            propertyInfo1.setName("arg1");
            propertyInfo1.setValue(poiFields);
            Request.addProperty(propertyInfo);
            Request.addProperty(propertyInfo1);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);	                                
            envelope.setOutputSoapObject(Request);
            HttpTransportSE ht = new HttpTransportSE( LoginActivity.URL);

             try{
            	 ht.call(SOAP_ACTION, envelope);                           
                 final SoapObject result = (SoapObject)envelope.bodyIn; 	                                        
                         
                 if(result != null)
                 	{
                				 
               		 if(result.getProperty(0).toString().equals("POI can't be inserted because it already exists in range R")|| result.getProperty(0).toString().equals("The POI has already been set !")
                     ||result.getProperty(0).toString().equals("swsta")){
               			 		boolean exists= datasource.getPoi(poiToBeInserted);
               			 		if (exists==false)
               			 			{
               			 			datasource.addPoi(poiToBeInserted);
               			 			boolean pendingPoiExists=datasource.getPendingPoi(poiToBeInserted);
               			 			if(pendingPoiExists==true){
               			 				datasource.deletePendingPoi(poiToBeInserted);
                       			
               			 				}
               			 			}
                       		 
                       		 
                       	 }
                       }                                                          
     
                   }catch(Exception e){
                		break;
                		
                   }
                        	                    
                    
        }
       
        int counter = datasource.getPendingPoisCount();
        if (counter==0)
        {
	        Intent intent = new Intent(setService.this, setService.class);
			PendingIntent pIntent = PendingIntent.getActivity(setService.this, 0, intent, 0);
			String longText = "All pending POIs were successfully added to POIserver and local database!";
			Notification n  = new Notification.Builder(setService.this)
			   	        .setContentTitle("POI service Notification!")
			   	        .setContentText(longText)
			   	        .setStyle(new Notification.BigTextStyle().bigText(longText))
			   	        .setSmallIcon(R.drawable.ic_launcher)
			   	        .setContentIntent(pIntent)
			   	        .setAutoCancel(true)
			   	        .build();
					   	    	    
			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notificationManager.notify(0, n);
			timer.cancel();
        }
    	
    
 }
}
}