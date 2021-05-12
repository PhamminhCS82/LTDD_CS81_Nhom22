package com.example.weatherforecast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherforecast.R;
import com.example.weatherforecast.common.Common;
import com.example.weatherforecast.model.WeatherForecastResponse;
import com.squareup.picasso.Picasso;

public class DailyWeatherAdapter extends BaseAdapter {
    Context context;
    WeatherForecastResponse weatherForecastResponse;
    public DailyWeatherAdapter(Context context, WeatherForecastResponse weatherForecastResponse){
        this.context = context;
        this.weatherForecastResponse = weatherForecastResponse;
    }
    @Override
    public int getCount() {
        return weatherForecastResponse.getDaily().size();
    }

    @Override
    public Object getItem(int position) {
        return weatherForecastResponse.getDaily().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.item_weather_daily, null);
        TextView day = convertView.findViewById(R.id.tv_day_of_week);
        ImageView img = convertView.findViewById(R.id.icon_weather_of_day);
        TextView tempMax = convertView.findViewById(R.id.tv_temp_of_day_max);
        TextView tempMin = convertView.findViewById(R.id.tv_temp_of_day_min);
        TextView tvWeatherInfo = convertView.findViewById(R.id.tv_weather_info);

        int tMax = (int) Math.round(weatherForecastResponse.getDaily().get(position).temp.getMax());
        int tMin = (int) Math.round(weatherForecastResponse.getDaily().get(position).temp.getMin());
        day.setText(Common.convertUnixToDayOfWeek(weatherForecastResponse.getDaily().get(position).dt));
        tvWeatherInfo.setText(weatherForecastResponse.getDaily().get(position).weather.get(0).getDescription());
        tempMax.setText(tMax + "°C");
        tempMin.setText(tMin + "°C");
        Picasso.get().load("http://openweathermap.org/img/wn/" +
                weatherForecastResponse.getDaily().get(position).weather.get(0).getIcon() +
                "@2x.png").into(img);
        return convertView;
    }
}
