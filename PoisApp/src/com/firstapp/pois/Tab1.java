package com.firstapp.pois;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import android.app.Fragment;
import android.app.Service;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
 
public class Tab1 extends Fragment implements OnItemSelectedListener {
	
	private GPSTracker gps;
	
	@SuppressWarnings("unused")
	private Spinner spinner;
	private Button btnSet;
	private static String item;
	private String userFields;
	private String poiFields;
	public static double latitude;
    public static double longitude;
    private   View rootView;
    private String name;
    private Poi poiToBeInserted;	
	private static String TAG = "MyFirstApp";	  	
	private static final String SOAP_ACTION = "http://server/setMonitorData";
	private static final String METHOD_NAME = "setMonitorData";
	private static final String NAMESPACE = "http://server/"; 
	
	 
 
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		  
		  Log.i("1", "onCreateView");
		  
	        rootView = inflater.inflate(R.layout.tab1, container, false);
	        super.onCreate(savedInstanceState);
	       
	        spinner = (Spinner)rootView.findViewById(R.id.spinner);
	        // Spinner click listener
	        spinner.setOnItemSelectedListener(this);
	        btnSet=(Button) rootView.findViewById(R.id.btnSet);
	        
	        // Spinner Drop down elements
	        List<String> categories = new ArrayList<String>();
	        categories.add("Drink");
	        categories.add("Cinema");
	        categories.add("Site seeing");
	        categories.add("Library");
	        categories.add("University");
	        categories.add("Fast Food Restaurant");
	        categories.add("Take Away Restaurant");
	        categories.add("Typical Restaurant");

	        // Spinner Drop down elements
	        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
			
			// Drop down layout style - list view with radio button
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			// attaching data adapter to spinner
			spinner.setAdapter(dataAdapter);       
	         
	        return rootView;
	  }
	  
	  @Override
      public void onActivityCreated(Bundle savedInstanceState){
      	super.onActivityCreated(savedInstanceState);
      	
      	Log.i("1", "onActivityCreated");
      	
      	gps = new GPSTracker(getActivity().getApplicationContext());
      	 
        // check if GPS enabled     
        if(gps.canGetLocation()){
             
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
             
            // \n is for new line
            Context context = getActivity().getApplicationContext();
            Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
           	
        
        btnSet.setOnClickListener(new Button.OnClickListener() {
    		@Override
            public void onClick(View v) {
    			
    			Thread ws = new Thread(){
                	
                    @Override
                    public void run(){   
                    	
                    	
                        String u= LoginActivity.c_username;
                        String p = LoginActivity.c_password;
                        EditText username = (EditText) rootView.findViewById(R.id.name);
                        name = username.getText().toString();
                        DataSource datasource;

                        userFields="";
                        poiFields="";
                        userFields = userFields.concat(u+"#"+p);
                        poiFields= poiFields.concat(Double.toString(latitude)) +"#"+ (Double.toString(longitude)+"#"+ item +"#"+name );
                        poiToBeInserted=new Poi(latitude,longitude,name,item);
                        
                        String stat="";
                        stat = ActiveInternetConnection.getConnectivityStatusString((getActivity().getApplicationContext()));
                        Log.i(TAG, stat);
                        if (!(stat.equals("Not connected to Internet") ))
                        {
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
		                            
		                            	 Log.i(TAG, result.getProperty(0).toString()); 
		                            	 
		                            	 getActivity().runOnUiThread(new Runnable() {
		                            		 public void run()   { 
		                            			 
		                                    	 Toast.makeText((getActivity().getApplicationContext()), result.getProperty(0).toString(), Toast.LENGTH_LONG).show();

		                            			
		                            		 }	 
		                            	 });
		                            	 
                            			 datasource = DataSource.getInstance(getActivity());
                            		        datasource.open();   			 
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
		                    		e.getMessage();
		                    		Log.i(TAG, "sdsdsds");
		                    		getActivity().runOnUiThread(new Runnable() {	
			                                @Override
			                                public void run() {
			                                		Toast.makeText((getActivity().getApplicationContext()), "No connection with server atm" , Toast.LENGTH_LONG).show();								
			                                }
			                            });
		                    		

		             		        datasource = new DataSource(getActivity().getApplicationContext());
		             		        datasource.open();
		             	
		             		        
		             		       
		             		        boolean poiExists=datasource.getPoi(poiToBeInserted);
		             		        Log.i("eimai to poiExists",String.valueOf(poiExists));
		             		        if(poiExists==false)
		             		        	datasource.addPendingPoi(poiToBeInserted); 
		             		        else{
		             		        	 getActivity().runOnUiThread(new Runnable() {                        		
		                                     @Override
		                                     public void run() {
		                                    	 	Toast.makeText((getActivity().getApplicationContext()), "Poi has already been set locally!" , Toast.LENGTH_LONG).show();
		                                     }
		                                  });
		             		        	 }
		             		        Intent i= new Intent(getActivity(), setService.class);		                    		
		                         	getActivity().getApplicationContext().startService(i);
		                    		
		                       }
		                            	                    
		                        
                    }
                    else
                    {	              			
                    	 getActivity().runOnUiThread(new Runnable() {                        		
                            @Override
                            public void run() {
                            	   Toast.makeText((getActivity().getApplicationContext()), "No internet connection!" , Toast.LENGTH_LONG).show();							
                            }
                        });  

                    }
                    }                  	
                    	
                    };                    
                    
                    ws.start();	            	
            }
        });    	
      	
      }

	 
    @Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// On selecting a spinner item
		item = parent.getItemAtPosition(position).toString();
		
		// Showing selected spinner item
		

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}