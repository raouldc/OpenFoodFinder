package com.kudos.openfoodfinder;

import com.example.openfoodfinder.R;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class SearchableActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query =  intent.getStringExtra(SearchManager.QUERY);
			doMySearch(query);
		}
		
	}
	
	public void doMySearch(String query) {
		
	}
	
}
