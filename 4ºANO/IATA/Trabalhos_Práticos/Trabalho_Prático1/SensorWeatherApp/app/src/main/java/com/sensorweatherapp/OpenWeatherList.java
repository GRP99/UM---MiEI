package com.sensorweatherapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpenWeatherList extends ArrayAdapter<OpenWeather> implements Filterable {
    private final Activity context;
    private List<OpenWeather> originalopenWeatherList;
    private List<OpenWeather> tempopenWeatherList;
    private CustomFilter cs;

    public OpenWeatherList(Activity context, List<OpenWeather> originalopenWeatherList) {
        super(context, R.layout.list_layout, originalopenWeatherList);
        this.context = context;
        this.originalopenWeatherList = originalopenWeatherList;
        this.tempopenWeatherList = originalopenWeatherList;
    }

    @Nullable
    @Override
    public OpenWeather getItem(int position) {
        return originalopenWeatherList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewCity = listViewItem.findViewById(R.id.textViewCity);
        TextView textViewDate = listViewItem.findViewById(R.id.textViewDate);
        TextView textViewWeather = listViewItem.findViewById(R.id.textViewWeather);

        OpenWeather ow = originalopenWeatherList.get(position);

        textViewCity.setText(ow.getCity());
        Date d = new Date(ow.getLdt() * 1000);
        textViewDate.setText(d.toString());
        textViewWeather.setText(ow.toString());

        return listViewItem;
    }

    @Override
    public int getCount() {
        return originalopenWeatherList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (cs == null) {
            cs = new CustomFilter();
        }
        return cs;
    }

    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toLowerCase();
                List<OpenWeather> filters = new ArrayList<>();
                for (int i = 0; i < tempopenWeatherList.size(); i++) {
                    if (tempopenWeatherList.get(i).getCity().toLowerCase().contains(constraint)) {
                        OpenWeather singleweather = new OpenWeather(tempopenWeatherList.get(i));
                        filters.add(singleweather);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = tempopenWeatherList.size();
                results.values = tempopenWeatherList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            originalopenWeatherList = (List<OpenWeather>) filterResults.values;
            notifyDataSetChanged();

        }
    }
}

