package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "race_results",
        foreignKeys = {@ForeignKey(
                entity = RoomRace.class,
                parentColumns = "id",
                childColumns = "raceId",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = RoomDriver.class,
                parentColumns = "id",
                childColumns = "driverId",
                onDelete = CASCADE
        )}
)
public class RoomRaceResult {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public int position;

    public String time;

    public int raceId;

    public int driverId;

}
