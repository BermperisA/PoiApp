package com.firstapp.pois;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Poi {
	private double _x;
	private double _y;
	private String _poiName;
	private String _type;
	
	public Poi(){
		
		
		
	}
	
	public Poi( double x,double y,String poiName,String type){
		super();
		this._poiName=poiName;
		this._type=type;
		this._x=x;
		this._y=y;
		
		
	}
	public Poi(String type,double x,double y){
		this._type=type;
		this._x=x;
		this._y=y;				
	}
	
	public Poi(double x,double y){
		this._x=x;
		this._y=y;	
		
	}
	public String getPoiName(){
		
		return this._poiName;
		
	}
	public void setPoiName(String poiName){
		
		this._poiName=poiName;
		
		
	}
	public String getType(){
		
		return this._type;
		
	}
	public void setType(String type){
		this._type=type;
		
		
	}
	
	public double getX(){
		return this._x;
		
	}
	public void setX(double x){
		this._x=x;
		
	
	}
	public double getY(){
		return this._y;
	
	}
	public void setY(double y){
		
		this._y=y;
		
	}
}
