package com.kudos.off;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class GPSLocation {
	
	Location _location;
	
	public GPSLocation(Context context)
	{
		LocationManager locManager;
        locManager =(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        _location = locManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (_location != null) {
            double latitude = _location.getLatitude();
            double longitude = _location.getLongitude();
        }
	}
	
	//returns the Latitude of the current location
	public double getLatitude()
	{
		return _location != null?_location.getLatitude():0;
	}
	
	//returns the Longitude of the current location
	public double getLongitude()
	{
		return _location != null?_location.getLongitude():0;
		
	}

}
