package android.com.example.flightfare;

/**
 * Created by puja on 27/3/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//Make network Connection and Parse JSON
public class NetworkUtils {

    public static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public static final ArrayList<Flight> fetchFlightdata(String requestURL) {
        URL url = createUrl(requestURL);
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem in creating connection" + e);
        }
        ArrayList<Flight> flights = extractdata(jsonResponse);
        return flights;


    }
//get the url
    private static URL createUrl(String stringurl) {
        URL url = null;

        try {
            url = new URL(stringurl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL " + e);
        }
        return url;
    }
//make http request
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;

        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();//connect to http

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readfromstream(inputStream);
            } else {
                Log.e(LOG_TAG, "Wrong Status code " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem retrieving in JSON response " + e);
        }//disconnect any connection if exists and close any inputstream
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //read the input stream
    private static String readfromstream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //parse the json and add to the array list of type flight object
    public static final ArrayList<Flight> extractdata(String jsonResponse) {
        ArrayList<Flight> flights = new ArrayList<Flight>();

        try {
            JSONObject rootobject = new JSONObject(jsonResponse);

            JSONArray flight = rootobject.getJSONArray("flights");


            for (int i = 0; i < flight.length(); i++) {
                JSONObject arrayObject = flight.getJSONObject(i);

                String originCode = arrayObject.getString("originCode");
                Log.d(LOG_TAG, "originalcode" + originCode);
                String destinationCode = arrayObject.getString("destinationCode");
                Log.d(LOG_TAG, "destinationcode" + destinationCode);
                String departureTime = arrayObject.getString("departureTime");
                Log.d(LOG_TAG, "departuretime" + departureTime);
                String arrivalTime = arrayObject.getString("arrivalTime");
                Log.d(LOG_TAG, "arrivaltime" + arrivalTime);
                String airlineCode = arrayObject.getString("airlineCode");
                Log.d(LOG_TAG, "airlinecode" + airlineCode);
                String airlineClass = arrayObject.getString("class");
                Log.d(LOG_TAG, "class" + airlineClass);
                Long eTime = Long.parseLong(arrivalTime);
                Date dateObject = new Date(eTime);
                Long dTime = Long.parseLong(departureTime);
                Date dateObject2 = new Date(dTime);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM/HH mm ");
                String arrival = dateFormat.format(dateObject);
                String depart = dateFormat.format(dateObject2);
                JSONArray arrayFare = arrayObject.getJSONArray("fares");

                String providerId;
                String fare;
                int number = arrayFare.length();
                Flight flightObject = new Flight(originCode, destinationCode, depart, arrival, airlineCode, airlineClass);
                for (int j = 0; j < arrayFare.length(); j++) {
                    JSONObject arrayFareObject = arrayFare.getJSONObject(j);
                    providerId = arrayFareObject.getString("providerId");
                    fare = arrayFareObject.getString("fare");
                    //Log.d(LOG_TAG, "providerId & fare :" + providerId + " " + fare);
                    Flight.Fare fareObject[] = new Flight.Fare[number];
                    fareObject[j] = flightObject.new Fare(providerId, fare);
                    Log.d(LOG_TAG, "fare " + fareObject[j].getFare());
                    Log.d(LOG_TAG, "Providerid" + fareObject[j].getProviderId());
                    flightObject.fares.add(fareObject[j]);


                }


                flights.add(flightObject);


//                flights.add(new Flight(originCode,destinationCode,depart,arrival,
//                        airlineCode,airlineClass));


            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, "problem parsing jsone", e);
        }
        return flights;

    }

}