package com.example.growingmobilef1.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Location implements Serializable {

    private double latitude;
    private double longitude;
    private String locality;
    private String country;

    public Location fromJson(JSONObject json){

        Location tempLoc = new Location();

        if(json.length() != 0){
            try{

                tempLoc.setLatitude(json.getDouble("lat"));
                tempLoc.setLongitude(json.getDouble("long"));
                tempLoc.setLocality(json.getString("locality"));
                tempLoc.setCountry(json.getString("country"));

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return tempLoc;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
