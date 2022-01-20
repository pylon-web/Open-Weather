package com.saurabh.openweatherapp;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.saurabh.openweatherapp.bean.Current;
import com.saurabh.openweatherapp.bean.Daily;
import com.saurabh.openweatherapp.bean.Hourly;
import com.saurabh.openweatherapp.bean.Temperature;
import com.saurabh.openweatherapp.bean.Weather;
import com.saurabh.openweatherapp.bean.WeatherData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class WeatherServicesRunnable implements Runnable{

    private static final String TAG = "WeatherServiceRunnable";
    private final MainActivity mainActivity;
    private double[] LatLon;
    private String units;
    private static final String UNITF = "°F";
    private static final String UNITC = "°C";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/onecall?lat=41.8675766&lon=-87.616232&appid=d17b2c5f2f30019edb25884df8ffa712&units=imperial&lang=en&exclude=minutely";
    public WeatherServicesRunnable(MainActivity mainActivity,double[] LatLon,String units) {
        this.mainActivity = mainActivity;
        this.LatLon = LatLon;
        this.units = units;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        if(LatLon[0]==0.0 && LatLon[1]==0.0){
            LatLon[0] = 41.8676;
            LatLon[1] = -87.6162;
        }


        String weather = "https://api.openweathermap.org/data/2.5/onecall?lat="+LatLon[0]+"&lon="+LatLon[1]+"&appid=d17b2c5f2f30019edb25884df8ffa712&units="+units+"&lang=en&exclude=minutely";
        Uri uri = Uri.parse(weather);
        String urlToUse = uri.toString();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(urlToUse);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                Log.d(TAG, "run: HTTP ResponseCode NOT OK: " + conn.getResponseCode());
                handleResults(null);
                return;
            }

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }

            Log.d(TAG, "weather data: " + stringBuilder.toString());


        }catch (Exception e){
            Log.e(TAG, "run: ", e);
            handleResults(null);
            return;
        }
        handleResults(stringBuilder.toString());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleResults(String s) {
        if (s == null){
            Log.d(TAG, "handleResults: Failure in data download");
//            mainActivity.runOnUiThread(mainActivity::downloadFailed);
            return;
        }
        final WeatherData weather = parseJSON(s);
        mainActivity.runOnUiThread(() -> {
            if (weather != null) mainActivity.getWeather(weather);
        });
    }
    private String getTemp(double temp) {
        return (long)Math.ceil(temp) + (units.equals("metric") ? UNITC : UNITF);
    }
    private String getVisibility(double visibility) {
        return "Visibility: "+visibility/1000 + (units.equals("metric") ? " km":" mi");
    }
    private String getWindSpeed(double wind) {
        return (long)Math.ceil(wind) + (units.equals("metric") ? " m/s" : " mph");
    }

    private WeatherData parseJSON(String s) {
        try{
            JSONObject jMain = new JSONObject(s);
            String timezone = jMain.getString("timezone");
            String timezoneOffset = jMain.getString("timezone_offset");
            JSONObject jCurrent = jMain.getJSONObject("current");
            JSONObject currentWeather = (JSONObject) jCurrent.getJSONArray("weather").get(0);
            Weather weather = new Weather(currentWeather.getString("id"),
                    currentWeather.getString("main"),
                    currentWeather.getString("description"),
                    currentWeather.getString("icon"));
            Current current = new Current(jCurrent.getString("dt"),
                    jCurrent.getString("sunrise"),
                    jCurrent.getString("sunset"),
                    getTemp(jCurrent.getDouble("temp")),
                    getTemp(jCurrent.getDouble("feels_like")),
                    jCurrent.getString("pressure"),
                    jCurrent.getString("humidity"),
                    jCurrent.getString("uvi"),
                    jCurrent.getString("clouds"),
                    getVisibility(jCurrent.getDouble("visibility")),
                    getWindSpeed(jCurrent.getDouble("wind_speed")),
                    jCurrent.getString("wind_deg"),
                    weather);
            JSONArray hourlyArr = jMain.getJSONArray("hourly");
            ArrayList<Hourly> hourlyArrayList = new ArrayList<>();

            for (int i = 0; i < hourlyArr.length(); i++) {
                JSONObject jHourly = (JSONObject) hourlyArr.get(i);
                JSONObject jWeather = (JSONObject) jHourly.getJSONArray("weather").get(0);
                Weather wea = new Weather(jWeather.getString("id"),
                        jWeather.getString("main"),
                        jWeather.getString("description"),
                        jWeather.getString("icon"));
                hourlyArrayList.add(new Hourly(
                        jHourly.getString("dt"),
                        timezoneOffset,
                        getTemp(jHourly.getDouble("temp")),
                        wea,
                        jHourly.getString("pop")
                ));
            }
            JSONArray dailyArr = jMain.getJSONArray("daily");
            ArrayList<Daily> dailyArrayList = new ArrayList<>();

            for (int i = 0; i < dailyArr.length(); i++) {
                JSONObject jDaily = (JSONObject) dailyArr.get(i);
                JSONObject temp = jDaily.getJSONObject("temp");
                Temperature temperature = new Temperature(getTemp(temp.getDouble("day")),
                        getTemp(temp.getDouble("min")),
                        getTemp(temp.getDouble("max")),
                        getTemp(temp.getDouble("night")),
                        getTemp(temp.getDouble("eve")),
                        getTemp(temp.getDouble("morn")));
                JSONObject jWeather = (JSONObject) jDaily.getJSONArray("weather").get(0);
                Weather wea = new Weather(jWeather.getString("id"),
                        jWeather.getString("main"),
                        jWeather.getString("description"),
                        jWeather.getString("icon"));
                dailyArrayList.add(new Daily(jDaily.getString("dt"),
                        jMain.getDouble("lat"),
                        jMain.getDouble("lon"),
                        timezoneOffset,
                        temperature,
                        wea,
                        jDaily.getString("pop"),
                        jDaily.getString("uvi")));
            }
            return new WeatherData(jMain.getDouble("lat"),
                    jMain.getDouble("lon"),
                    timezone,
                    timezoneOffset,
                    current,
                    hourlyArrayList,
                    dailyArrayList);


        }catch (Exception e){
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
