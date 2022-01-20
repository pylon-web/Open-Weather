package com.saurabh.openweatherapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.saurabh.openweatherapp.Services.Services;
import com.saurabh.openweatherapp.bean.Daily;
import com.saurabh.openweatherapp.bean.WeatherData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DailyForecast extends AppCompatActivity {

    RecyclerView recyclerView;
    DailyWeatherAdapter dailyWeatherAdapter;
    List<Daily> list;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        recyclerView = findViewById(R.id.recycler);

        Bundle bundle = getIntent().getBundleExtra("List");
        list = (ArrayList<Daily>)bundle.getSerializable("List");
        setTitle(getLocation(list.get(0).getTimezone(),list.get(0).getLon()));

        dailyWeatherAdapter = new DailyWeatherAdapter(list,this);
        recyclerView.setAdapter(dailyWeatherAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }

    public String getLocation(double loc, double lon) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> address = geocoder.getFromLocation(loc, lon, 1);
            if (address == null || address.isEmpty()) {
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

            return p1 + ", " + p2;
        } catch (IOException e) {
            return null;
        }
    }
}