package com.example.growingmobilef1.Database.InterfaceDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ModelRoom.RoomRaceResult;

import java.util.List;

@Dao
public interface RaceResultsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RoomRaceResult raceResults);

    @Query("SELECT * FROM race_results WHERE id = :id")
    RoomRaceResult findRaceResults(int id);

    @Query("SELECT * FROM race_results")
    LiveData<List<RoomRaceResult>> getAllRaceResults();

    @Query("SELECT * FROM race_results WHERE raceId = :raceId")
    LiveData<List<RoomRaceResult>> getRaceResultsByRaceId(String raceId);

    @Query("SELECT raceId AS res_race_id, race_results.position AS res_position, driver.code AS dri_code " +
            "FROM race_results JOIN driver ON (race_results.driverId = driver.driverId) " +
            "WHERE raceId IN (:races) AND race_results.position < 4")
    LiveData<List<RoomPodium>> getRacePodium(List<String> races);

    class RoomPodium{

        public String res_race_id;
        public int res_position;
        public String dri_code;

    }
}
