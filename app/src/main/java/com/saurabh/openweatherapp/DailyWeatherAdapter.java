package com.saurabh.openweatherapp;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.saurabh.openweatherapp.Services.Services;

import com.saurabh.openweatherapp.bean.Daily;
import com.saurabh.openweatherapp.bean.WeatherData;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {
    List<Daily> list;
    DailyForecast dailyForecast;
    public final static String f="°F";

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE,MM/dd ", Locale.getDefault());

    public DailyWeatherAdapter(List<Daily> list, DailyForecast dailyForecast) {
        this.list = list;
        this.dailyForecast = dailyForecast;
    }

    @NonNull
    @Override
    public DailyWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyWeatherAdapter.ViewHolder holder, int position) {
        Services services =new Services();
        holder.daydate.setText(dateTime(list.get(position).getDt(),list.get(position).getTimezone_offset(),dtf));
        holder.tempminmax.setText(list.get(position).getTemperature().getMax()+
                "/"+list.get(position).getTemperature().getMin());
        holder.descriptions.setText(list.get(position).getWeather().getDescription());
        holder.pop.setText("("+list.get(position).getPop()+"% precip.)");
        holder.uvIndex.setText("UV Index: "+services.convertCeil(list.get(position).getUvi()));
        holder.am8.setText(list.get(position).getTemperature().getMorn());
        holder.pm1.setText(list.get(position).getTemperature().getDay());
        holder.pm5.setText(list.get(position).getTemperature().getEve());
        holder.pm11.setText(list.get(position).getTemperature().getNight());

        String icon ="_"+list.get(position).getWeather().getIcon();
        int iconResId = dailyForecast.getResources().getIdentifier(icon,
                "drawable", dailyForecast.getPackageName());
        holder.imageView.setImageResource(iconResId);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView daydate,tempminmax,descriptions,pop,uvIndex,am8,pm1,pm5,pm11;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            daydate = itemView.findViewById(R.id.daydate);
            tempminmax = itemView.findViewById(R.id.tempminmax);
            descriptions = itemView.findViewById(R.id.descriptions);
            pop = itemView.findViewById(R.id.pop);
            uvIndex = itemView.findViewById(R.id.uvi);
            am8 = itemView.findViewById(R.id.am8);
            pm1 = itemView.findViewById(R.id.pm1);
            pm5 = itemView.findViewById(R.id.pm5);
            pm11 = itemView.findViewById(R.id.pm11);
            imageView = itemView.findViewById(R.id.imageView3);

        }
    }

    public String dateTime(String dt, String offset,DateTimeFormatter dtf ){
        return LocalDateTime.ofEpochSecond(Long.parseLong(dt) + Long.parseLong(offset), 0, ZoneOffset.UTC).format(dtf);
    }
}
