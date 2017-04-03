package com.firstapp.pois;

import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PoiCard extends Activity {
	private EditText poiName;
	private EditText poiType;
	private EditText poix;
	private EditText poiy;
	private String name;
	private String type;
	private String x;
	private String y;
	private Button btnDlt;
	private Poi PoiToBeDeleted;
	private DataSource datasource;
	private String str;
	private String str2;
	private String typeName;
	private String filter;
	private double latitude;
    private double longitude;
    private String poiToBeDeleted="";
	private List<Poi>pois;
	 private static String epistrofi="";
	private static String TAG = "MyFirstApp";	  	
	private static final String SOAP_ACTION = "http://server/deleteData";
	private static final String METHOD_NAME = "deleteData";
	private static final String NAMESPACE = "http://server/"; 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poi_card);
		Intent myIntent=getIntent();
		name=myIntent.getStringExtra("poiName");
		type=myIntent.getStringExtra("poiType");
		x=myIntent.getStringExtra("poiX");
		y=myIntent.getStringExtra("poiY");
		poiName=(EditText)findViewById(R.id.editTextName);
		poiName.setText(name);
		poiName.setFocusable(false);
		poiName.setClickable(false);
		poiType=(EditText)findViewById(R.id.editTextType);
		poiType.setText(type);
		poiType.setFocusable(false);
		poiType.setClickable(false);
		poix=(EditText)findViewById(R.id.editTextX);
		poix.setText(x);
		poix.setFocusable(false);
		poix.setClickable(false);
		poiy=(EditText)findViewById(R.id.editTextY);
		poiy.setText(y);
		poiy.setFocusable(false);
		poiy.setClickable(false);
		PoiToBeDeleted=new Poi(Double.parseDouble(x),Double.parseDouble(y),name,type);
		poiToBeDeleted=poiToBeDeleted.concat(x + "#" + y );
				
	
		btnDlt=(Button)findViewById(R.id.btnDelete);
		
		
		
	}
	public void onResume(){
		super.onResume();
		datasource = new DataSource(getApplicationContext());
        datasource.open();

		btnDlt.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				
					
		      

				//datasource.getMyWritableDatabase();
				datasource.deletePoi(new Poi(Double.parseDouble(x),Double.parseDouble(y),name,type));
				Toast.makeText(getApplicationContext(), "Poi was successfully deleted!", Toast.LENGTH_LONG).show();
			     
			        
				Thread ws = new Thread(){
                	
                    @Override
                    public void run(){   
                    	
                    	String u= LoginActivity.c_username;
                        String p = LoginActivity.c_password;
                        
                        str="";
                        str = str.concat(u+"#"+p);
                        String stat="";
                        stat = ActiveInternetConnection.getConnectivityStatusString(getApplicationContext());
                        Log.i(TAG, stat);
                        if (!(stat.equals("Not connected to Internet") ))
                        {
	
	                        SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
	                        PropertyInfo propertyInfo = new PropertyInfo();
	                        propertyInfo.setType(PropertyInfo.STRING_CLASS);
	                        propertyInfo.setName("arg0");
	                        propertyInfo.setValue(str);
	                        PropertyInfo propertyInfo1 = new PropertyInfo();
	                        propertyInfo1.setType(PropertyInfo.STRING_CLASS);
	                        propertyInfo1.setName("arg1");
	                        propertyInfo1.setValue(poiToBeDeleted);
	                        Request.addProperty(propertyInfo);
	                        Request.addProperty(propertyInfo1);
	                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);	                                
	                        envelope.setOutputSoapObject(Request);
	                        //envelope.dotNet = true;
	                        HttpTransportSE ht = new HttpTransportSE( LoginActivity.URL);

		                        try{
		                            ht.call(SOAP_ACTION, envelope);                           
		                            final SoapObject result = (SoapObject)envelope.bodyIn; 
		                                        
		                             
		                             if(result != null)
		                             {		
		                            	
			                                if (result.getProperty(0).toString().equals("Username not Found"))
			                    	        {	
			                                	runOnUiThread(new Runnable() {
		
			                                         @Override
			                                         public void run() {
			                                        	 Toast.makeText(getApplicationContext(), "Username not Found", Toast.LENGTH_SHORT).show();
		
			                                         }
			                                     });
			                                	 
			                    	        }
		 	                    		    else if  (result.getProperty(0).toString().equals("Wrong Password!"))
		 	                    		    {	 runOnUiThread(new Runnable() {
		
			                                         @Override
			                                         public void run() {
			                                        	 Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
			                                        	
			                                         }
			                                     });		                                	 
			                    	        }
		 	                    		    else
			                    		    {	  runOnUiThread(new Runnable() {
		 	                    		    	@Override
		                                         public void run() {
		                                        	 Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
		                                        	
		                                         }                               	 
			                    	        });		                                 
		                    	        }
		                             }
		                             
		                        }catch(Exception e){
		                    		e.getMessage();
		                    			Log.i(TAG, "sdsdsds");
			                    		runOnUiThread(new Runnable() {	
			                                @Override
			                                public void run() {
			                                		Toast.makeText(getApplicationContext(), "No connection with server atm" , Toast.LENGTH_LONG).show();								
			                                }
			                            }); 	                            	                    
		                        }	                  
                        
	                    }
                        else
	                    {
                        	runOnUiThread(new Runnable() {                        		
                                @Override
                                public void run() {
                                	   Toast.makeText(getApplicationContext(), "No internet connection!" , Toast.LENGTH_LONG).show();							
                                }                            
                            });  
	                    }	                    
                      }
	                };                    
		            ws.start();
	                
	                 
	            }
	        });	
		
	}

}
