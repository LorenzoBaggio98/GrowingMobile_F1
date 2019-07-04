package com.example.growingmobilef1.Database.InterfaceDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.growingmobilef1.Database.ModelRoom.RoomDriver;

import java.util.List;

@Dao
public interface DriverDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RoomDriver driver);

    @Query("SELECT * FROM driver WHERE driverId = :id")
    RoomDriver findDriver(String id);

    @Query("SELECT * FROM driver")
    LiveData<List<RoomDriver>> getAllDrivers();
}
