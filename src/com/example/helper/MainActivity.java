package com.example.helper;

import java.util.List;

import com.example.helper.GooglePlaces;
import com.example.helper.Place;

import com.example.helper.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView textView = (TextView) findViewById(R.id.textView);

		/**##### Example of how to use GooglePlaces class #####**/
		GooglePlaces googlePlaces = new GooglePlaces();

		/* arguments for getPlaces() method */
		String key = "AIzaSyCH8wY-9O12M-A6xFH-tpENupZ1pqyunS0";
		Double lat = -36.8498; Double lng = 174.7650; //These coordinates is Queen St. Auckland
		int radius = 500; //500m
		
		/* get list of restaurants */
		List<Place> list = googlePlaces.getPlaces(key, lat, lng, radius);
		
		try {
			/* iterate through each place in list */
			for (Place place : list) {
				if (list.isEmpty()) {
					textView.setText("empty list");
					break;
				}
				/* print details of place */
				textView.append(System.getProperty("line.separator")+ "name: " + place.getName());
				textView.append(System.getProperty("line.separator")+ "address: " + place.getAddress() +
						System.getProperty("line.separator")); //and so on for other details..
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
