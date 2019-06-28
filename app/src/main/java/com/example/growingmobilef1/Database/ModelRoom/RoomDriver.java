package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.growingmobilef1.Model.IListableModel;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "driver",
        foreignKeys = @ForeignKey(
                entity = RoomConstructor.class,
                parentColumns = "constructorId",
                childColumns = "constructorId",
                onDelete = CASCADE
        )
)
public class RoomDriver implements IListableModel {

    @PrimaryKey
    @NonNull
    public String driverId;

    public String name;
    public String surname;

    public String nationality;
    public String dateOfBirth;

    public int rankPosition;
    public int rankPoints;

    public String constructorId;

}
