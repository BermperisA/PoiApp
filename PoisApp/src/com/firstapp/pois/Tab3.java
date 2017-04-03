package com.firstapp.pois;

import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

 
public class Tab3 extends ListFragment {
	private static final String SAVE_SELECTED = "save selected";

	private List <Poi> pois;
	private int poisCounter;
	private DataSource datasource;
	private String[] TypeXY;
	
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
	   Log.i("3", "onCreateView");
        View rootView = inflater.inflate(R.layout.tab3, container, false);
        
        
         
        return rootView;
    }
   
	@Override
	
   
    public void onActivityCreated(Bundle savedInstanceState) {
    	
        super.onActivityCreated(savedInstanceState);
       
        //DatabaseHandler db=DatabaseHandler.getInstance(getActivity());
        
        Log.i("3", "onActivityCreated");
   	 	datasource = new DataSource(getActivity());
        datasource.open();
        poisCounter=datasource.getPoisCount();
        String[] names=new String[poisCounter];
        TypeXY=new String[poisCounter];
        pois=datasource.getAllPois();
       int counter=0;
       
        for (Poi cn : pois) { 	
        	names[counter]=cn.getPoiName();
        	TypeXY[counter]= cn.getType();
        	TypeXY[counter]=TypeXY[counter].concat("#" + cn.getX() + "#" + cn.getY() );
        	counter++;
        	
        }
        
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
            android.R.layout.simple_list_item_1,names);
        setListAdapter(adapter);
      
		
      }
	
    public void onResume(){
    	super.onResume();
    	
    	 Log.i("3", "onResume");
    	datasource = DataSource.getInstance(getActivity());
        datasource.open();
    	
         poisCounter=datasource.getPoisCount();
        String[] names=new String[poisCounter];
        TypeXY=new String[poisCounter];
        pois=datasource.getAllPois();
       
    	
        int counter=0;
        
         for (Poi cn : pois) { 	
         	names[counter]=cn.getPoiName();
         	TypeXY[counter]= cn.getType();
         	TypeXY[counter]=TypeXY[counter].concat("#" + cn.getX() + "#" + cn.getY() );
         	counter++;
         	
         }
         
         
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
             android.R.layout.simple_list_item_1,names);
         setListAdapter(adapter);
         
    	
    	}
    @Override
    public void onListItemClick(ListView l,View v,int position,long id){
    	Intent myIntent=new Intent(getActivity().getApplicationContext(),PoiCard.class);
    	myIntent.putExtra("poiName", getListView().getItemAtPosition(position).toString());
    	String parts[]=TypeXY[position].split("#");
    	myIntent.putExtra("poiType", parts[0]);
    	myIntent.putExtra("poiX",parts[1]);
    	myIntent.putExtra("poiY", parts[2]);
    	startActivity(myIntent);
    	
    	//Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    	
    	
    }
    
    
	}