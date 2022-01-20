package com.saurabh.openweatherapp.bean;

import java.io.InputStream;
import java.io.Serializable;

public class Current implements Serializable {

    private String dt;
    private String sunrise;
    private String sunset;
    private String temp;
    private String feel_like;
    private String pressure;
    private String humidity;
    private String uvi;
    private String clouds;
    private String visibility;
    private String wind_speed;
    private String wind_deg;
    private Weather weather;

    public Current(String dt, String sunrise, String sunset, String temp, String feel_like, String pressure, String humidity, String uvi, String clouds, String visibility, String wind_speed, String wind_deg, Weather weather) {
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.feel_like = feel_like;
        this.pressure = pressure;
        this.humidity = humidity;
        this.uvi = uvi;
        this.clouds = clouds;
        this.visibility = visibility;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
        this.weather = weather;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getFeel_like() {
        return feel_like;
    }

    public void setFeel_like(String feel_like) {
        this.feel_like = feel_like;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getUvi() {
        return uvi;
    }

    public void setUvi(String uvi) {
        this.uvi = uvi;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(String wind_deg) {
        this.wind_deg = wind_deg;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
