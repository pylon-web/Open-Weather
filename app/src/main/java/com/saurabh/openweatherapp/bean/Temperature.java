package com.saurabh.openweatherapp.bean;

import java.io.Serializable;

public class Temperature implements Serializable {
    private String day;
    private String min;
    private String max;
    private String night;
    private String eve;
    private String morn;

    public Temperature(String day, String min, String max, String night, String eve, String morn) {
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }

    public Temperature(String morn,String day, String eve, String night) {
        this.day = day;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getEve() {
        return eve;
    }

    public void setEve(String eve) {
        this.eve = eve;
    }

    public String getMorn() {
        return morn;
    }

    public void setMorn(String morn) {
        this.morn = morn;
    }
}
