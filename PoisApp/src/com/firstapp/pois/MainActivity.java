package com.firstapp.pois;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;

public class MainActivity extends Activity {
	private Button helloButton;
	private EditText editTextName;
	private TextView textViewHello;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
		helloButton=(Button)findViewById(R.id.button_hello);
		editTextName=(EditText) findViewById(R.id.edit_name);
		
		
		
	}
	public void sayHello(View view){
		
		textViewHello.setText("Hello " + editTextName.getText());
		helloButton.setText("Say hello again!");
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.toggle_view_menu, menu);
		menu.add("Email");
		return true;
	}

}
