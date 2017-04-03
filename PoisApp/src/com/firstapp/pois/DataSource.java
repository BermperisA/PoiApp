package com.firstapp.pois;
import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;




public class DataSource {
	private SQLiteDatabase database;
	private DatabaseHandler db;
	private static DataSource dbInstance=null;
	private static final double R=1;
	

	private static final String[]allColumns= {DatabaseHandler.KEY_X,DatabaseHandler.KEY_Y,DatabaseHandler.KEY_POINAME,DatabaseHandler.KEY_TYPE};
	 
	
	
	public DataSource(Context context){
		db=DatabaseHandler.getInstance(context);	
		
	}
	public static DataSource getInstance(Context context){
		
		if(dbInstance==null)
			dbInstance=new DataSource(context.getApplicationContext());	
			
		return dbInstance;	
		
		
		
	}
	public void open() throws SQLException {
		
		database=db.getMyWritableDatabase();
		
		
	}
	public void close(){
		db.close();
		
		
	}
	 public void addPoi(Poi poi) {
	        
	        Log.d("addPoi",poi.toString());
	        ContentValues values = new ContentValues();
	        values.put(DatabaseHandler.KEY_X, Double.toString(poi.getX())); 
	        values.put(DatabaseHandler.KEY_Y, Double.toString(poi.getY())); 
	        values.put(DatabaseHandler.KEY_POINAME, poi.getPoiName()); 
	        values.put(DatabaseHandler.KEY_TYPE, poi.getType()); 
	       
	        
	 
	        // Inserting Row
	        database.insert(DatabaseHandler.TABLE_POIS, null, values);
	       // db.close(); // Closing database connection
	    }
	 public void addPendingPoi(Poi poi) {
	        
	        Log.d("addPendingPoi",poi.toString());
	        ContentValues values = new ContentValues();
	        values.put(DatabaseHandler.KEY_X, Double.toString(poi.getX())); 
	        values.put(DatabaseHandler.KEY_Y, Double.toString(poi.getY())); 
	        values.put(DatabaseHandler.KEY_POINAME, poi.getPoiName()); 
	        values.put(DatabaseHandler.KEY_TYPE, poi.getType()); 
	       
	        
	 
	        // Inserting Row
	        database.insert(DatabaseHandler.TABLE_PENDING_POIS, null, values);
	       // db.close(); // Closing database connection
	    }
	 public void deletePoi(Poi poi) {
	        //SQLiteDatabase db = this.getMyWritableDatabase();
	        database.delete(DatabaseHandler.TABLE_POIS, DatabaseHandler.KEY_X + " = ? AND " + DatabaseHandler.KEY_Y + "=?",
	                new String[] { String.valueOf(poi.getX()),String.valueOf(poi.getY()) });
	        Log.d("deletePoi()", poi.getPoiName());
	       // db.close();
	    }
	 public void deletePendingPoi(Poi poi) {
	        //SQLiteDatabase db = this.getMyWritableDatabase();
	        database.delete(DatabaseHandler.TABLE_PENDING_POIS, DatabaseHandler.KEY_X + " = ? AND " + DatabaseHandler.KEY_Y + "=?",
	                new String[] { String.valueOf(poi.getX()),String.valueOf(poi.getY()) });
	        Log.d("deletePoi()", poi.getPoiName());
	       // db.close();
	    }
	 public List<Poi> getAllPois() {
	        List<Poi> poiList = new ArrayList<Poi>();
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_POIS;
	 
	        //SQLiteDatabase db = this.getMyWritableDatabase();
	        Cursor cursor = database.rawQuery(selectQuery, null);
	 
	        // looping through all rows and adding to list
	        Poi poi=null;
	        if (cursor.moveToFirst()) {
	            do {
	                poi = new Poi();
	               
	                poi.setX(cursor.getDouble(0));
	                poi.setY((cursor.getDouble(1)));
	                poi.setPoiName(cursor.getString(2));
	                poi.setType(cursor.getString(3));
	                // Adding contact to list
	                poiList.add(poi);
	            } while (cursor.moveToNext());
	        }
	        Log.d("getAllPois()", poiList.toString());
	 
	        return poiList;
	    }
	 public List<Poi> getAllPendingPois() {
	        List<Poi> poiList = new ArrayList<Poi>();
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_PENDING_POIS;
	 
	        //SQLiteDatabase db = this.getMyWritableDatabase();
	        Cursor cursor = database.rawQuery(selectQuery, null);
	 
	        // looping through all rows and adding to list
	        Poi poi=null;
	        if (cursor.moveToFirst()) {
	            do {
	                poi = new Poi();
	               
	                poi.setX(cursor.getDouble(0));
	                poi.setY((cursor.getDouble(1)));
	                poi.setPoiName(cursor.getString(2));
	                poi.setType(cursor.getString(3));
	                // Adding poi to list
	                poiList.add(poi);
	            } while (cursor.moveToNext());
	        }
	        Log.d("getAllPois()", poiList.toString());
	 
	        return poiList;
	    }
	 public int getPoisCount() {
	    	int count=0;
	        String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_POIS;
	        //SQLiteDatabase db = this.getMyReadableDatabase();
	        Cursor cursor = database.rawQuery(countQuery, null);
	        if(cursor!=null && !cursor.isClosed()){
	        	count=cursor.getCount();
	        	cursor.close();
	        	}
	        
	 
	        // return count
	        return count;
	    }
	 public int getPendingPoisCount() {
	    	int count=0;
	        String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_PENDING_POIS;
	        //SQLiteDatabase db = this.getMyReadableDatabase();
	        Cursor cursor = database.rawQuery(countQuery, null);
	        if(cursor!=null && !cursor.isClosed()){
	        	count=cursor.getCount();
	        	cursor.close();
	        	}
	        
	 
	        // return count
	        return count;
	    }
	 public boolean getPoi(Poi poiSearch) {
	 
		 Double x1=poiSearch.getX();
		 Double y1=poiSearch.getY();
		 int counter=0;
	        //SQLiteDatabase db = this.getMyWritableDatabase();
	       // Cursor cursor = database.rawQuery(selectQuery, null);
	        Cursor cursor=database.query(DatabaseHandler.TABLE_POIS, DatabaseHandler.COLUMNS, DatabaseHandler.KEY_TYPE +" LIKE ? AND " +DatabaseHandler.KEY_POINAME + " LIKE ? ", new String[]{"%" + poiSearch.getType()+"%", "%"+poiSearch.getPoiName()+"%"},null,null,null);
	        Log.i("eimai h getPoi kai kalesthka", Double.toString(x1));
	       // Cursor cursor = database.query(DatabaseHandler.TABLE_POIS,DatabaseHandler.COLUMNS,DatabaseHandler.KEY_X + " =? AND " + DatabaseHandler.KEY_Y + "=?", new String[] {String.valueOf(poiSearch.getX()),String.valueOf(poiSearch.getY()) }, null, null,null,null);
	        if (cursor.moveToFirst()) {
	        	
	            do {
	            	Log.i("eimai to x kathe fora",Double.toString(cursor.getDouble(0)));
	            	
	            	if((Math.pow((cursor.getDouble(0)-(x1)),2)) + (Math.pow((cursor.getDouble(1)-(y1)), 2)) <= (Math.pow(R, 2)))
	            		counter++;
	           			
	            		
	            
	            } while (cursor.moveToNext());
	        }
	            if (counter!=0)
	            	return true;
	            else
	            	return false;
	            	
	            	
	            
	        }
	           	   
	       
	     
	      
	    
	 public boolean getPendingPoi(Poi poiSearch) {
		 	int poisCounter=0;
		 	boolean poiExists;
		 	
	        Cursor cursor = database.query(DatabaseHandler.TABLE_PENDING_POIS,DatabaseHandler.COLUMNS,DatabaseHandler.KEY_X + " =? AND " + DatabaseHandler.KEY_Y + "=?", new String[] {String.valueOf(poiSearch.getX()),String.valueOf(poiSearch.getY()) }, null, null,null,null);
	        
	        if (cursor != null){
	            cursor.moveToFirst();
	            poiExists=true;
	            Poi poi=new Poi(cursor.getDouble(0),cursor.getDouble(1),cursor.getString(2),cursor.getString(3));
	            
	        }
	        else{
	        	
	        	poiExists=false;
	        	
	        }
	       
	        return poiExists;
	    }
	 
	 
	
}
