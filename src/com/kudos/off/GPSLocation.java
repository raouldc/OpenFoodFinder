package com.kudos.off;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

public class GPSLocation implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener  {

	Location _location;
	Context context;
	private LocationClient mLocationClient;

	public GPSLocation(Context context)
	{
		this.context=context;
        mLocationClient = new LocationClient(context, this, this);
        mLocationClient.connect();
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

	protected void updateWithNewLocation(Location location) {
		_location = location;
	}

	 @Override
	    public void onConnected(Bundle dataBundle) {
	        // Display the connection status
		 	_location = mLocationClient.getLastLocation();
	        Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();

	    }
	    /*
	     * Called by Location Services if the connection to the
	     * location client drops because of an error.
	     */
	    @Override
	    public void onDisconnected() {
	        // Display the connection status
	        Toast.makeText(context, "Disconnected. Please re-connect.",
	                Toast.LENGTH_SHORT).show();
	    }
	    /*
	     * Called by Location Services if the attempt to
	     * Location Services fails.
	     */
	    @Override
	    public void onConnectionFailed(ConnectionResult connectionResult) {
	    }
}