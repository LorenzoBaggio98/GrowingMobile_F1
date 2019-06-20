package com.example.growingmobilef1.Database.InterfaceDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.growingmobilef1.Model.Driver;
import com.example.growingmobilef1.Database.ModelRoom.RoomDriver;

import java.util.List;

@Dao
public interface DriverDao {

    @Insert
    void insert(RoomDriver driver);

    @Query("SELECT * FROM driver WHERE id = :id")
    RoomDriver findDriver(int id);

    @Query("SELECT * FROM driver")
    LiveData<List<Driver>> getAllDriver();
}
