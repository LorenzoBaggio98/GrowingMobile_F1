package com.example.growingmobilef1.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class FastestLap {

    private int rank;
    private int lap;

    private Time Time;
    private AverageSpeed AverageSpeed;

    public static FastestLap fromJson(JSONObject json){

        Time tempTime = new Time();
        AverageSpeed tempAS = new AverageSpeed();
        FastestLap tempFast = new FastestLap();

        if(json.length() != 0){
            try{

                tempFast.setRank(json.getInt("rank"));
                tempFast.setLap(json.getInt("lap"));
                tempFast.setTime(tempTime.fromJson(json.getJSONObject("Time")));
                tempFast.setAverageSpeed(tempAS.fromJson(json.getJSONObject("AverageSpeed")));

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return tempFast;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getLap() {
        return lap;
    }

    public void setLap(int lap) {
        this.lap = lap;
    }

    public com.example.growingmobilef1.Model.Time getTime() {
        return Time;
    }

    public void setTime(com.example.growingmobilef1.Model.Time time) {
        Time = time;
    }

    public com.example.growingmobilef1.Model.AverageSpeed getAverageSpeed() {
        return AverageSpeed;
    }

    public void setAverageSpeed(com.example.growingmobilef1.Model.AverageSpeed averageSpeed) {
        AverageSpeed = averageSpeed;
    }
}
