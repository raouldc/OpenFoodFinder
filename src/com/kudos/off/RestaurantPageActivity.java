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
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_page);

		Bundle b = getIntent().getExtras();
		Place place = b.getParcelable("com.kudos.off.Place");
		setTitle("Open Food Finder");
		TextView textName = (TextView) findViewById(R.id.textName);
		TextView textAddress = (TextView) findViewById(R.id.textAddress);
		ImageView icon = (ImageView) findViewById(R.id.restaurantIcon);

		textName.setText(place.getName());
		textAddress.setText(place.getAddress());

		new DownloadIconTask((ImageView) findViewById(R.id.restaurantIcon))
				.execute(place.getIconURL());

		// all the map related code
//		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(
//				R.id.map)).getMap();
//		map.addMarker(new MarkerOptions().position(place.getLocation()).title(
//				place.getName()));
//
//		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
//				place.getLocation(), 15);
//		map.animateCamera(update);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant_page, menu);
		return true;
	}

}
