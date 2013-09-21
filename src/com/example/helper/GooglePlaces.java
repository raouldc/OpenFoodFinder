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
	public int count = 0;
	
	private HttpClient client;
	private JSONObject jsonObject;
	private String result;
	final static String URL = "https://maps.googleapis.com/maps/api/place/search/json?key=AIzaSyCH8wY-9O12M-A6xFH-tpENupZ1pqyunS0&location=37.994682%2C-87.6045923&radius=500&sensor=false";

	private JSONObject getJSONObject(String argUrl)
			throws ClientProtocolException, IOException, JSONException {
		// StringBuilder url = new StringBuilder(URL2); // use StringBuilder
		// instead of just
		// concatenating because
		// Strings
		// are immutable - conserve android system resources

		// url.append(username);

		client = new DefaultHttpClient();
		StringBuilder url = new StringBuilder(URL);

		// HttpGet request = new HttpGet(url.toString());
		HttpGet request = new HttpGet(URL);

		HttpResponse response = client.execute(request);
		int status = response.getStatusLine().getStatusCode();

		/**
		 * Status Codes: 1xx - informational 2xx - success (200) 3xx -
		 * redirection 4xx - client error 5xx - server error
		 */

		if (status == 200) {
			HttpEntity entity = response.getEntity();
			String data = EntityUtils.toString(entity);

			// do some formatting to get the actual array which is inside [
			// ]
			// brackets
			// or do formatting later.... probably better idea.
			// String resultsData = data.substring(
			// data.indexOf("\"results\" : [") + 12,
			// data.indexOf("\"status\" : ") - 2);
			// JSONArray jsonArray = new JSONArray(resultsData);
			// JSONObject jsonObj = new JSONObject(jsonArray.toString());
			JSONObject jsonObj = new JSONObject(data);
			// jsonObj = jsonObj.getJSONObject("results"); //WRONG. results is
			// not a JSONObject type, it must be a JSONArray type.
			return jsonObj;
		} else {
			// Toast.makeText(getApplicationContext(), "error",
			// Toast.LENGTH_LONG);
			throw new ClientProtocolException();
		}
	}

	public class Thread extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				jsonObject = getJSONObject(null);
				// return jsonObject.toString();
				// return jsonObject.toString();
			}

			catch (ClientProtocolException e) {
				e.printStackTrace();
				e.getCause().toString();
			} catch (IOException e) {
				e.printStackTrace();
				e.getCause().toString();
			} catch (JSONException e) {
				// textView.setText(e.getCause().toString());
				e.getCause().toString();
			}
			return jsonObject.toString();

		}

		/** gets whatever String was returned from doInBackground **/
		@Override
		protected void onPostExecute(String result1) {
			result = result1;
		}

	}

	// get URL as arg
	public List<Place> getPlaces() {
		new Thread().execute();
		if (this.jsonObject == null) {
			System.out.println("null JSONObj!");
		}
		return convertToList(this.jsonObject);
	}

	private List<Place> convertToList(JSONObject jsonObj) {
		// this.result = jsonString.substring(
		// jsonString.indexOf("\"results\" : [") + 12,
		// jsonString.indexOf("\"status\" : ") - 2);

		List<Place> listOfPlaces = new ArrayList<Place>();

		try {
			JSONObject currentJSONObj;
			JSONObject geometry;
			JSONObject location;
			// JSONArray jsonArray = new JSONArray(this.result);
			while (this.jsonObject == null) {
				SystemClock.sleep(1000);
			}
			
			JSONArray jsonArray = jsonObj.getJSONArray("results");
			Place currentPlace;

			int i = 0;
			while (i < jsonArray.length()) {
				currentJSONObj = jsonArray.getJSONObject(i);

				geometry = currentJSONObj.getJSONObject("geometry");
				location = geometry.getJSONObject("location");

				currentPlace = new Place(currentJSONObj.getString("name"),
						location.getDouble("lat"), location.getDouble("lng"));
				// currentJSONObj.getString("formatted_address"));

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