package com.kudos.off;

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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;


public class MainActivity extends Activity {

	private List<Place> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView listView = (ListView) findViewById(R.id.listView);
		TextView textView = (TextView) findViewById(R.id.textView);

		/**##### Example of how to use GooglePlaces class #####**/
		GooglePlaces googlePlaces = new GooglePlaces();

		String key = "AIzaSyCH8wY-9O12M-A6xFH-tpENupZ1pqyunS0";
		Double lat = -36.8498; Double lng = 174.7650; //These coordinates is Queen St. Auckland
		int radius = 500; //500m

		/* get list of restaurants */
		final List<Place> list = googlePlaces.getPlaces(key, lat, lng, radius);
		
		ListAdapter listAdapter = new CustomPlaceAdapter(MainActivity.this,list);
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
			TextView address = (TextView)listItemView.findViewById(R.id.address);
//			RatingBar rating = (RatingBar)listItemView.findViewById(R.id.rating);
			
			name.setText(places.get(position).getName());
			address.setText(places.get(position).getAddress());
			address.setText("Rating: " + places.get(position).getRating().toString());
			
			return listItemView;
					
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
