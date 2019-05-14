package com.example.growingmobilef1.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class AverageSpeed {

    private String units;
    private Double speed;

    public static AverageSpeed fromJson(JSONObject json){

        AverageSpeed tempAS = new AverageSpeed();

        if(json.length() != 0){
            try{

                tempAS.setUnits(json.getString("units"));
                tempAS.setSpeed(json.getDouble("speed"));

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return tempAS;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }
}
