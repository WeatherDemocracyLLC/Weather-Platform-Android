package com.webmobrilweatherapp.model;

public class VotinglistModel {

    private int voter_image;
    private String voter_name;
    private String tempeture_in_degree;
    private String speed;
    private String typeofspeed;
    private String voter_location;
    private int cloudy_Image;

    public VotinglistModel(int voter_image, String voter_name, String tempeture_in_degree, String speed, String typeofspeed, String voter_location, int cloudy_Image) {
        this.voter_image = voter_image;
        this.voter_name = voter_name;
        this.tempeture_in_degree = tempeture_in_degree;
        this.speed = speed;
        this.typeofspeed = typeofspeed;
        this.voter_location = voter_location;
        this.cloudy_Image = cloudy_Image;
    }

    public int getVoter_image() {
        return voter_image;
    }

    public void setVoter_image(int voter_image) {
        this.voter_image = voter_image;
    }

    public String getVoter_name() {
        return voter_name;
    }

    public void setVoter_name(String voter_name) {
        this.voter_name = voter_name;
    }

    public String getTempeture_in_degree() {
        return tempeture_in_degree;
    }

    public void setTempeture_in_degree(String tempeture_in_degree) {
        this.tempeture_in_degree = tempeture_in_degree;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getTypeofspeed() {
        return typeofspeed;
    }

    public void setTypeofspeed(String typeofspeed) {
        this.typeofspeed = typeofspeed;
    }

    public String getVoter_location() {
        return voter_location;
    }

    public void setVoter_location(String voter_location) {
        this.voter_location = voter_location;
    }

    public int getCloudy_Image() {
        return cloudy_Image;
    }

    public void setCloudy_Image(int cloudy_Image) {
        this.cloudy_Image = cloudy_Image;
    }
}
