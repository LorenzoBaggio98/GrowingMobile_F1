package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.growingmobilef1.Model.IListableModel;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "race_results",
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

    @PrimaryKey
    @NonNull
    public int id;

    public int position;
    public String time;

    public String raceId;
    public String driverId;

}
