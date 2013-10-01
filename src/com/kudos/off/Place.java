package com.kudos.off;

import android.os.Parcel;
import android.os.Parcelable;


//need to import GooglePlayServices
import com.google.android.gms.maps.model.LatLng;


/*Interface, lets you pass place object through intents. Like serializable, but more xxXXmlgswagXXxx*/
public class Place implements Parcelable{
 
    private String name;
     
    //public String icon;
     
    private LatLng location;
     
    private String address;
      
    private double rating;
    
    /** constructor **/
    public Place(String name, Double lat, Double lng, String address, Double rating) {
    	this.name = name;
    	this.address = address;
    	this.rating = rating;
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

	public Double getRating() {
		return rating;
	}
	
	@Override
    public String toString() {
        return name;
    }

	/*Constructor, converts parcel object back into class instance*/
	private Place (Parcel in) {
		name = in.readString();
		location=LatLng.CREATOR.createFromParcel(in);
		address = in.readString();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*Instance to parcel conversion*/
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(name);
		location.writeToParcel(out, flags);
		out.writeString(address);
	}
	
    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
    
}