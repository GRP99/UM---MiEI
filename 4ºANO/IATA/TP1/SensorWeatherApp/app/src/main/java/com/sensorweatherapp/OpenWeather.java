package com.sensorweatherapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class OpenWeather {
    private String city;
    private String description;
    private Double temperature;
    private Double temperatureMin;
    private Double temperatureMax;
    private Double pressure;
    private int humidity;
    private int visibility;
    private int windSpeed;
    private Double uvi;
    private Long ldt;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public OpenWeather() {
        city = "";
        description = "";
        temperature = 0.0;
        temperatureMin = 0.0;
        temperatureMax = 0.0;
        pressure = 0.0;
        visibility = 0;
        windSpeed = 0;
        uvi = 0.0;
        ldt = Long.valueOf(0);
    }

    public OpenWeather(OpenWeather ow) {
        this.city = ow.getCity();
        this.description = ow.getDescription();
        this.temperature = ow.getTemperature();
        this.temperatureMin = ow.getTemperatureMin();
        this.temperatureMax = ow.getTemperatureMax();
        this.pressure = ow.getPressure();
        this.humidity = ow.getHumidity();
        this.visibility = ow.getVisibility();
        this.windSpeed = ow.getWindSpeed();
        this.uvi = ow.getUvi();
        this.ldt = ow.getLdt();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getUvi() {
        return uvi;
    }

    public void setUvi(Double uvi) {
        this.uvi = uvi;
    }

    public Long getLdt() {
        return ldt;
    }

    public void setLdt(Long ldt) {
        this.ldt = ldt;
    }

    @Override
    public String toString() {
        if (visibility == 0) {
            return " Description = " + description +
                    "\n Temperature = " + temperature + " °C" +
                    "\n TemperatureMin = " + temperatureMin + " °C" +
                    "\n TemperatureMax = " + temperatureMax + " °C" +
                    "\n Pressure = " + pressure + " hPa" +
                    "\n Humidity = " + humidity + "%" +
                    "\n WindSpeed = " + windSpeed + " km/h" +
                    "\n UVI = " + uvi;
        }
        return " Description = " + description +
                "\n Temperature = " + temperature + " °C" +
                "\n TemperatureMin = " + temperatureMin + " °C" +
                "\n TemperatureMax = " + temperatureMax + " °C" +
                "\n Pressure = " + pressure + " hPa" +
                "\n Humidity = " + humidity + "%" +
                "\n Visibility = " + visibility + " km" +
                "\n WindSpeed = " + windSpeed + " km/h" +
                "\n UVI = " + uvi;
    }
}
