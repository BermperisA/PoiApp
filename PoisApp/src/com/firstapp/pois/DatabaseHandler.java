package com.firstapp.pois;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
// All Static variables
// Database Version
	private static final int DATABASE_VERSION = 1;
	private static DatabaseHandler sInstance;
	private static SQLiteDatabase myWritableDb;
	private static SQLiteDatabase myReadableDb;
	// Database Name
	private static final String DATABASE_NAME = "pois.db";
	// POIS table name
	public static final String TABLE_POIS = "pois";
	public static final String TABLE_PENDING_POIS ="pendingPois";

	// Contacts Table Columns names
	public static final String KEY_POINAME = "poiName";
	public static final String KEY_TYPE = "type";
	public static final String KEY_X = "x";
	public static final String KEY_Y = "y";
	public static final String[]COLUMNS= {KEY_X,KEY_Y,KEY_POINAME,KEY_TYPE};
	private static final String CREATE_POIS_TABLE = "CREATE TABLE " + TABLE_POIS + "("
            + KEY_X + " REAL," + KEY_Y + " REAL,"
            + KEY_POINAME + " TEXT," + KEY_TYPE + " TEXT" + ");";
	private static final String CREATE_PENDING_POIS_TABLE = "CREATE TABLE " + TABLE_PENDING_POIS + "("
            + KEY_X + " REAL," + KEY_Y + " REAL,"
            + KEY_POINAME + " TEXT," + KEY_TYPE + " TEXT" + ");";
	public static DatabaseHandler getInstance(Context context) {

	    // Use the application context, which will ensure that you 
	    // don't accidentally leak an Activity's context.
	    // See this article for more information: http://bit.ly/6LRzfx
	    if (sInstance == null) {
	      sInstance = new DatabaseHandler(context.getApplicationContext());
	    }
	    return sInstance;
	  }
	
	 public SQLiteDatabase getMyWritableDatabase() {
	        if ((myWritableDb == null) || (!myWritableDb.isOpen())) {
	            myWritableDb = this.getWritableDatabase();
	        }
	 
	        return myWritableDb;
	    }
	 public SQLiteDatabase getMyReadableDatabase(){
		if((myReadableDb==null)|| (!myReadableDb.isOpen()))   {
			myReadableDb=this.getReadableDatabase();
			}
		return myReadableDb;	 
		 
	 }
	 
	    @Override
	    public void close() {
	        super.close();
	        if (myWritableDb != null) {
	            myWritableDb.close();
	            myWritableDb = null;
	        }
	        if(myReadableDb!=null){
	        	myReadableDb.close();
	        	
	        }
	    }
	
	public DatabaseHandler(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		
	}
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		
		db.execSQL(CREATE_POIS_TABLE);
		db.execSQL("CREATE UNIQUE INDEX pois_idx ON " + TABLE_POIS + "(" + KEY_X + "," + KEY_Y + ")" );
		db.execSQL(CREATE_PENDING_POIS_TABLE);
		db.execSQL("CREATE UNIQUE INDEX pendingpois_idx ON " + TABLE_PENDING_POIS + "(" + KEY_X + "," + KEY_Y + ")" );
	}
	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_POIS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PENDING_POIS);
		// Create tables again
		onCreate(db);}
	/**
     * All CRUD(Create, Read, Update, Delete) Operations
     */  
    
}