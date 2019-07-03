package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.growingmobilef1.Model.IListableModel;

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
public class RoomQualifyingResult implements IListableModel {

    public RoomQualifyingResult(int id, int position, String raceId, String driverId, String q1, String q2, String q3){
        this.id = id;
        this.position = position;
        this.raceId = raceId;
        this.driverId = driverId;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
    }

    @PrimaryKey
    @NonNull
    public int id;

    public int position;

    public String raceId;
    public String driverId;

    public String q1;
    public String q2;
    public String q3;

}
