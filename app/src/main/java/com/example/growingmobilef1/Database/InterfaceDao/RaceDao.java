package com.example.growingmobilef1.Database.InterfaceDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.growingmobilef1.Database.ModelRoom.RoomRace;

import java.util.List;

@Dao
public interface RaceDao {

    @Insert
    void insert(RoomRace race);

    @Query("SELECT * FROM race WHERE id = :id")
    RoomRace findRace(int id);

    @Query("SELECT * FROM race")
    LiveData<List<RoomRace>> getAllRaces();
}
