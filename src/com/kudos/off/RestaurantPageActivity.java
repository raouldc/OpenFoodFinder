package com.kudos.off;

import com.kudos.off.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;



public class RestaurantPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_page);
		
		Bundle b = getIntent().getExtras();
		Place place = b.getParcelable("com.kudos.off.Place");
		
		TextView textName = (TextView)findViewById(R.id.textName);
		TextView textAddress = (TextView)findViewById(R.id.textAddress);
		
		textName.setText(place.getName());
		textAddress.setText(place.getAddress());
		textAddress.setText(place.getRating().toString());
		//textAddress.setText(place.getLocation().latitude + "," + place.getLocation().longitude);
		
		//if (place.getRating() > 0) { //-1 rating means rating not available/unknown
			TextView textRating = (TextView)findViewById(R.id.textRating);
			//should make more pretty
			textRating.setText("Rating: " + place.getRating().toString()); 
		
		
		//all the map related code
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.addMarker(new MarkerOptions().position(place.getLocation()).title(place.getName()));

		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(place.getLocation(), 15);
		map.animateCamera(update);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant_page, menu);
		return true;
	}

}
