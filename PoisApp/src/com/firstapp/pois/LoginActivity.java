package com.firstapp.pois;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView.OnEditorActionListener;

 
public class LoginActivity extends Activity {
	private EditText password;
	private EditText username;
	private Button loginBtn;
	private Button dokimiBtn;
	private TextView registerScreen;
	
	private String str;
	private String str2;
	
	private static String TAG = "MyFirstApp";	  	
	private static final String SOAP_ACTION = "http://server/getMapData";
	private static final String METHOD_NAME = "getMapData";
	private static final String NAMESPACE = "http://server/"; 
	public static final String URL = "http://192.168.1.154:9999/WebPOIService/WebPOIService?WSDL";

	public static  String c_username;
	public static  String c_password;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login);
        
        username=(EditText) findViewById(R.id.username);
        username.setInputType(InputType.TYPE_CLASS_TEXT);
        
        password=(EditText) findViewById(R.id.log_password);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        
        loginBtn=(Button) findViewById(R.id.btnLogin);
        
        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.log_password);
        username.addTextChangedListener(new InputValidator(username));
        password.addTextChangedListener(new InputValidator(password));
        username.setOnEditorActionListener(new EmptyTextListener(username));
        password.setOnEditorActionListener(new EmptyTextListener(password));
        
        registerScreen = (TextView) findViewById(R.id.link_to_register);
        this.setTitle("Login");        
        
        
 
    }
    
    public void onResume(){     	
     	
     	super.onResume();

        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
               
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                 
            }
        });
        
        loginBtn.setOnClickListener(new Button.OnClickListener() {
    		@Override
            public void onClick(View v) { 
    			 
    			Thread ws = new Thread(){
                    	
                    @Override
                    public void run(){   
                    	
                    	EditText username = (EditText)findViewById(R.id.username);
                        String u= username.getText().toString();
                        EditText password = (EditText)findViewById(R.id.log_password);
                        String p = password.getText().toString();
                        
                        str="";
                        str2="";
                        str = str.concat(u+"#"+p);
                        str2= str2.concat("-10#-10");                        

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
	                        propertyInfo1.setValue(str2);
	                        Request.addProperty(propertyInfo);
	                        Request.addProperty(propertyInfo1);
	                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);	                                
	                        envelope.setOutputSoapObject(Request);
	                        //envelope.dotNet = true;
	                        HttpTransportSE ht = new HttpTransportSE( URL);

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
			                    		    {	 
		 	                    		    	c_username = u;
		 	                    		    	c_password = p;
		 	                    		    	
		 	                    			   Intent intent = new Intent(getApplicationContext(), Tabs.class);
		 	                    			   startActivity(intent);
			                                	 
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
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			 if (s.length() != 0) {
	                switch (et.getId()) {
	                case R.id.username: {
	                    if (!Pattern.matches("^[a-z]{1,16}$", s)) {
	                        et.setError("Username must have only a-z");
	                        
	                    }
	                }
	                    break;

	                case R.id.log_password: {
	                    //if (!Pattern.matches("[a-zA-Z][0-9]$", s)) {
	                      //  et.setError("Password must have only a-z and A-Z");
	                    //}
	                }
	                    break;
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
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			 if (actionId == EditorInfo.IME_ACTION_NEXT) {
	                // Called when user press Next button on the soft keyboard

	                if (et.getText().toString().equals(""))
	                    et.setError("Empty Field!");
	            }
			 if (actionId == EditorInfo.IME_ACTION_DONE) {
	                // Called when user press Next button on the soft keyboard

	                if (et.getText().toString().equals(""))
	                    et.setError("Empty Field!");
	            }
	            return false;
	        }
		}

}    
