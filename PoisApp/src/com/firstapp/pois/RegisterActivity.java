package com.firstapp.pois;

import java.util.regex.Pattern;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.inputmethod.EditorInfo;
 


public class RegisterActivity extends Activity {
	
	private EditText password;
	private EditText confirmPassword;
	private EditText username;
	private Button btnRegister;
	private String str="";
	
	private static String TAG = "MyFirstApp";	  	
	private static final String SOAP_ACTION = "http://server/registeruser";
	private static final String METHOD_NAME = "registeruser";
	private static final String NAMESPACE = "http://server/"; 
	 
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // Set View to register.xml
	        setContentView(R.layout.register);
	        username=(EditText) findViewById(R.id.reg_username);
	        username.setInputType(InputType.TYPE_CLASS_TEXT);
	        
	        password=(EditText) findViewById(R.id.reg_password);
	        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
	        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
	        
	        confirmPassword=(EditText) findViewById(R.id.reg_conf_password);
	        confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
	        confirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
	  
	        btnRegister=(Button) findViewById(R.id.btnRegister);
	     
	        
	        username.addTextChangedListener(new InputValidator(username));
	        password.addTextChangedListener(new InputValidator(password));
	        confirmPassword.addTextChangedListener(new InputValidator(confirmPassword));
	        username.setOnEditorActionListener(new EmptyTextListener(username));
	        password.setOnEditorActionListener(new EmptyTextListener(password));
	        confirmPassword.setOnEditorActionListener(new EmptyTextListener(confirmPassword));
	       
	       
	       
}
	 public void onResume(){     	
     	
     	super.onResume();
     	btnRegister.setOnClickListener(new Button.OnClickListener() {
    		@Override
            public void onClick(View v) {                   
                    Thread ws = new Thread(){
                    	
                    @Override
                    public void run(){   
                    	
                    	EditText username = (EditText)findViewById(R.id.reg_username);
                        String u= username.getText().toString();
                        EditText password = (EditText)findViewById(R.id.reg_password);
                        String p = password.getText().toString();
                        EditText confpassword = (EditText)findViewById(R.id.reg_conf_password);
                        String c = confpassword.getText().toString();

                        str="";
                        str = str.concat(u+"#"+p+"#"+c);
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
	                        Request.addProperty(propertyInfo);
	                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);	                                
	                        envelope.setOutputSoapObject(Request);
	                        HttpTransportSE ht = new HttpTransportSE( LoginActivity.URL);

		                        try{
		                            ht.call(SOAP_ACTION, envelope);                           
		                            final SoapObject result = (SoapObject)envelope.bodyIn;                                         
		                             
		                             if(result != null)
		                             {		
			                                if (result.getProperty(0).toString().equals("Successful Registration"))
			                    	        {	
			                                	 Intent intent = new Intent(getApplicationContext(), Tabs.class);
		 	                    		          startActivity(intent);
			                    	        }
		 	                    		    else if  (result.getProperty(0).toString().equals("Password mismatch!"))
		 	                    		    {	 runOnUiThread(new Runnable() {
		
			                                         @Override
			                                         public void run() {
			                                        	 Toast.makeText(getApplicationContext(), "Password mismatch!", Toast.LENGTH_SHORT).show();
			                                        	
			                                         }
			                                     });	 	                    		    	
			                                	 
			                    	        }
		 	                    		   else if  (result.getProperty(0).toString().equals("Username is already in use!"))
			                    		    {	 runOnUiThread(new Runnable() {
		
			                                         @Override
			                                         public void run() {
			                                        	 Toast.makeText(getApplicationContext(), "Username is already in use!", Toast.LENGTH_SHORT).show();
		
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
	 
	 private class InputValidator implements TextWatcher {
	        private EditText et;

	        private InputValidator(EditText editText) {
	            this.et = editText;
	        }

			@Override
			public void afterTextChanged(Editable arg0) {
			
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) 
			{
				if (s.length() != 0) {
					int sl=0; 
		                switch (et.getId()) {
		                case R.id.reg_username: {
		                    if (!Pattern.matches("^[a-z]{1,16}$", s)) {
		                        et.setError("Username must have only a-z");
		                        
		                    }
		                }
		                    break;

		                /*case R.id.reg_password: {
		                	sl = s.length();
		                	 
		                }
		                    break;	
		                    	
		                case R.id.reg_conf_password: {
		                   if (sl==s.length()){
		                	   
		                	   if (confirmPassword!=password){
		  						 et.setError("Password mismatch");
		  					 }		                	   
		                   }
		                   break;
		                 }*/
		                	 
		                }					 
		            }				
				}				
			}

	 
	 
	 private class EmptyTextListener implements OnEditorActionListener {
	        private EditText et;

	        public EmptyTextListener(EditText editText) {
	            this.et = editText;
	        }

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
	                // Called when user press Next button on the soft keyboard

	                if (et.getText().toString().equals(""))
	                    et.setError("Empty Field!");
	            }
				
			 if (actionId == EditorInfo.IME_ACTION_DONE) {
	                // Called when user press Next button on the soft keyboard

	                if (et.getText().toString().equals(""))
	                    et.setError("Emptyyy!");
	            }
				return false;
			}
	 }
}


