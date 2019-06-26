package com.example.growingmobilef1.RoomDrivers;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.growingmobilef1.Model.Driver;
import com.example.growingmobilef1.Model.DriverStandings;

@Database(entities ={DriverStandingsDao.class},version = 1)
public abstract class DriverDataBase extends RoomDatabase {
    public abstract DriverDAO driverDAO();
}
