package com.sensorweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {
    private Button b_current;
    private Button b_forescast;
    private Button b_history;
    private Button b_graph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_current = findViewById(R.id.currentWeather);
        b_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentWeather();
            }
        });

        b_forescast = findViewById(R.id.forecastWeather);
        b_forescast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecastWeather();
            }
        });

        b_history = findViewById(R.id.history);
        b_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHistory();
            }
        });

        b_graph = findViewById(R.id.graph);
        b_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGraph();
            }
        });
    }

    public void getCurrentWeather() {
        Intent intent = new Intent(this, CurrentWeather.class);

        startActivity(intent);
    }

    public void getForecastWeather() {
        Intent intent = new Intent(this, ForecastWeather.class);

        startActivity(intent);
    }

    public void getHistory() {
        Intent intent = new Intent(this, History.class);

        startActivity(intent);
    }

    public void getGraph() {
        Intent intent = new Intent(this, Graph.class);

        startActivity(intent);
    }
}