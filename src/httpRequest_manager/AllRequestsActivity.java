package httpRequest_manager;

import info.androidhive.loginandregistration.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.helper.JSONParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.tv.TvContract.Programs;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AllRequestsActivity extends ListActivity {


	private ProgressDialog pd;

	//jsone parser object

	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> requestsList;
	
	
	// url to get all requests list
	//"http://localhost:8080/testUserManager/webresources/servertest.users/searchUsers"/"+ name;
	//" http://localhost:8080/testUserManager/webresources/servertest.users/" + id+ "/"+name;
	
	private static String COUNT_URL = "";
	private static String url_all_requests = "";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_REQUESTS = "requests";
	private static final String TAG_RID = "rid";
	private static final String TAG_RQUEST_TEXT = "request_text";


	JSONArray requests = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_requests);

		requestsList = new ArrayList<HashMap<String, String>>();

		// Loading requests in Background Thread

		new LoadAllRequests().execute();

		// Get listview
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {


			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				// getting values from selected ListItem
				String rid = ((TextView) view.findViewById(R.id.rid)).getText().toString();
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), EditRequestActivity.class);

				// sending rid to next activity
				in.putExtra(TAG_RID, rid);

				// starting new activity and expecting some response back
				startActivityForResult(in, 100);

			}
		});
	}

	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadAllRequests extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(AllRequestsActivity.this);
			pd.setMessage("Loading products. Please wait...");
			pd.setIndeterminate(false);
			pd.setCancelable(false);
			pd.show();
		}


		/**
		 * getting All requests from url
		 * */

		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_requests, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					requests = json.getJSONArray(TAG_REQUESTS);

					// looping through All Products
					for (int i = 0; i < requests.length(); i++) {
						JSONObject c = requests.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_RID);
						String name = c.getString(TAG_RQUEST_TEXT);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_RID, id);
						map.put(TAG_RQUEST_TEXT, name);

						// adding HashList to ArrayList
						requestsList.add(map);
					}
				} else {
					// no products found
					// Launch Add New product Activity
					Intent i = new Intent(getApplicationContext(),
							NewRequestActivity.class);
					// Closing all previous activities
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pd.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							AllRequestsActivity.this, requestsList,
							R.layout.list_item, new String[] { TAG_RID,TAG_RQUEST_TEXT},new int[] { R.id.rid, R.id.name });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}
	}

}