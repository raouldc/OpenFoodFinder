package com.kudos.off;

import java.util.Collections;
import java.util.List;


import com.kudos.off.R;
import com.kudos.off.GooglePlaces;
import com.kudos.off.Place;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnItemSelectedListener {

	private int radius = 500; //default value of 500m
	private Bundle bundle;
	private String[] keyList;
	boolean connectionStatusOK = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		bundle = savedInstanceState;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Open Food Finder");
		ListView listView = (ListView) findViewById(R.id.listView);
		
		TextView statusText = (TextView) findViewById(R.id.textView1);
		
		Spinner spinner = (Spinner) findViewById(R.id.drop_down);
		spinner.setOnItemSelectedListener(this);

		Double lat, lng;
		try {
			GPSLocation gLoc  = new GPSLocation(getApplicationContext());
			lat = gLoc.getLatitude(); lng = gLoc.getLongitude(); 
		} catch (Exception e) {
			lat = -36.8498; lng = 174.7650; //Queen St. Auckland
		}

		List<Place> listOfPlaces = null;

		keyList = getResources().getStringArray(R.array.keys);
		for (int i=0; i<keyList.length; i++) {
			try {
				listOfPlaces = new GooglePlaces().getPlaces(keyList[i], lat, lng, radius);
				
				if (listOfPlaces == null) {
					// could not connect at all
					break;
				} else if (listOfPlaces.size() == 0) {
					// max quota reached for key
					continue;
				} else {
					connectionStatusOK = true;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		final List<Place> list;
		
		if (connectionStatusOK == false) {
			statusText.setText("Connection Error");
			list = Collections.emptyList();
		} else {
			list = listOfPlaces;
		}
		
		ListAdapter listAdapter = new CustomPlaceAdapter(MainActivity.this, list);
		listView.setAdapter(listAdapter);
		
		/*Anonymous class to create a listener for item selections.*/
		listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedPos, long id) {
				Place clickedOn = list.get(clickedPos); //Get the contact that has been clicked on from the array
				
				/*Intent to pass the Contact object that has been clicked on into the new activity, 
				 * by bundling it first.
				 */
				Intent intent = new Intent(MainActivity.this, RestaurantPageActivity.class);
				intent.putExtra("com.kudos.off.Place", clickedOn); //Should be changed to com.kudos.openfoodfinder.Place
				startActivity(intent);
			}
		});
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.radius_array	, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);	
	}
		
	private class CustomPlaceAdapter extends ArrayAdapter<Place> {
		private Context context;
		private List<Place> places;
		
		CustomPlaceAdapter(Context context, List<Place> places) {
			super(context, android.R.layout.simple_list_item_1, places);
			
			this.context = context;
			this.places = places;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View listItemView = inflater.inflate(R.layout.custom_list,null);
			
			TextView name = (TextView)listItemView.findViewById(R.id.name);
			TextView noRating = (TextView)listItemView.findViewById(R.id.rating);
			RatingBar ratingBar = (RatingBar)listItemView.findViewById(R.id.ratingBar);
			
			
			name.setText(places.get(position).getName());
			Double ratingValue = places.get(position).getRating();
			if (ratingValue < 0) {
					noRating.setText("No rating available");
					noRating.setVisibility(View.VISIBLE);
				} else {
					ratingBar.setRating(Float.parseFloat(ratingValue.toString()));
					ratingBar.setVisibility(View.VISIBLE);
				}
			
			return listItemView;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
	 if (!(parent.getItemAtPosition(pos).toString().equals("Radius(m)"))) {
		 radius = Integer.parseInt(parent.getItemAtPosition(pos).toString());
		 this.onCreate(bundle);		 
	 }
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
