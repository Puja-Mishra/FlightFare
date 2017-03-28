package android.com.example.flightfare;


import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
//it is the main activity that will start the main screen
public class FlightSearchActivity extends AppCompatActivity {

    //Url to access the JSON
    public static final String Request_URL = "https://firebasestorage.googleapis.com/v0/b/gcm-test-6ab64.appspot.com/o/ixigoandroidchallenge%2Fsample_flight_api_response.json?alt=media&token=d8005801-7878-4f57-a769-ac24133326d6";



    private static final String LOG_TAG = FlightSearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(LOG_TAG, "TEST : Flight ACTIVITY ON CREATE() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_search);
        //execute background process using ASYNC TASK to access netwrok connection and parse json
        FlightAsyncTask flightAsyncTask = new FlightAsyncTask();
        flightAsyncTask.execute(Request_URL);


    }


    private class FlightAsyncTask extends AsyncTask<String, Void, ArrayList<Flight>> {
        ArrayList<Flight> flights;

        //background thread of async task
        @Override
        protected ArrayList<Flight> doInBackground(String... params) {
            flights = NetworkUtils.fetchFlightdata(params[0]);
            return flights;

        }

        @Override
        protected void onPostExecute(ArrayList<Flight> events) {
            updateUi(flights);

        }
    }
    //main thread execution after doInBackground completes
    public void updateUi(final ArrayList<Flight> flights1) {

        final FlightAdapter flightAdapter = new FlightAdapter(this, flights1);
        ListView flightlistview = (ListView) findViewById(R.id.list);
        flightlistview.setAdapter(flightAdapter);
        flightlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flight f = flightAdapter.getItem(position);

                Log.d(LOG_TAG, "flight position" + f);
                Intent fareintent = new Intent(FlightSearchActivity.this, FareActivity.class);

                fareintent.putExtra("Fares",f);

                startActivity(fareintent);

            }
        });
    }


}


