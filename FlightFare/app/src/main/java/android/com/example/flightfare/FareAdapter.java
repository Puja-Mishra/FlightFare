package android.com.example.flightfare;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

/**
 * Created by puja on 27/3/17.
 */
//custom adapter for the fare listview
public class FareAdapter extends ArrayAdapter<Flight.Fare> {

    private String moriginalCode;
    private String mdestinationCode;

    private String marrivalTime;
    private String mdepartTime;
    public FareAdapter(Context context, ArrayList<Flight.Fare> fare,String originalCode,String destinationCode,String arrivalTime,String departTime) {
        super(context, 0, fare);
        moriginalCode=originalCode;
        mdestinationCode=destinationCode;
        marrivalTime=arrivalTime;
        mdepartTime=departTime;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listview = convertView;
        if (listview == null)

        {
            listview = LayoutInflater.from(getContext()).inflate(R.layout.activity_fare, parent, false);
        }
        Flight.Fare fareObject = getItem(position);
        Flight flight;
        TextView providerId = (TextView) listview.findViewById(R.id.providerId);
        TextView fare = (TextView) listview.findViewById(R.id.fare);
        int i= Integer.parseInt(fareObject.getProviderId());
        String provider="";
        //setting the provider after reading providerId from Json
        switch (i)
        {
            case 1: provider="MakeMyTrip";
                    break;
            case 2: provider="Cleartrip";
                    break;
            case 3: provider="Yatra";
                    break;
            case 4: provider="Musafir";
                    break;
        }
        providerId.setText(provider);
        fare.setText(fareObject.getFare());
        Button btn=(Button)listview.findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"Future Android Developer will book your Flight",Toast.LENGTH_SHORT).show();
            }
        });

        //getting the data,month and time and setting it on the view
        String oDate;

        String arrivalDate=marrivalTime;
        String parts[]=arrivalDate.split("/");
        oDate=parts[0];

        String departDate=mdepartTime;


        String dateparts[]=oDate.split("-");
        String dateJourney=dateparts[0];
        int monthJourney=Integer.parseInt(dateparts[1]);
        String months=getMonthForInt(monthJourney);
        ((AppCompatActivity)getContext()).getSupportActionBar().setTitle(moriginalCode+"-"+ mdestinationCode
                + "   "+ dateJourney
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