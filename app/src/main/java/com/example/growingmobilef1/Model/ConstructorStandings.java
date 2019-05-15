package com.example.growingmobilef1.Model;

import com.example.growingmobilef1.Interface.IListableObject;

import org.json.JSONException;
import org.json.JSONObject;

public class ConstructorStandings implements IListableObject {

    private int position;
    private String positionText;
    private int points;
    private int wins;
    private Constructor Constructor;

    public static ConstructorStandings fromJson(JSONObject json){

        ConstructorStandings tempConsStand = new ConstructorStandings();

        Constructor tempCons = new Constructor();

        if(json.length() != 0){
            try{

                tempConsStand.setPosition(json.getInt("position"));
                tempConsStand.setPositionText(json.getString("positionText"));
                tempConsStand.setPoints(json.getInt("points"));
                tempConsStand.setWins(json.getInt("wins"));

                tempConsStand.setConstructor(tempCons.fromJson(json.getJSONObject("Constructor")));

            }catch (JSONException e){
                e.printStackTrace();
            }

        }

        return tempConsStand;
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

    public com.example.growingmobilef1.Model.Constructor getConstructor() {
        return Constructor;
    }

    public void setConstructor(com.example.growingmobilef1.Model.Constructor constructor) {
        Constructor = constructor;
    }

    @Override
    public int getmId() {
        return 0;
    }

    @Override
    public String getmMainInformation() {
        return getConstructor().getName();
    }

    @Override
    public String getmOptionalInformation() {
        return "" + getPosition();
    }

    @Override
    public String getmSecondaryInformation() {
        return "" + getPoints();
    }

    @Override
    public Boolean isButtonRequired() {
        return false;
    }
}
