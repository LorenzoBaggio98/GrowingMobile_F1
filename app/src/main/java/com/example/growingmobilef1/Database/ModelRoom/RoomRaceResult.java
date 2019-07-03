package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import com.example.growingmobilef1.Model.IListableModel;
import java.time.LocalTime;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "race_results",
        primaryKeys = {"id", "raceId"},
        foreignKeys = {@ForeignKey(
                entity = RoomRace.class,
                parentColumns = "circuitId",
                childColumns = "raceId",
                onDelete = CASCADE
        ), @ForeignKey(
                entity = RoomDriver.class,
                parentColumns = "driverId",
                childColumns = "driverId",
                onDelete = CASCADE
        )}
)
public class RoomRaceResult implements IListableModel {

    @NonNull
    public int id;

    public int position;
    public String time;

    @NonNull
    public String raceId;
    public String driverId;

    //Todo
    public LocalTime timeToLocalTime(){

        LocalTime time = LocalTime.parse(this.time);

        return time;
    }
}
