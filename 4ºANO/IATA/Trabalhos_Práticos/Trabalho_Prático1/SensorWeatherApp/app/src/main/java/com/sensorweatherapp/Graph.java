package com.sensorweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Graph extends AppCompatActivity {
    Spinner spinner;
    DatabaseReference databaseReference;
    List<String> cities;

    private Button b_temp;
    private Button b_tempmin;
    private Button b_tempmax;
    private Button b_hum;
    private Button b_press;
    private Button b_vis;
    private Button b_wind;
    private Button b_uvi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        spinner = findViewById(R.id.spinner);
        cities = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("weather").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dp : snapshot.getChildren()) {
                    String spinnerCity = dp.child("city").getValue(String.class);
                    if (!(cities.contains(spinnerCity))) cities.add(spinnerCity);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Graph.this, android.R.layout.simple_spinner_item, cities);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        b_temp = findViewById(R.id.gtemperature);
        b_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();
                getTempGraph(item);
            }
        });

        b_tempmin = findViewById(R.id.gtemperatureMin);
        b_tempmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();
                getTempMinGraph(item);
            }
        });

        b_tempmax = findViewById(R.id.gtemperatureMax);
        b_tempmax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();
                getTempMaxGraph(item);
            }
        });

        b_hum = findViewById(R.id.ghumidity);
        b_hum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();
                getHumidityGraph(item);
            }
        });

        b_press = findViewById(R.id.gpressure);
        b_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();
                getPressGraph(item);
            }
        });

        b_vis = findViewById(R.id.gvisibility);
        b_vis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();
                getVisGraph(item);
            }
        });

        b_wind = findViewById(R.id.gwindSpeed);
        b_wind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();
                getWindGraph(item);
            }
        });

        b_uvi = findViewById(R.id.guvi);
        b_uvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();
                getUVIGraph(item);
            }
        });
    }

    public void getTempGraph(String item) {
        Intent intent = new Intent(this, TemperatureGraph.class);
        intent.putExtra("city", item);
        startActivity(intent);
    }

    public void getTempMinGraph(String item) {
        Intent intent = new Intent(this, TemperatureMinGraph.class);
        intent.putExtra("city", item);
        startActivity(intent);
    }

    public void getTempMaxGraph(String item) {
        Intent intent = new Intent(this, TemperatureMaxGraph.class);
        intent.putExtra("city", item);
        startActivity(intent);
    }

    public void getHumidityGraph(String item) {
        Intent intent = new Intent(this, HumidityGraph.class);
        intent.putExtra("city", item);
        startActivity(intent);
    }

    public void getPressGraph(String item) {
        Intent intent = new Intent(this, PressureGraph.class);
        intent.putExtra("city", item);
        startActivity(intent);
    }

    public void getVisGraph(String item) {
        Intent intent = new Intent(this, VisibilityGraph.class);
        intent.putExtra("city", item);
        startActivity(intent);
    }


    public void getWindGraph(String item) {
        Intent intent = new Intent(this, WindGraph.class);
        intent.putExtra("city", item);
        startActivity(intent);
    }

    public void getUVIGraph(String item) {
        Intent intent = new Intent(this, UVIGraph.class);
        intent.putExtra("city", item);
        startActivity(intent);
    }
}