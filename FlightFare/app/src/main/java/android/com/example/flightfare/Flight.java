package android.com.example.flightfare;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by puja on 27/3/17.
 */

public class Flight implements Serializable {
    private String airlineCode;
    private String airlineClass;
    private String originCode;
    private String destinationCode;
    private String departureTime;
    private String ArrivalTime;
    ArrayList<Fare>fares=new ArrayList<>();

    public Flight (String originCode,String destinationCode,String departureTime,String arrivalTime,
                   String airlineCode, String airlineClass){
        this.originCode=originCode;
        this.destinationCode=destinationCode;
        this.departureTime=departureTime;
        this.ArrivalTime=arrivalTime;
        this.airlineCode=airlineCode;
        this.airlineClass=airlineClass;

    }


    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirlineClass() {
        return airlineClass;
    }

    public void setAirlineClass(String airlineClass) {
        this.airlineClass = airlineClass;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public class Fare implements Serializable {
        private String providerId;
        private String fare;


        public Fare(String providerId,String fare){
            this.providerId=providerId;
            this.fare=fare;
        }

        public  String getProviderId() {
            return providerId;
        }

//        public void setProviderId(String providerId) {
//            this.providerId = providerId;
//        }

        public String getFare() {
            return fare;
        }

//        public void setFare(String fare) {
//            this.fare = fare;
//        }
    }
}
