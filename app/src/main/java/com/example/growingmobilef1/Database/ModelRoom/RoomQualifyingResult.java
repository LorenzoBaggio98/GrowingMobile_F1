package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "qualifying_results",
        foreignKeys = {@ForeignKey(
                entity = RoomRace.class,
                parentColumns = "circuitId",
                childColumns = "circuitId",
                onDelete = CASCADE
        ), @ForeignKey(
                entity = RoomDriver.class,
                parentColumns = "id",
                childColumns = "driverId",
                onDelete = CASCADE
        )}
)
public class RoomQualifyingResult {

    public RoomQualifyingResult(final int id, int position, String qual1, String qual2, String qual3, String circuitId, String driverId){
        this.id = id;
        this.position = position;
        this.qual1 = qual1;
        this.qual2 = qual2;
        this.qual3 = qual3;
        this.circuitId = circuitId;
        this.driverId = driverId;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public int position;

    //public String time;

    public String circuitId;

    public String driverId;

    public String qual1;
    public String qual2;
    public String qual3;
}