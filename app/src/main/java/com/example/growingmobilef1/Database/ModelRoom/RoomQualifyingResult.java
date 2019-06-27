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
public class RoomQualifyingResult {

    public RoomQualifyingResult(int id, int position, String raceId, String driverId, String temp1, String temp2, String temp3){
        this.id = id;
        this.position = position;
        this.raceId = raceId;
        this.driverId = driverId;
        this.temp1 = temp1;
        this.temp2 = temp2;
        this.temp3 = temp3;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public int position;

    public String raceId;
    public String driverId;

    public String temp1;
    public String temp2;
    public String temp3;

}
