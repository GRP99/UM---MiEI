package com.sensorweatherapp;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class Weather extends AsyncTask<String, Void, String> { //First String means URL is in String, Void mean nothing, Third String means Return type will be String
    @Override
    protected String doInBackground(String... address) { //String... means multiple address can be send. It acts as array
        try {
            URL url = new URL(address[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Establish connection with address
            connection.connect();

            //retrieve data from url
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            //Retrieve data and return it as String
            int data = isr.read();
            String content = "";
            char ch;
            while (data != -1) {
                ch = (char) data;
                content = content + ch;
                data = isr.read();
            }
            return content;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}


public class CurrentWeather extends AppCompatActivity {
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

        if (ow.getTemperature() >= 27 && ow.getTemperature() < 32) {
            //yellow
            AlertCautionHeat exampleDialog = new AlertCautionHeat();
            exampleDialog.show(getSupportFragmentManager(), "alert caution heat");
        }
        if (ow.getTemperature() >= 32 && ow.getTemperature() < 41) {
            //orange
            AlertExtremeCautionHeat exampleDialog = new AlertExtremeCautionHeat();
            exampleDialog.show(getSupportFragmentManager(), "alert extreme caution heat");
        }
        if (ow.getTemperature() >= 41 && ow.getTemperature() < 54) {
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

    private void saveData(OpenWeather ow) {
        this.databaseWeather = FirebaseDatabase.getInstance().getReference("weather");

        String id = this.databaseWeather.push().getKey();

        this.databaseWeather.child(id).setValue(ow);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private OpenWeather getCurrent(String cName) {
        Weather weather = new Weather();
        Weather onecall = new Weather();

        String content_weather = null;
        String content_onecall = null;

        OpenWeather openWeather = new OpenWeather();

        try {
            content_weather = weather.execute("https://api.openweathermap.org/data/2.5/weather?q=" + cName + "&appid=ba1dda85409027c5ad13cdbb212451e4&units=metric").get();
            //Log the data
            Log.i("contentWeather", content_weather);

            //JSON
            JSONObject jsonObject = new JSONObject(content_weather);
            Long dt = jsonObject.getLong("dt");
            String coordenates = jsonObject.getString("coord");
            String weatherData = jsonObject.getString("weather");
            String mainData = jsonObject.getString("main");

            String longitude = "";
            String latitude = "";

            JSONObject coordPart = new JSONObject(coordenates);
            longitude = coordPart.getString("lon");
            latitude = coordPart.getString("lat");

            String description = "";

            //weather data is in Array
            JSONArray array = new JSONArray(weatherData);
            for (int i = 0; i < array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                description = weatherPart.getString("description");
            }

            Double temperature = 0.0;
            Double temperatureMin = 0.0;
            Double temperatureMax = 0.0;
            Double pressure = 0.0;
            int humidity = 0;

            JSONObject mainPart = new JSONObject(mainData);
            temperature = Double.parseDouble(mainPart.getString("temp"));
            temperatureMin = Double.parseDouble(mainPart.getString("temp_min"));
            temperatureMax = Double.parseDouble(mainPart.getString("temp_max"));
            pressure = Double.parseDouble(mainPart.getString("pressure"));
            humidity = Integer.parseInt(mainPart.getString("humidity"));


            double visibility;
            visibility = Double.parseDouble(jsonObject.getString("visibility"));
            //By default visibility is in meter
            int visibiltyInKilometer = (int) (visibility / 1000);

            content_onecall = onecall.execute("https://api.openweathermap.org/data/2.5/onecall?lat=" + latitude + "&lon=" + longitude + "&exclude=minutely,hourly,daily&appid=ba1dda85409027c5ad13cdbb212451e4&units=metric").get();
            Log.i("contentData2", content_onecall);

            JSONObject jsonObj = new JSONObject(content_onecall);

            Double windSpeed = 0.0;
            Double uvi = 0.0;

            String current = jsonObj.getString("current");
            JSONObject currentPart = new JSONObject(current);

            windSpeed = Double.parseDouble(currentPart.getString("wind_speed"));
            int windSpeedInKilometer = (int) (windSpeed * 3.6);
            uvi = Double.parseDouble(currentPart.getString("uvi"));

            openWeather.setCity(cName.toLowerCase());
            openWeather.setDescription(description);
            openWeather.setTemperature(temperature);
            openWeather.setTemperatureMin(temperatureMin);
            openWeather.setTemperatureMax(temperatureMax);
            openWeather.setHumidity(humidity);
            openWeather.setPressure(pressure);
            openWeather.setVisibility(visibiltyInKilometer);
            openWeather.setWindSpeed(windSpeedInKilometer);
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
            saveData(weather);

            this.result.setText(weather.toString());

            checkConditions(weather);
        } else {
            this.result.setText("Nonexistent city, try again...");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);
    }
}