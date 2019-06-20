package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "race")
public class RoomRace {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public String name;

    public int circuitId;

    public String dateTime;

    public int notification;

}
