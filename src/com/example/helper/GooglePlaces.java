package com.example.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.SystemClock;

public class GooglePlaces {
	
	private HttpClient client;
	private JSONObject jsonObject;
	final static String baseURL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
	
	/** The main method **/
	public List<Place> getPlaces(String key, Double lat, Double lng, int radius) {
		 /* use StringBuilder instead of just concatenating because 
		  * Strings are immutable - conserving android system resources */
		StringBuilder url = new StringBuilder(baseURL);
		url.append("key=" + key); //append API_KEY
		url.append("&location=" + lat.toString() + "," + lng.toString());
		url.append("&radius=" + String.valueOf(radius));
		url.append("&query=food%7Crestaurant%7Cbar%7Ccafe"); // "%7C" is equivalent to "|"
		url.append("&sensor=false"); //true or false??? true means users location was obtained from GPS service
		
		String seeURL = url.toString();
		
		AsyncTask<String, Integer, String> task = new Task();
		//task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url.toString());
		task.execute(url.toString());
		
		/* wait until task has finished */
		while (jsonObject == null) {
			SystemClock.sleep(1000);
			// or is it better to use Thread.currentThread.sleep(1000); ?
		}
		
		return convertToList(url.toString());
	}
	
	private synchronized JSONObject getJSONObject(String url)
			throws ClientProtocolException, IOException, JSONException {

		client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
				
		int status = response.getStatusLine().getStatusCode();

		if (status == 200) { // status code 200 means success
			HttpEntity entity = response.getEntity();
			String data = EntityUtils.toString(entity);

			JSONObject jsonObj = new JSONObject(data);
			
			/* Note: jsonObj = jsonObj.getJSONObject("results"); - is WRONG. 
			 * results is not a JSONObject type, it is a JSONArray type */
			
			return jsonObj;
			
		} else {
			// Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG);
			throw new ClientProtocolException();
		}
	}

	public class Task extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... url) {
			try {
				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
				/* we are only dealing with 1 url (which is the first url) */
				jsonObject = getJSONObject(url[0]);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				e.getCause().toString();
			} catch (IOException e) {
				e.printStackTrace();
				e.getCause().toString();
			} catch (JSONException e) {
				e.getCause().toString();
			}

			return jsonObject.toString();
		}
		
		/** gets whatever String was returned from doInBackground **/
		@Override
		protected void onPostExecute(String result) {
			//doesn't seem to work that well
		}
	}

	private List<Place> convertToList(String url) {
		List<Place> listOfPlaces = new ArrayList<Place>();
		
		if (this.jsonObject == null) {
			//take some action if jsonObject is still null
		}

		try {
			JSONObject currentJSONObj;
			JSONObject geometry;
			JSONObject location;

			JSONArray jsonArray = this.jsonObject.getJSONArray("results");
			Place currentPlace;

			int i = 0;
			while (i < jsonArray.length()) {
				currentJSONObj = jsonArray.getJSONObject(i);

				geometry = currentJSONObj.getJSONObject("geometry");
				location = geometry.getJSONObject("location");

				currentPlace = new Place(currentJSONObj.getString("name"),
						location.getDouble("lat"), location.getDouble("lng"),
						currentJSONObj.getString("formatted_address"));
				listOfPlaces.add(currentPlace);
				i++;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listOfPlaces;
	}
}