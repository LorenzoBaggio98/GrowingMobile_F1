package com.example.growingmobilef1.Database.InterfaceDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.growingmobilef1.Database.ModelRoom.RoomRace;

import java.util.List;

@Dao
public interface RaceDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RoomRace race);

    @Query("SELECT * FROM race WHERE circuitId = :circuitId")
    RoomRace findRace(String circuitId);

    @Query("SELECT * FROM race")
    LiveData<List<RoomRace>> getAllRaces();
}
