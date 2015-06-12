package logic.testrequest;



import java.io.OutputStream;

import httpRequest_manager.AllRequestsActivity;
import httpRequest_manager.NewRequestActivity;
import info.androidhive.loginandregistration.R;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity_register extends ActionBarActivity {

	ProgressBar pb;	
	Button btnViewRequests ;
	Button btnNewRequest ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);
 
		btnViewRequests = (Button) findViewById(R.id.btnViewRequests);
 		btnNewRequest = (Button) findViewById(R.id.btnCreateRequest);
		
		
 	// view request click event
        btnViewRequests.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), AllRequestsActivity.class);
                startActivity(i);
 
            }
        });

        
     // view request click event
        btnNewRequest.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), NewRequestActivity.class);
                startActivity(i);
 
            }
        });

	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {

			if (isOnline()) {

				// our functionality 

			} else {

				Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();

			}


			return true;
		}
		return super.onOptionsItemSelected(item);
	}









	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnectedOrConnecting()) {

			return true ;

		} else {

			return false ; 

		}


	}



	private class myTask extends AsyncTask<String ,String , String>{

		//Start task
		@Override
		protected void onPreExecute() {

			pb.setVisibility(View.VISIBLE);


		}


		//task complete
		@Override
		protected String doInBackground(String... params) {

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			pb.setVisibility(View.INVISIBLE);
			super.onPostExecute(result);
		}






	}

}
