package com.saurabh.openweatherapp.Services;

import android.location.Address;
import android.location.Geocoder;

import com.saurabh.openweatherapp.DailyForecast;
import com.saurabh.openweatherapp.MainActivity;

import java.io.IOException;
import java.util.List;


public class Services {

    MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getWindDirection(double degrees){
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X";
    }


    public String convertCeil(String s){
        return String.valueOf((int)Math.ceil(Double.parseDouble(s)));
    }

    public String getLocationName(String userProvidedLocation) {
        Geocoder geocoder = new Geocoder(new DailyForecast()); // Here, “this” is an Activity
        try {
            List<Address> address =
                    geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) {
                // Nothing returned!
                return null;
            }
            String country = address.get(0).getCountryCode();
            String p1 = "";
            String p2 = "";
            if (country.equals("US")) {
                p1 = address.get(0).getLocality();
                p2 = address.get(0).getAdminArea();
            } else {
                p1 = address.get(0).getLocality();
                if (p1 == null)
                    p1 = address.get(0).getSubAdminArea();
                p2 = address.get(0).getCountryName();
            }
            String locale = p1 + ", " + p2;
            return locale;
        } catch (IOException e) {
            // Failure to get an Address object
            return null;
        }
    }




}
