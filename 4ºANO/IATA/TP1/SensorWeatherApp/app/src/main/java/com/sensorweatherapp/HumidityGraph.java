package com.sensorweatherapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.Date;

public class HumidityGraph extends AppCompatActivity {
    DatabaseReference reference;
    String city;
    GraphView graphView;
    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humiditygraph);

        Intent intent = getIntent();
        city = intent.getExtras().getString("city");

        reference = FirebaseDatabase.getInstance().getReference("weather");

        this.graphView = findViewById(R.id.graph);
        this.graphView.getViewport().setScalable(true);
        this.graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        this.series = new LineGraphSeries<>();
        this.series.setColor(Color.BLUE);
        this.series.setDrawDataPoints(true);
        this.series.setDataPointsRadius(10);
        this.series.setThickness(8);
        this.series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Date d = new Date((long) (dataPoint.getX() * 1000));
                String msg = "Timestamp:" + d.toString() + ";\nValue: " + dataPoint.getY();
                Toast.makeText(HumidityGraph.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        this.graphView.addSeries(this.series);

        graphView.getViewport().setMinX(series.getLowestValueX());
        graphView.getViewport().setMaxX(series.getHighestValueX());
        graphView.getViewport().setXAxisBoundsManual(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int tot = 0;
                for (DataSnapshot openWeatherSnapshot : snapshot.getChildren()) {
                    OpenWeather ow = openWeatherSnapshot.getValue(OpenWeather.class);
                    if (ow.getCity().equals(city)) {
                        tot++;
                    }
                }
                DataPoint[] dp = new DataPoint[tot];
                int index = 0;
                for (DataSnapshot openWeatherSnapshot : snapshot.getChildren()) {
                    OpenWeather ow = openWeatherSnapshot.getValue(OpenWeather.class);
                    if (ow.getCity().equals(city)) {
                        dp[index] = new DataPoint(ow.getLdt(), ow.getHumidity());
                        index++;
                    }
                }
                series.resetData(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}