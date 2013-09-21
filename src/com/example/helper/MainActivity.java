package com.example.helper;

import java.util.List;

import com.example.helper.GooglePlaces;
import com.example.helper.Place;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView textView = (TextView) findViewById(R.id.textView);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GooglePlaces googlePlaces = new GooglePlaces();
		List<Place> list = googlePlaces.getPlaces();
		try {
		for (Place place : list) {
			if (list.isEmpty()) {
				textView.setText("empty list");
				break;
			}
			textView.append(place.toString() + "\n");
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
