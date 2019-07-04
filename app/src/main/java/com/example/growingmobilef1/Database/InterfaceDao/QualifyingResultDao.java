package com.example.growingmobilef1.Database.InterfaceDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.growingmobilef1.Database.ModelRoom.RoomQualifyingResult;

import java.util.List;

@Dao
public interface QualifyingResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RoomQualifyingResult qualifyingResult);

    @Query("SELECT * FROM qualifying_results WHERE id = :id")
    RoomQualifyingResult findQualResults(int id);

    @Query("SELECT * FROM qualifying_results")
    LiveData<List<RoomQualifyingResult>> getAllQualResults();

    @Query("SELECT * FROM qualifying_results WHERE raceId = :raceId")
    LiveData<List<RoomQualifyingResult>> getQualResultsByRaceId(String raceId);
}
