package com.firstapp.pois;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.firstapp.pois.setService.ptask;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
 
public class Tab2 extends Fragment {
	
	private CheckBox chkEntertainment, chkEducation, chkFood, chkDrink, chkCinema, chkSite, chkLibrary, chkUniversity, chkFast, chkTake, chkTypical;
	private Button btnDisplay, btnx, btnfilter, btnrefresh;
	private View layout;
	private GoogleMap map;
	boolean visibility_Flag = true;
	boolean check_Drink_flag=false, result;
	boolean check_Entertainment_flag=false;
	boolean check_cinema=false;
	boolean check_drink=false;
	boolean check_site_seeing=false;
	boolean check_university=false;
	boolean check_library=false;
	boolean check_typical_restaurant=false;
	boolean check_take_away_restaurant=false;
	boolean check_fast_food_restaurant=false;
	private String types="";
	private static String TAG = "MyFirstApp";	  	
	private static final String SOAP_ACTION = "http://server/getMapData";
	private static final String METHOD_NAME = "getMapData";
	private static final String NAMESPACE = "http://server/"; 
	private   View rootView;
	private String str;
	private String str2;
	private String typeName;
	private String filter;
	private double latitude;
    private double longitude;
    private static String epistrofi="";
    private DataSource datasource;
    private Timer timer;
	private List<Poi> pois;
	private List<Marker> markers;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

    	 Log.i("2", "onCreateView");
        View rootView = inflater.inflate(R.layout.tab2, container, false);
        chkEntertainment = (CheckBox)rootView.findViewById(R.id.checkEntertainment);
		chkDrink= (CheckBox)rootView.findViewById(R.id.checkDrink);
		chkSite=(CheckBox)rootView.findViewById(R.id.checkSite);
		chkCinema=(CheckBox)rootView.findViewById(R.id.checkCinema);
		chkEducation = (CheckBox)rootView.findViewById(R.id.checkEducation);
		chkLibrary = (CheckBox)rootView.findViewById(R.id.checkLibrary);
		chkUniversity = (CheckBox)rootView.findViewById(R.id.checkUniversity);
		chkFood = (CheckBox)rootView.findViewById(R.id.checkFood);
		chkFast= (CheckBox)rootView.findViewById(R.id.checkFast);
		chkTake = (CheckBox)rootView.findViewById(R.id.checkTake);
		chkTypical = (CheckBox)rootView.findViewById(R.id.checkTypical);
		btnDisplay = (Button)rootView.findViewById(R.id.btnDisplay);
		btnx = (Button)rootView.findViewById(R.id.buttonx);
		btnfilter = (Button)rootView.findViewById(R.id.btnfilter);
		btnrefresh = (Button)rootView.findViewById(R.id.btnrefresh);
		layout = (View)rootView.findViewById(R.id.layout);
		layout.setVisibility(View.INVISIBLE);
		markers=new ArrayList<Marker>();
        
        return rootView;
        
        }
    
    public void getData(){
    	
    	Thread ws = new Thread(){
          	
            @Override
            public void run(){   
            	
            	
                String u= LoginActivity.c_username;
                String p = LoginActivity.c_password;                

                str="";
                str2="";
                str = str.concat(u+"#"+p);
                
                str2= str2.concat(Double.toString(Tab1.latitude)) +"#"+ (Double.toString(Tab1.longitude) );
                
                String stat="";
                stat = ActiveInternetConnection.getConnectivityStatusString((getActivity().getApplicationContext()));
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
                    propertyInfo1.setValue(str2);
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
	                                	 getActivity().runOnUiThread(new Runnable() {

	                                         @Override
	                                         public void run() {
	                                        	 Toast.makeText((getActivity().getApplicationContext()), "Username not Found", Toast.LENGTH_SHORT).show();

	                                         }
	                                     });
	                                	 
	                    	        }
	                    		    else if  (result.getProperty(0).toString().equals("Wrong Password!"))
	                    		    {	  getActivity().runOnUiThread(new Runnable() {

	                                         @Override
	                                         public void run() {
	                                        	 Toast.makeText((getActivity().getApplicationContext()), "Wrong Password!", Toast.LENGTH_SHORT).show();
	                                        	
	                                         }
	                                     });
	                    		    	
	                                	 
	                    	        }
	                                
	                    		    else if (result.getProperty(0).toString().equals("No POIS found in this region!")){
	                    		    	getActivity().runOnUiThread(new Runnable() {

	                                         @Override
	                                         public void run() {
	                                        	 Toast.makeText((getActivity().getApplicationContext()), "No POIS found in this region!", Toast.LENGTH_SHORT).show();
	                                        	
	                                         }
	                                     });
	                    		    	epistrofi="";
	                    		    	
	                    		    }
	                    		    else
	                    		    {	 
	                    		    	epistrofi=result.getProperty(0).toString();
	                    		    	Log.i(TAG, epistrofi);
	                                	 
	                    	        }
                                
                   	        }
                                                          
         
                       }catch(Exception e){
                    		e.getMessage();
                    			Log.i(TAG, "sdsdsds" );
                    		getActivity().runOnUiThread(new Runnable() {	
	                                @Override
	                                public void run() {
	                                		Toast.makeText((getActivity().getApplicationContext()), "No connection with server atm" , Toast.LENGTH_LONG).show();								
	                                }
	                            });                            	                    
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
    
     
        
        
		@Override
        public void onActivityCreated(Bundle savedInstanceState){
			
			 Log.i("2", "onActivityCreated");
			 
        	super.onActivityCreated(savedInstanceState);
        	addListenerOnButton();
        	
        	 map    = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        	 
         	
        	 updateMap();
        	 map.setMyLocationEnabled(true);
         	Location location= map.getMyLocation();
         	if (location!=null){                			
         		LatLng myLocation=new LatLng(location.getLatitude(),location.getLongitude());
         		CameraPosition cameraPosition=new CameraPosition.Builder().target(myLocation).zoom(12).build();
         		map.getUiSettings().setCompassEnabled(true);
         		map.getUiSettings().setRotateGesturesEnabled(true);
         		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
         		//map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,Constants.));
         		
         	}
         	timer = new Timer();
	        timer.schedule(new refreshMap(), 0, 10000);
         	

        }
		
		
		class refreshMap extends TimerTask {
		    public void run() {
		    	
		    	
		    	getActivity().runOnUiThread(new Runnable() {
           		 public void run()   { 
           			 
           			updateMap();
           			
           		 }	 
           	 });
		    	
		    	
		    }
		}
		
		public void updateMap(){
			
			removeMarkers();
			getData();
			datasource = new DataSource(getActivity());
            datasource.open();
            pois=datasource.getAllPois();
          	
           		
	             for (Poi cn : pois) {
	            		latitude=cn.getX();
	            		longitude=cn.getY();
	            		Marker marker= map.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(cn.getPoiName()).snippet(cn.getType()));
	            		marker.showInfoWindow();
	            		marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
	            		markers.add(marker);            			
	            			
	            		}
	             pois=datasource.getAllPendingPois();
		          	
	             for (Poi cn : pois) {
	            		latitude=cn.getX();
	            		longitude=cn.getY();
	            		Marker marker= map.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(cn.getPoiName()).snippet(cn.getType()));
	            		marker.showInfoWindow();
	            		marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
	            		markers.add(marker);            			
	            			
	            		}
	             
	       if(!epistrofi.equals("")){
	             String[] parts = epistrofi.split("\\$");
			     String[][] data = new String[parts.length][];
			     int r = 0;
			     for (String row : parts) {
			           data[r] = row.split("#");
			           r++;
			         }	
			        
			     for(int k=0; k<= r-1; k++){		        			 
			     	 latitude=Double.parseDouble(data[k][2]);		        		 
			       	 longitude=Double.parseDouble(data[k][3]);
			         Marker marker= map.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(data[k][0]).snippet(data[k][1]));
			         marker.showInfoWindow();
			         markers.add(marker);
			     }
			       	 
          	}
	       markers.size();
           	
		}
		
		
		@Override
		public void onDestroyView() {
		    super.onDestroyView();
		    MapFragment f = (MapFragment) getFragmentManager()
		                                         .findFragmentById(R.id.map);
		    if (f != null) 
		        getFragmentManager().beginTransaction().remove(f).commit();
		}
		
        public void onPause(){        	
        	super.onPause();	               	
       	          
        		timer.cancel();

              }           	
            	
            
        
        
        private void removeMarkers() {
            if (!(markers.isEmpty())){
	        	for (Marker marker: markers) {
	                marker.remove();
	            }
	            markers.clear();
            }
        }
        

        
        private void addListenerOnButton() {
        	
      	  btnfilter.setOnClickListener(new OnClickListener() {
		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if(visibility_Flag==true){
                    layout.setVisibility(View.VISIBLE);
                   visibility_Flag = false; 			  
  		  }
  		  }});
  	 
  		btnDisplay.setOnClickListener(new OnClickListener() {
  	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if(visibility_Flag==false){
                    layout.setVisibility(View.INVISIBLE);
                   visibility_Flag = true;
              }
  			 
  			  removeMarkers();
  			  getData();
  			Log.i("TYPES", types); 
  			String [] typeParts= types.split("#");
               	if(!epistrofi.equals("")){                 
		                int x1, y1;
		                String nm, tp;
		                String[] parts = epistrofi.split("\\$");
		  		        String[][] data = new String[parts.length][];
		  		         int r = 0;
		  		         for (String row : parts) {
		  		             data[r] = row.split("#");
		  		             r++;
		  		         }		  		       

				         for (int i=0; i<=(typeParts.length -1); i++){
				        	 for(int k=0; k<= r-1; k++){
				        		 if (data[k][1].equals((typeParts[i]))){
				        			 latitude=Double.parseDouble(data[k][2]);		        		 
			  		               	 longitude=Double.parseDouble(data[k][3]);
			  		        		 Marker marker= map.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(data[k][0]).snippet(data[k][1]));
			  		               	marker.showInfoWindow(); 
			  		               	markers.add(marker);
				        			 
				        								        			 
				        		 }  					        		 
				        	 }					        	 
				         }
               	       } 
				         datasource = new DataSource(getActivity());
				         datasource.open();
				         pois=datasource.getAllPois();
				         for (int i=0; i<=(typeParts.length -1); i++){
					             for (Poi cn : pois) {
					            	 if (cn.getType().equals((typeParts[i]))){
					             
					            		latitude=cn.getX();
					            		longitude=cn.getY();
					            		Marker marker= map.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(cn.getPoiName()).snippet(cn.getType()));
					            		marker.showInfoWindow();
					            		marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
					            		markers.add(marker);            			
					            			
					            		}
					             }
				         }
					             
					     pois=datasource.getAllPendingPois();
					     for (int i=0; i<=(typeParts.length -1); i++){
				             for (Poi cn : pois) {
				            	 if (cn.getType().equals((typeParts[i]))){
					            		latitude=cn.getX();
					            		longitude=cn.getY();
					            		Marker marker= map.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(cn.getPoiName()).snippet(cn.getType()));
					            		marker.showInfoWindow();
					            		marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
					            		markers.add(marker);            			
					            			
					            		}
				             }
					     }	
					     markers.size();
               	  
  		  }  		  
  		  
  		});
  		
  		  btnx.setOnClickListener(new OnClickListener() {
  		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if(visibility_Flag==false){
                    layout.setVisibility(View.INVISIBLE);
                   visibility_Flag = true;
               

  			  
  		  }
  		  }});
  		  
  		btnrefresh.setOnClickListener(new OnClickListener() {
		    	 
	          //Run when button is clicked
		  @Override
		  public void onClick(View v) {
			  if(visibility_Flag==false){
                  layout.setVisibility(View.INVISIBLE);
                 visibility_Flag = true;  
		  }
			  updateMap();
		  }});
		  
  		 
  		  
  		  chkEntertainment.setOnClickListener(new OnClickListener() {
 		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if (chkEntertainment.isChecked()){
  				  check_Entertainment_flag=true;
  				  if(check_cinema==false){
  					  chkCinema.setChecked(true);
  					  check_cinema=true;	  
  				  }
  				  
  				  if(check_drink==false){
  					  chkDrink.setChecked(true);
  					  check_drink=true;
  				  }
  				  if(check_site_seeing==false){
  				  chkSite.setChecked(true);
  				  check_site_seeing=true;
  				  }
  				 types=types.concat("drink#cinema#site seeing#");
  				
  				  	  
  			  }
  			  else{
  				  chkDrink.setChecked(false);
  				  chkSite.setChecked(false);
  				  chkCinema.setChecked(false);
  				  check_drink=false;
  				  check_site_seeing=false;
  				  check_cinema=false;
  				  types=types.replace("drink#cinema#site seeing#","");
  			  }
        
  			  
  			  
  		  }
  		  });
  		  chkCinema.setOnClickListener(new OnClickListener() {
		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if (chkCinema.isChecked()){
  				  check_cinema=true;
  				  if(check_drink==true && check_site_seeing==true){
  					  chkEntertainment.setChecked(true);
  				  }
  				  types=types.concat("cinema#");
  					  
  				  
  				  	  
  			  }
  			  else{
  				  types=types.replace("cinema#","");
  				  check_cinema=false;
  				  
  				if(check_drink==true && check_site_seeing==true){
  					chkEntertainment.setChecked(false);
  					chkSite.setChecked(true);
  					check_site_seeing=true;
  					chkDrink.setChecked(true);
  					check_drink=true;
  					
  					
  				}
  					
  			  }
        
  			  
  		  }
  		  });
  		  chkSite.setOnClickListener(new OnClickListener() {
		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if (chkSite.isChecked()){
  				  check_site_seeing=true;
  				  if(check_drink==true && check_cinema==true){
  					  chkEntertainment.setChecked(true);
  				  }
  				  types=types.concat("site seeing#");
  					  
  				  
  				  	  
  			  }
  			  else{
  				  types=types.replace("site seeing#","");
  				  check_site_seeing=false;
  				  
  				if(check_drink==true && check_cinema==true){
  					chkEntertainment.setChecked(false);
  					chkCinema.setChecked(true);
  					check_cinema=true;
  					chkDrink.setChecked(true);
  					check_drink=true;
  					
  					
  				}
  					
  			  }
        
  			  
  		  }
  		  });
  		  chkDrink.setOnClickListener(new OnClickListener() {
		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if (chkDrink.isChecked()){
  				  check_drink=true;
  				  if(check_cinema==true && check_site_seeing==true){
  					  chkEntertainment.setChecked(true);
  				 }
  				  types=types.concat("drink#");
  					  
  				 
  				  	  
  			  }
  			  else{
  				  types=types.replace("drink#","");
  				  check_drink=false;
  				  
  				if(check_cinema==true && check_site_seeing==true){
  					chkEntertainment.setChecked(false);
  					chkSite.setChecked(true);
  					check_site_seeing=true;
  					chkCinema.setChecked(true);
  					check_cinema=true;
  					
  					
  				}
  					
  			  }
        
  			  
  		  }
  		  });
  		  
  		  
  		  
  		  chkEducation.setOnClickListener(new OnClickListener() {
  		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if (chkEducation.isChecked()){
  				  if (check_library==false){
  					  chkLibrary.setChecked(true);
  					  check_library=true;
  				  }
  				  if(check_university==false){
  				  chkUniversity.setChecked(true);
  				  check_university=true;
  				  }
  				  	  
  			  }
  			  else{
  				  
  				  chkLibrary.setChecked(false);
  				  chkUniversity.setChecked(false);
  				  check_university=false;
  				  check_library=false;
  			  }
        
  			  
  		  }
  		  });
  		  chkUniversity.setOnClickListener(new OnClickListener() {
		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if (chkUniversity.isChecked()){
  				  check_university=true;
  				  if(check_library==true){
  					  chkEducation.setChecked(true);
  				  }
  				  types=types.concat("university#");
  					  
  				  
  				  	  
  			  }
  			  else{
  				  types=types.replace("university#","");
  				  check_university=false;
  				  
  				if(check_library==true){
  					chkEducation.setChecked(false);
  					chkLibrary.setChecked(true);
  					check_library=true;
  					
  					
  					
  				}
  					
  			  }
        
  			  
  		  }
  		  });
  		  chkLibrary.setOnClickListener(new OnClickListener() {
 		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if (chkLibrary.isChecked()){
  				  check_library=true;
  				  if(check_university==true){
  					  chkEducation.setChecked(true);
  				  }
  				  types=types.concat("library#");
  					  
  				  
  				  	  
  			  }
  			  else{
  				  types=types.replace("library#","");
  				  check_library=false;
  				  
  				if(check_university==true){
  					chkEducation.setChecked(false);
  					chkUniversity.setChecked(true);
  					check_university=true;
  					
  					
  					
  				}
  					
  			  }
        
  			  
  		  }
  		  });
  		  
  		  chkFood.setOnClickListener(new OnClickListener() {
  		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if (chkFood.isChecked()){
  				  if(check_fast_food_restaurant==false){
  					  chkFast.setChecked(true);
  					  check_fast_food_restaurant=true;
  				  }
  				  if(check_take_away_restaurant==false){
  					  chkTake.setChecked(true);
  					  check_take_away_restaurant=true;
  				  }
  				  if(check_typical_restaurant==false){
  					  chkTypical.setChecked(true);
  					  check_typical_restaurant=true;
  				  }
  				  types=types.concat("fast food restaurant#take away restaurant#typical restaurant#");
  				  	  
  			  }
  			  
  			  else{
  				  chkFast.setChecked(false);
  				  chkTake.setChecked(false);
  				  chkTypical.setChecked(false);
  				  check_fast_food_restaurant=false;
  				  check_typical_restaurant=false;
  				  check_take_away_restaurant=false;
  				  types=types.replace("fast food restaurant#take away restaurant#typical restaurant#", "");	  
  				  
  			  }
        
  			  
  		  }
  		  });
  		  chkFast.setOnClickListener(new OnClickListener() {
		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if (chkFast.isChecked()){
  				  check_fast_food_restaurant=true;
  				  if(check_take_away_restaurant==true && check_typical_restaurant==true){
  					  chkFood.setChecked(true);
  				  }
  				  types=types.concat("fast food restaurant#");
  					  
  				  
  				  	  
  			  }
  			  else{
  				  types=types.replace("fast food restaurant#","");
  				  check_fast_food_restaurant=false;
  				  
  				if(check_take_away_restaurant==true && check_typical_restaurant==true){
  					chkFood.setChecked(false);
  					chkTypical.setChecked(true);
  					check_typical_restaurant=true;
  					chkTake.setChecked(true);
  					check_take_away_restaurant=true;
  					
  					
  				}
  					
  			  }
        
  			  
  		  }
  		  });
  		  chkTake.setOnClickListener(new OnClickListener() {
 		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if (chkTake.isChecked()){
  				  check_take_away_restaurant=true;
  				  if(check_fast_food_restaurant==true && check_typical_restaurant==true){
  					  chkFood.setChecked(true);
  				  }
  				  types=types.concat("take away restaurant#");
  					  
  				  
  				  	  
  			  }
  			  else{
  				  types=types.replace("take away restaurant#","");
  				  check_take_away_restaurant=false;
  				  
  				if(check_fast_food_restaurant==true && check_typical_restaurant==true){
  					chkFood.setChecked(false);
  					chkTypical.setChecked(true);
  					check_typical_restaurant=true;
  					chkFast.setChecked(true);
  					check_fast_food_restaurant=true;
  					
  					
  				}
  					
  			  }
        
  			  
  		  }
  		  });
  		  chkTypical.setOnClickListener(new OnClickListener() {
 		    	 
  	          //Run when button is clicked
  		  @Override
  		  public void onClick(View v) {
  			  if (chkTypical.isChecked()){
  				  check_typical_restaurant=true;
  				  if(check_take_away_restaurant==true && check_fast_food_restaurant==true){
  					  chkFood.setChecked(true);
  				  }
  				  types=types.concat("typical restaurant#");
  					  
  				  
  				  	  
  			  }
  			  else{
  				  types=types.replace("typical restaurant#","");
  				  check_typical_restaurant=false;
  				  
  				if(check_take_away_restaurant==true && check_fast_food_restaurant==true){
  					chkFood.setChecked(false);
  					chkFast.setChecked(true);
  					check_fast_food_restaurant=true;
  					chkTake.setChecked(true);
  					check_take_away_restaurant=true;
  					
  					
  				}
  					
  			  }
        
  			  
  		  }
  		  });		 
  		
  	
	}
        
        	
     
 }
	
