package com.saurabh.openweatherapp.bean;

import java.io.Serializable;

public class Daily implements Serializable {

    private String dt;
    private double timezone;
    private double lon;
    private String timezone_offset;
    private Temperature temperature;
    private Weather weather;
    private String pop;
    private String uvi;


    public Daily(String dt,double timezone,double lon,String timezone_offset, Temperature temperature, Weather weather, String pop, String uvi) {
        this.dt = dt;
        this.timezone = timezone;
        this.lon = lon;
        this.timezone_offset = timezone_offset;
        this.temperature = temperature;
        this.weather = weather;
        this.pop = pop;
        this.uvi = uvi;
    }

    public String getDt() {
        return dt;
    }

    public double getLon() {
        return lon;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getUvi() {
        return uvi;
    }

    public void setUvi(String uvi) {
        this.uvi = uvi;
    }

    public double getTimezone() {
        return timezone;
    }

    public String getTimezone_offset() {
        return timezone_offset;
    }

    @Override
    public String toString() {
        return "Daily{" +
                "dt='" + dt + '\'' +
                ", timezone='" + timezone + '\'' +
                ", timezone_offset='" + timezone_offset + '\'' +
                ", temperature=" + temperature +
                ", weather=" + weather +
                ", pop='" + pop + '\'' +
                ", uvi='" + uvi + '\'' +
                '}';
    }
}
