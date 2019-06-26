package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "constructor")
public class RoomConstructor {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public String name;
    public String nationality;

    public int rankPosition;
    public int rankPoints;

}
