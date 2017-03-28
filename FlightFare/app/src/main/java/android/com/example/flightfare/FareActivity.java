package android.com.example.flightfare;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

//shows all the fares from all providers for that flight
public class FareActivity extends AppCompatActivity {

    private static final String LOG_TAG = FareActivity.class.getSimpleName();
    //second screen that appears after clicking on the listview of first sceen
    //it dynamincally gets rendered
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_search);
        Flight flightObject= (Flight)getIntent().getSerializableExtra("Fares");
        ArrayList<Flight.Fare> fare = flightObject.fares;
        final FareAdapter fareAdapter=new FareAdapter(this,fare,flightObject.getOriginCode(),flightObject.getDestinationCode(),flightObject.getArrivalTime(),flightObject.getDepartureTime());
        ListView farelistview=(ListView)findViewById(R.id.list);
        farelistview.setAdapter(fareAdapter);


    }
}
