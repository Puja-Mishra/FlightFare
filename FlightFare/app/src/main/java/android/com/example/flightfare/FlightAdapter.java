package android.com.example.flightfare;

/**
 * Created by puja on 27/3/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by puja on 26/3/17.
 */
//custom Adapter for Flight for first screen listview
public class FlightAdapter extends ArrayAdapter<Flight> {

    public static final String  LOG_TAG=FlightAdapter.class.getSimpleName();
    public FlightAdapter(Context context, ArrayList<Flight>flight)
    {
        super(context,0,flight);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listview=convertView;
        if(listview==null)
        {


            listview= LayoutInflater.from(getContext()).inflate(R.layout.flight_list,parent,false);
        }
        Flight f=getItem(position);

        //getting the data,month and time and setting it on the view
        String oDate;
        String aTime;
        String arrivalDate=f.getArrivalTime();
        String parts[]=arrivalDate.split("/");
        oDate=parts[0];
        aTime=parts[1];
        String departDate=f.getDepartureTime();
        String dTime;
        String parts1[]=departDate.split("/");
        dTime=parts[1];
        String dateparts[]=oDate.split("-");
        String dateJourney=dateparts[0];
        int monthJourney=Integer.parseInt(dateparts[1]);
        String months=getMonthForInt(monthJourney);

        TextView arrivalTime=(TextView)listview.findViewById(R.id.arr_time);
        arrivalTime.setText(aTime);
        TextView deparTime=(TextView)listview.findViewById(R.id.dept_time);
        deparTime.setText(dTime);
        TextView airlineCode=(TextView)listview.findViewById(R.id.airlinecode);
        airlineCode.setText(f.getAirlineCode().toString());
        TextView classFlight=(TextView)listview.findViewById(R.id.classf);
        classFlight.setText(f.getAirlineClass());
        TextView fare=(TextView)listview.findViewById(R.id.fares1);
        ArrayList<Flight.Fare> fareValue=f.fares;



        Flight.Fare flightfare = f.fares.get(0);
        Integer  min= Integer.parseInt(flightfare.getFare());
        Integer  max= Integer.parseInt(flightfare.getFare());
        for(int i = 0; i < fareValue.size(); i++) {
            Flight.Fare fnum = f.fares.get(i);
            int number = Integer.parseInt(fnum.getFare());
            if(number < min) min = number;
            if(number > max) max = number;
        }
        //display the min fare of each flight on the first screen
        String mins=min.toString();
        fare.setText(mins);
        ((AppCompatActivity)getContext()).getSupportActionBar().setTitle(f.getOriginCode()+"-"+ f.getDestinationCode()+
                "  "+ dateJourney
                +" - "+ months);



        return listview;

    }
    //get the name of the month
    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }


}
