package com.sensorweatherapp;


import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONObject;

public class ForecastWeather extends AppCompatActivity {
    TextView cityName;
    Button searchButton;
    TextView result;

    DatabaseReference databaseWeather;


    private void checkConditions(OpenWeather ow) {
        if (ow.getUvi() >= 3.0 && ow.getUvi() <= 5.9) {
            //yellow
            AlertModerateUV exampleDialog = new AlertModerateUV();
            exampleDialog.show(getSupportFragmentManager(), "alert moderate UV");
        }
        if (ow.getUvi() >= 6.0 && ow.getUvi() <= 7.9) {
            //orange
            AlertHighUV exampleDialog = new AlertHighUV();
            exampleDialog.show(getSupportFragmentManager(), "alert high UV");
        }
        if (ow.getUvi() >= 8.0 && ow.getUvi() <= 10.9) {
            //red
            AlertVeryHighUV exampleDialog = new AlertVeryHighUV();
            exampleDialog.show(getSupportFragmentManager(), "alert very high UV");
        }
        if (ow.getUvi() >= 11.0) {
            //violet
            AlertExtremeUV exampleDialog = new AlertExtremeUV();
            exampleDialog.show(getSupportFragmentManager(), "alert extreme UV");
        }
        if (ow.getWindSpeed() >= 70 && ow.getWindSpeed() <= 90) {
            //yellow
            AlertYellowWind exampleDialog = new AlertYellowWind();
            exampleDialog.show(getSupportFragmentManager(), "yellow alert Wind");
        }
        if (ow.getWindSpeed() >= 91 && ow.getWindSpeed() <= 130) {
            //orange
            AlertOrangeWind exampleDialog = new AlertOrangeWind();
            exampleDialog.show(getSupportFragmentManager(), "orange alert Wind");
        }
        if (ow.getWindSpeed() >= 131) {
            //red
            AlertRedWind exampleDialog = new AlertRedWind();
            exampleDialog.show(getSupportFragmentManager(), "red alert Wind");
        }
        if (ow.getTemperature() >= 27 && ow.getTemperature() <= 32) {
            //yellow
            AlertCautionHeat exampleDialog = new AlertCautionHeat();
            exampleDialog.show(getSupportFragmentManager(), "alert caution heat");
        }
        if (ow.getTemperature() >= 32 && ow.getTemperature() <= 41) {
            //orange
            AlertExtremeCautionHeat exampleDialog = new AlertExtremeCautionHeat();
            exampleDialog.show(getSupportFragmentManager(), "alert extreme caution heat");
        }
        if (ow.getTemperature() >= 41 && ow.getTemperature() <= 54) {
            //red
            AlertDangerHeat exampleDialog = new AlertDangerHeat();
            exampleDialog.show(getSupportFragmentManager(), "alert danger heat");
        }
        if (ow.getTemperature() >= 54) {
            //violet
            AlertExtremeDangerHeat exampleDialog = new AlertExtremeDangerHeat();
            exampleDialog.show(getSupportFragmentManager(), "alert extreme danger heat");
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public OpenWeather getCurrent(String cName) {
        Weather weather = new Weather();
        Weather onecall = new Weather();

        String content_weather = null;
        String content_onecall = null;

        OpenWeather openWeather = new OpenWeather();

        try {
            content_weather = weather.execute("https://api.openweathermap.org/data/2.5/weather?q=" + cName + "&appid=ba1dda85409027c5ad13cdbb212451e4&units=metric").get();

            //JSON
            JSONObject jsonObject = new JSONObject(content_weather);
            String coordenates = jsonObject.getString("coord");

            String longitude = "";
            String latitude = "";

            JSONObject coordPart = new JSONObject(coordenates);
            longitude = coordPart.getString("lon");
            latitude = coordPart.getString("lat");

            content_onecall = onecall.execute("https://api.openweathermap.org/data/2.5/onecall?lat=" + latitude + "&lon=" + longitude + "&exclude=current,minutely,hourly&appid=ba1dda85409027c5ad13cdbb212451e4&units=metric").get();

            JSONObject jsonObj = new JSONObject(content_onecall);

            String nextday = jsonObj.getString("daily");
            JSONArray array = new JSONArray(nextday);
            JSONObject weatherPart = array.getJSONObject(1);

            Long dt = weatherPart.getLong("dt");
            String tempData = weatherPart.getString("temp");
            JSONObject tempPart = new JSONObject(tempData);
            Double temperature = Double.parseDouble(tempPart.getString("day"));
            Double temperatureMin = Double.parseDouble(tempPart.getString("min"));
            Double temperatureMax = Double.parseDouble(tempPart.getString("max"));
            Double pressure = Double.parseDouble(weatherPart.getString("pressure"));
            int humidity = Integer.parseInt(weatherPart.getString("humidity"));
            double windSpeed = Double.parseDouble(weatherPart.getString("wind_speed"));
            int windSpeedInKm = (int) ((int) windSpeed * 3.6);
            Double uvi = Double.parseDouble(weatherPart.getString("uvi"));
            //Double visibility = 0.0;
            //Double.parseDouble(weatherPart.getString("visibility"));
            //int visibiltyInKilometer = (int) (visibility / 1000);
            String descData = weatherPart.getString("weather");
            JSONArray arrayweather = new JSONArray(descData);
            JSONObject arrayweatherPart = arrayweather.getJSONObject(0);
            String description = arrayweatherPart.getString("description");

            openWeather.setCity(cName);
            openWeather.setDescription(description);
            openWeather.setTemperature(temperature);
            openWeather.setTemperatureMin(temperatureMin);
            openWeather.setTemperatureMax(temperatureMax);
            openWeather.setHumidity(humidity);
            openWeather.setPressure(pressure);
            //openWeather.setVisibility(visibiltyInKilometer);
            openWeather.setWindSpeed(windSpeedInKm);
            openWeather.setUvi(uvi);
            openWeather.setLdt(dt);

            return openWeather;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return openWeather;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void search(View view) {
        this.cityName = findViewById(R.id.cityName);
        this.searchButton = findViewById(R.id.searchButton);
        this.result = findViewById(R.id.result);

        String cName = this.cityName.getText().toString();

        OpenWeather weather = new OpenWeather(getCurrent(cName));

        if (!(cName.equals("") || weather.getDescription().equals(""))) {
            this.result.setText(weather.toString());

            checkConditions(weather);
        } else {
            this.result.setText("Nonexistent city, try again...");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecastweather);
    }
}