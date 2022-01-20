package com.saurabh.openweatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.saurabh.openweatherapp.Services.Services;
import com.saurabh.openweatherapp.bean.Daily;
import com.saurabh.openweatherapp.bean.Hourly;

import com.saurabh.openweatherapp.bean.WeatherData;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView location,date_time,temp,feels_like,weather_description,wind_description;
    TextView humidity,uv_index,visibility;
    TextView morning_temp,daytime_temp,evening_temp,night_temp;
    TextView sunrise,sunset;
    ImageView imageView;
    SwipeRefreshLayout swipeRefresh;


    HourlyWeatherAdapter adapter;
    RecyclerView recyclerView;
    List<Hourly> list = new ArrayList<>();
    List<Daily> dailyList = new ArrayList<>();
    Services services = new Services();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
    DateTimeFormatter sun = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault());
    double[] LatLon={0,0};
    String units="imperial";
    int flag =0;
    Bundle savedInstance;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        savedInstance= savedInstanceState;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefresh.setRefreshing(false);
            }
        });
        services.setMainActivity(this);
        if(hasNetworkConnection()==false){
            setContentView(R.layout.networkcheck);
            flag=1;
            Toast();
        }else {

            setText();
            WeatherServicesRunnable weatherServicesRunnable = new WeatherServicesRunnable(this, LatLon, units);
            new Thread(weatherServicesRunnable).start();
        }

    }
//    public void weatherService(){
//        WeatherServicesRunnable weatherServicesRunnable = new WeatherServicesRunnable(this, LatLon, units);
//        new Thread(weatherServicesRunnable).start();
//        if (swipeRefresh.isRefreshing()) {
//            swipeRefresh.setRefreshing(false);
//        }
//    }
//    public void Refrash(){
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                if(hasNetworkConnection()==false){
//
//                    setContentView(R.layout.networkcheck);
//                    flag=1;
//                    Toast();
//
//                }else{
//                    flag=0;
//                    setContentView(R.layout.activity_main);
//                    setText();
//                    weatherService();
//
//                }
//
//                swipeRefresh.setRefreshing(false);
//            }
//        });
//    }
    public void setText(){
        location = findViewById(R.id.location);
        date_time = findViewById(R.id.date_time);
        temp = findViewById(R.id.temperature);
        feels_like = findViewById(R.id.feels_like);
        weather_description = findViewById(R.id.weather_description);
        wind_description = findViewById(R.id.winds_description);
        humidity = findViewById(R.id.humidity);
        uv_index = findViewById(R.id.uv_index);
        visibility = findViewById(R.id.visibility);
        morning_temp = findViewById(R.id.morning_temp);
        daytime_temp = findViewById(R.id.daytime_temp);
        evening_temp = findViewById(R.id.evening_temp);
        night_temp = findViewById(R.id.night_temp);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        recyclerView = findViewById(R.id.recyle);
        imageView = findViewById(R.id.imageView2);
        swipeRefresh = findViewById(R.id.swipeRefresh);
    }

    public void Toast(){
        Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String dateTime(String dt, String offset,DateTimeFormatter dtf ){
        return LocalDateTime.ofEpochSecond(Long.parseLong(dt) + Long.parseLong(offset), 0, ZoneOffset.UTC).format(dtf);
    }

    public int getIcon(String icon){
        return this.getResources().getIdentifier("_"+icon,
                "drawable", this.getPackageName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.calender:
                if (hasNetworkConnection()==false){
                    setContentView(R.layout.networkcheck);
                    flag=1;
                    Toast();

                    return true;
                }
                else if(flag==1) {
                    flag=0;
                    setContentView(R.layout.activity_main);
                    setText();
                }
                Intent intent = new Intent(this, DailyForecast.class);
                Bundle bundle = new Bundle();
                Log.d("MainActivity","list"+dailyList);
                bundle.putSerializable("List", (Serializable)dailyList);
                intent.putExtra("List",bundle);
                startActivity(intent);
                return true;
            case R.id.location:
                if (hasNetworkConnection()==false){
                    setContentView(R.layout.networkcheck);
                    flag=1;
                    Toast();
                    return true;
                }
                else if(flag==1) {
                    setContentView(R.layout.activity_main);
                    setText();
                    flag=0;
                }
                final EditText txtUrl = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("Enter a Location")
                        .setMessage("For US locations, enter as 'City',or 'City,State'.\n"+"For international locations enter as 'City,Country'\n")
                        .setView(txtUrl)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                location(txtUrl.getText().toString());
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;


            case R.id.unit:
                if (hasNetworkConnection()==false){
                    setContentView(R.layout.networkcheck);
                    flag=1;
                    Toast();

                    return true;
                }
                else if(flag==1) {
                    flag=0;
                    setContentView(R.layout.activity_main);
                    setText();

                }

                if(units=="imperial"){
                    units="metric";
                    item.setIcon(R.drawable.units_c);

                }else{
                    units="imperial";
                    item.setIcon(R.drawable.units_f);
                }
                WeatherServicesRunnable weatherServicesRunnable = new WeatherServicesRunnable(this,LatLon,units);
                new Thread(weatherServicesRunnable).start();

                return true;



        }
        return super.onOptionsItemSelected(item);
    }
    public String convert(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void getWeather(WeatherData weather) {
        Log.d("Main",""+weather.getDaily().get(0).getTemperature().getMorn());
        location.setText(getLocation(weather.getLat(),weather.getLon()));
        date_time.setText(dateTime(weather.getCurrent().getDt(),weather.getTimezone_offset(),dtf));
        temp.setText(weather.getCurrent().getTemp());
        feels_like.setText("Feels Like "+weather.getCurrent().getFeel_like());
        weather_description.setText(convert(weather.getCurrent().getWeather().getDescription()+" ( "+weather.getCurrent().getClouds()+"% "+
                weather.getCurrent().getWeather().getMain()+" )"));
        wind_description.setText("Winds : "+services.getWindDirection(Double.parseDouble(weather.getCurrent().getWind_deg()))+" at "+
                weather.getCurrent().getWind_speed());
        humidity.setText("Humidity: "+weather.getCurrent().getHumidity()+"%");
        uv_index.setText("UV Index: "+services.convertCeil(weather.getCurrent().getUvi()));
        visibility.setText(weather.getCurrent().getVisibility());

        morning_temp.setText(weather.getDaily().get(0).getTemperature().getMorn());
        daytime_temp.setText(weather.getDaily().get(0).getTemperature().getDay());
        evening_temp.setText(weather.getDaily().get(0).getTemperature().getEve());
        night_temp.setText(weather.getDaily().get(0).getTemperature().getNight());
        sunrise.setText("Sunrise :"+dateTime(weather.getCurrent().getSunrise(),weather.getTimezone_offset(),sun));
        sunset.setText("Sunset:"+dateTime(weather.getCurrent().getSunset(),weather.getTimezone_offset(),sun));
        imageView.setImageResource(getIcon(weather.getCurrent().getWeather().getIcon()));

        list = weather.getHourly();
        dailyList=weather.getDaily();
        adapter = new HourlyWeatherAdapter(list,this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


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
    private double[] getLatLon(String userProvidedLocation) {
        Geocoder geocoder = new Geocoder(this); // Here, “this” is an Activity
        try {
            List<Address> address =
                    geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) {
                // Nothing returned!
                return null;
            }
            return new double[] {address.get(0).getLatitude(), address.get(0).getLongitude()};
        } catch (IOException e) {
            // Failure to get an Address object
            return null;
        }
    }
    public void location(String url){
        LatLon = getLatLon(url);
        if(LatLon==null){
            Toast.makeText(this,"Enter Valid input",Toast.LENGTH_SHORT).show();
        }else{
            WeatherServicesRunnable weatherServicesRunnable= new WeatherServicesRunnable(this,LatLon,units);
            new Thread(weatherServicesRunnable).start();

        }
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        i.setComponent(new ComponentName("com.google.android.calendar", "com.android.calendar.LaunchActivity"));
        startActivity(i);

    }
}
