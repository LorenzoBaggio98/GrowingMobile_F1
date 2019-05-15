package com.example.growingmobilef1.Model;
import org.json.JSONObject;

public class Time {

    private int millis;
    private String time;

    public static Time fromJson(JSONObject json){

        Time tempTime = new Time();

        if(json != null){
            if(json.length() != 0){

                tempTime.setMillis(json.optInt("millis"));
                tempTime.setTime(json.optString("time"));
            }
        }

        return tempTime;
    }

    public int getMillis() {
        return millis;
    }

    public void setMillis(int millis) {
        this.millis = millis;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
