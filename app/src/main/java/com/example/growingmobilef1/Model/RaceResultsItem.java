package com.example.growingmobilef1.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class RaceResultsItem {

    private int number;
    private int position;
    private String positionText;
    private int points;

    private Pilot Pilot;
    private Constructor Constructor;

    private int grid;
    private int laps;
    private String status;
    private Time Time;
    private FastestLap FastestLap;

    public RaceResultsItem(){

    }

    public static RaceResultsItem fromJson(JSONObject json){

        RaceResultsItem temp = new RaceResultsItem();

        Pilot tempPilot = new Pilot();
        Constructor tempCons = new Constructor();
        Time tempTime = new Time();
        FastestLap tempFL = new FastestLap();

        if(json.length() != 0){
            try{

                temp.setNumber(json.getInt("number"));
                temp.setPosition(json.getInt("position"));
                temp.setPositionText(json.getString("positionText"));
                temp.setPoints(json.getInt("points"));

                temp.setPilot(tempPilot.fromJson(json.getJSONObject("Pilot")));
                temp.setConstructor(tempCons.fromJson(json.getJSONObject("Constructor")));

                temp.setGrid(json.getInt("grid"));
                temp.setLaps(json.getInt("laps"));
                temp.setStatus(json.getString("status"));

                temp.setTime(tempTime.fromJson(json.optJSONObject("Time")));
                temp.setFastestLap(tempFL.fromJson(json.getJSONObject("FastestLap")));

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

    public Pilot getPilot() {
        return Pilot;
    }

    public void setPilot(Pilot pilot) {
        Pilot = pilot;
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

    public com.example.growingmobilef1.Model.Constructor getConstructor() {
        return Constructor;
    }

    public void setConstructor(com.example.growingmobilef1.Model.Constructor constructor) {
        Constructor = constructor;
    }

    public com.example.growingmobilef1.Model.Time getTime() {
        return Time;
    }

    public void setTime(com.example.growingmobilef1.Model.Time time) {
        Time = time;
    }

    public com.example.growingmobilef1.Model.FastestLap getFastestLap() {
        return FastestLap;
    }

    public void setFastestLap(com.example.growingmobilef1.Model.FastestLap fastestLap) {
        FastestLap = fastestLap;
    }
}
