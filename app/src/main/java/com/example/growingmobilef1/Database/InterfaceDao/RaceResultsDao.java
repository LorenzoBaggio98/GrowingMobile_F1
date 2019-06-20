package com.example.growingmobilef1.Database.InterfaceDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.example.growingmobilef1.Database.ModelRoom.RoomRaceResult;

import java.util.List;

@Dao
public interface RaceResultsDao {

    @Insert
    long insert(RoomRaceResult raceResults);

    @Query("SELECT * FROM race_results WHERE id = :id")
    RoomRaceResult findRaceResults(int id);

    @Query("SELECT * FROM race_results")
    LiveData<List<RoomRaceResult>> getAllRaceResults();

    @Query("SELECT * FROM race_results WHERE raceId = :raceId")
    LiveData<List<RoomRaceResult>> getRaceResultsByRaceId(int raceId);
}