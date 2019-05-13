package com.example.growingmobilef1.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class RaceResultsItem {

    private int number;
    private int position;
    private String positionText;
    private int points;

    private Driver Driver;

    private int grid;
    private int laps;
    private String status;

    public RaceResultsItem(){

    }

    public static RaceResultsItem fromJson(JSONObject json){

        Driver tempDriver = new Driver();
        RaceResultsItem temp = new RaceResultsItem();

        if(json.length() != 0){
            try{

                temp.setNumber(json.getInt("number"));
                temp.setPosition(json.getInt("position"));
                temp.setPositionText(json.getString("positionText"));
                temp.setPoints(json.getInt("points"));
                temp.setDriver(tempDriver.fromJson(json.getJSONObject("Driver")));

                temp.setGrid(json.getInt("grid"));
                temp.setLaps(json.getInt("laps"));
                temp.setStatus(json.getString("status"));


            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return temp;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public com.example.growingmobilef1.Model.Driver getDriver() {
        return Driver;
    }

    public void setDriver(com.example.growingmobilef1.Model.Driver driver) {
        Driver = driver;
    }

    public int getGrid() {
        return grid;
    }

    public void setGrid(int grid) {
        this.grid = grid;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
