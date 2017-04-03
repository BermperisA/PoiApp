package com.firstapp.pois;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

 
public class Tabs extends FragmentActivity {
	
    private ActionBar actionBar;
    // Tab titles
 
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);
		
		Resources res = getResources();
    	String text = String.format(res.getString(R.string.username_password),LoginActivity.c_username);
    	this.setTitle(text);
		
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.Tab Frag1Tab = actionBar.newTab().setText("Tab1");
		ActionBar.Tab Frag2Tab = actionBar.newTab().setText("Tab2");
		ActionBar.Tab Frag3Tab = actionBar.newTab().setText("Tab3");

		
		Fragment Fragment1 = new Tab1();
		Fragment Fragment2 = new Tab2();
		Fragment Fragment3 = new Tab3();

		
		Frag1Tab.setTabListener(new MyTabsListener(Fragment1));
		Frag2Tab.setTabListener(new MyTabsListener(Fragment2));
		Frag3Tab.setTabListener(new MyTabsListener(Fragment3));

		
		actionBar.addTab(Frag1Tab);
		actionBar.addTab(Frag2Tab);
		actionBar.addTab(Frag3Tab);

		
	}
	
	class MyTabsListener implements ActionBar.TabListener {
		public Fragment fragment;
		
		public MyTabsListener(Fragment fragment){
			this.fragment = fragment;
		}


		@Override
		public void onTabReselected(Tab arg0, android.app.FragmentTransaction ft) {			
			
		}

		@Override
		public void onTabSelected(Tab arg0, android.app.FragmentTransaction ft) {

		    ft.replace(R.id.container, fragment);			
			
		}

		@Override
		public void onTabUnselected(Tab arg0,android.app.FragmentTransaction arg1) {

		}
	}

}

