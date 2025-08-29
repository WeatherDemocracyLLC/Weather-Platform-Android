package com.webmobrilweatherapp.model;

public class WeatherMayorModel {

    private int image;
    private String image_name;
    private String name2;
    private String location_name;
    private String month_name;

    public WeatherMayorModel(int image, String image_name, String name2, String location_name, String month_name) {
        this.image = image;
        this.image_name = image_name;
        this.name2 = name2;
        this.location_name = location_name;
        this.month_name = month_name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }
}
