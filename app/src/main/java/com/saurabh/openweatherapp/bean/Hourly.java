package com.saurabh.openweatherapp.bean;

import java.io.Serializable;

public class Hourly implements Serializable {

    private String dt;
    private String timezone_offset;
    private String temp;
    private Weather weather;
    private String pop;

    public Hourly(String dt,String timezone_offset, String temp, Weather weather, String pop) {
        this.dt = dt;
        this.timezone_offset = timezone_offset;
        this.temp = temp;
        this.weather = weather;
        this.pop = pop;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
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

    public String getTimezone_offset() {
        return timezone_offset;
    }
}
