package com.example.growingmobilef1.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverStandings {

    private int position;
    private String positionText;
    private int points;
    private int wins;
    private Driver Driver;
    private Constructor Constructor;

    public static DriverStandings fromJson(JSONObject json){

        DriverStandings tempDrivStand = new DriverStandings();
        Driver tempDriver = new Driver();
        Constructor tempCons = new Constructor();

        if(json.length() != 0){
            try{

                tempDrivStand.setPosition(json.getInt("position"));
                tempDrivStand.setPositionText(json.getString("positionText"));
                tempDrivStand.setPoints(json.getInt("points"));
                tempDrivStand.setWins(json.getInt("wins"));

                tempDrivStand.setDriver(tempDriver.fromJson(json.getJSONObject("Driver")));
                tempDrivStand.setConstructor(tempCons.fromJson(json.optJSONObject("Constructor")));

            }catch (JSONException e){
                e.printStackTrace();
            }

        }

        return tempDrivStand;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPositionText() {
        return positionText;
    }

    public void setPositionText(String positionText) {
        this.positionText = positionText;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public Driver getDriver() {
        return Driver;
    }

    public void setDriver(com.example.growingmobilef1.Model.Driver driver) {
        Driver = driver;
    }

    public com.example.growingmobilef1.Model.Constructor getConstructor() {
        return Constructor;
    }

    public void setConstructor(com.example.growingmobilef1.Model.Constructor constructor) {
        Constructor = constructor;
    }
}
