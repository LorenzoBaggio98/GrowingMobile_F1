package com.example.growingmobilef1.RoomDrivers;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.growingmobilef1.Model.DriverStandings;

import java.util.List;

@Dao
public interface DriverDAO {

    @Query("SELECT * FROM  DriverStandingsDao")
    List<DriverStandings> getAll();

    @Insert
    void insertAll(DriverStandings... driverStandings);
}
