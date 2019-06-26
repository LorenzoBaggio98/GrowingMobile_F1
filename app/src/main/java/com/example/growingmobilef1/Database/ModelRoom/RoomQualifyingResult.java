package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "qualifying_results",
        foreignKeys = {@ForeignKey(
                entity = RoomRace.class,
                parentColumns = "id",
                childColumns = "raceId",
                onDelete = CASCADE
        ), @ForeignKey(
                entity = RoomDriver.class,
                parentColumns = "id",
                childColumns = "driverId",
                onDelete = CASCADE
        )}
)
public class RoomQualifyingResult {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public int position;

    public String time;


    public int raceId;

    public int driverId;

    public String temp1;
    public String temp2;
    public String temp3;

}
