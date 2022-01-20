package com.saurabh.openweatherapp;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.saurabh.openweatherapp.Services.Services;
import com.saurabh.openweatherapp.bean.Hourly;
import com.saurabh.openweatherapp.bean.WeatherData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {

    List<Hourly> list;
    MainActivity mainActivity;
    DateTimeFormatter sun = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault());
    DateTimeFormatter def = DateTimeFormatter.ofPattern("EEE", Locale.getDefault());

    String formatDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd"));

    public HourlyWeatherAdapter(List<Hourly> list, MainActivity mainActivity) {
        this.list = list;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_weather, parent, false);
        view.setOnClickListener(mainActivity);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String icon ="_"+list.get(position).getWeather().getIcon();
        int iconResId = mainActivity.getResources().getIdentifier(icon,
               "drawable", mainActivity.getPackageName());
        String df = list.get(position).getDt();
        String timzoneOffset = list.get(position).getTimezone_offset();

        if(mainActivity.dateTime(df,timzoneOffset,DateTimeFormatter.ofPattern("dd", Locale.getDefault())).equals(formatDateTime)){
            holder.day.setText("Today");
        }else {
            holder.day.setText(mainActivity.dateTime(df,timzoneOffset,def));
        }
        holder.time.setText(mainActivity.dateTime(df,timzoneOffset,sun));
        holder.description.setText(list.get(position).getWeather().getDescription());
        holder.imageView.setImageResource(iconResId);
        holder.temp.setText(list.get(position).getTemp());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day,time,temp,description;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
            temp = itemView.findViewById(R.id.hourly_temp);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
