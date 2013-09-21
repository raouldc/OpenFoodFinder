package com.example.helper;

//need to import GooglePlayServices
import com.google.android.gms.maps.model.LatLng;


public class Place {
 
    private String name;
     
    //public String icon;
     
    private LatLng location;
     
    private String address;
      
    
    /** constructor **/
    public Place(String name, Double lat, Double lng) {
    	this.name = name;
    	//this.address = address;
    	setLocation(lat, lng);
    }
    
	public void setLocation(Double lat, Double lng) {
		this.location = new LatLng(lat, lng);
	}

	public String getName() {
		return name;
	}

	public LatLng getLocation() {
		return location;
	}

	public String getAddress() {
		return address;
	}

	@Override
    public String toString() {
        return name;
    }
    
}