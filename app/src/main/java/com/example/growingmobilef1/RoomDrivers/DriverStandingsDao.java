package com.example.growingmobilef1.RoomDrivers;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.growingmobilef1.Model.Constructor;
import com.example.growingmobilef1.Model.Driver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
@Entity
public class DriverStandingsDao implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int position;
    @ColumnInfo(name="positionText")
    private String positionText;
    @ColumnInfo(name="points")
    private int points;
    @ColumnInfo(name="wins")
    private int wins;
    @ColumnInfo(name="driver")
    private com.example.growingmobilef1.Model.Driver Driver;
    @ColumnInfo(name="Constructor")
    private com.example.growingmobilef1.Model.Constructor Constructor;


    public static DriverStandingsDao fromJson(JSONObject json){

        DriverStandingsDao tempDrivStand = new DriverStandingsDao();
        Driver tempDriver = new Driver();
        Constructor tempCons = new Constructor();

        if(json != null) {
            if (json.length() != 0) {
                try {
                    tempDrivStand.setPosition(json.getInt("position"));
                    tempDrivStand.setPositionText(json.getString("positionText"));
                    tempDrivStand.setPoints(json.getInt("points"));
                    tempDrivStand.setWins(json.getInt("wins"));

                    tempDrivStand.setDriver(tempDriver.fromJson(json.getJSONObject("Driver")));
                    tempDrivStand.setConstructor(tempCons.fromJson(json.optJSONObject("Constructor")));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
