package com.example.weatherforecast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;
import com.example.weatherforecast.common.Common;
import com.example.weatherforecast.model.WeatherForecastResponse;
import com.squareup.picasso.Picasso;



public class  WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    Context context;
    WeatherForecastResponse weatherForecastResponse;
    public  WeatherForecastAdapter(Context context, WeatherForecastResponse weatherForecastResponse){
        this.context = context;
        this.weatherForecastResponse = weatherForecastResponse;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_hourly, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load("http://openweathermap.org/img/wn/" +
                weatherForecastResponse.getHourly().get(position).getWeather().get(0).getIcon() +
                "@2x.png").into(holder.imgIcon);


        if(position == 0) {holder.txtHourly.setText("Now");}
        else {
        holder.txtHourly.setText(Common.convertUnixToHour(weatherForecastResponse.getHourly().get(position).getDt()));}
        int t = (int) Math.round(weatherForecastResponse.getHourly().get(position).getTemp());
        String tempString = t + "°C";
        holder.txtTemp.setText(tempString);
    }

    @Override
    public int getItemCount() {
        return weatherForecastResponse.getHourly().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtHourly, txtTemp;
        ImageView imgIcon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_icon);
            txtHourly = itemView.findViewById(R.id.txt_hour);
            txtTemp = itemView.findViewById(R.id.txt_temp);
        }
    }
}
