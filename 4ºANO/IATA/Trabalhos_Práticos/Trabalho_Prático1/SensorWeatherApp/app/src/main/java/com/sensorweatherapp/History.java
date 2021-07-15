package com.sensorweatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    DatabaseReference databaseWeather;

    ListView listViewWeather;
    List<OpenWeather> openWeatherList = new ArrayList<>();

    OpenWeatherList adapter;

    SearchView searchView;

    @Override
    protected void onStart() {
        super.onStart();

        databaseWeather.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                openWeatherList.clear();

                for (DataSnapshot openWeatherSnapshot : snapshot.getChildren()) {
                    OpenWeather ow = openWeatherSnapshot.getValue(OpenWeather.class);
                    openWeatherList.add(ow);
                }

                adapter = new OpenWeatherList(History.this, openWeatherList);
                listViewWeather.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        databaseWeather = FirebaseDatabase.getInstance().getReference("weather");
        listViewWeather = findViewById(R.id.listViewWeather);
        searchView = findViewById(R.id.search_bar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                History.this.adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                History.this.adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

}