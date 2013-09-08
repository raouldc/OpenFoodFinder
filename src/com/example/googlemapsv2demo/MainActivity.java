package com.example.googlemapsv2demo;

/* Prerequisites:
 * 	- Google Play Services installed - make sure you import into project correctly
 * 
 * 	- Insert your own API key into AndroidManifest? I think it's for your own machine
 *    or try the one that Raoul told us to use: AIzaSyB-Xj3yiseh-5kItzoN6b0jG5rG0BQ_-us
 *
 * Target:
 * MinimumSDKVersion = 11 for getFragmentManager()
 * Android API 17 (4.2.2)
 */

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {
	//GPS coordinates
	private final LatLng LOCATION_AUCKLAND = new LatLng(-36.8404, 174.7399);
	private final LatLng LOCATION_WELLINGTON = new LatLng(-41.2889, 174.7772);

	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		//I would prefer adding markers here since we only need to do it once
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick_Auckland(View view) {
		//add marker
		//but does this continuously add marker?? if so, memory can run out.
		map.addMarker(new MarkerOptions().position(LOCATION_AUCKLAND).title("This is Auckland"));

		map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_AUCKLAND, 15);
		// higher number (arg2) = more zoom
		// or do CameraUpdate update = CameraUpdateFactory.newLatLng(LOCATION_AUCKLAND);

		map.animateCamera(update);
	}
	
	public void onClick_Wellington(View view) {
		map.addMarker(new MarkerOptions().position(LOCATION_WELLINGTON).title("This is Wellington"));
		
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_WELLINGTON, 14);
		map.animateCamera(update);
	}
	
}
