package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "driver",
        foreignKeys = @ForeignKey(
                entity = RoomConstructor.class,
                parentColumns = "id",
                childColumns = "constructorId",
                onDelete = CASCADE
        )
)
public class RoomDriver {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public String name;
    public String surname;

    public String nationality;
    public String dateOfBirth;

    public int rankPosition;
    public int rankPoints;

    public int constructorId;

}
