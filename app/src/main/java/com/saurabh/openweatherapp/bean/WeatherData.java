package com.saurabh.openweatherapp.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherData implements Serializable {

    private double lat;
    private double lon;
    private String timezone;
    private String timezone_offset;
    private Current current;
    private ArrayList<Hourly> hourly;
    private ArrayList<Daily> daily;

    public WeatherData(double lat, double lon, String timezone, String timezone_offset, Current current, ArrayList<Hourly> hourly, ArrayList<
            Daily> daily) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.timezone_offset = timezone_offset;
        this.current = current;
        this.hourly = hourly;
        this.daily = daily;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(String timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public ArrayList<Hourly> getHourly() {
        return hourly;
    }



    public ArrayList<Daily> getDaily() {
        return daily;
    }


}
