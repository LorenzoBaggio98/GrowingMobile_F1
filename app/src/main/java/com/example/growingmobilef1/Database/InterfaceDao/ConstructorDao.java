package com.example.growingmobilef1.Database.InterfaceDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.growingmobilef1.Database.ModelRoom.RoomConstructor;

import java.util.List;

@Dao
public interface ConstructorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RoomConstructor constructor);

    @Query("DELETE FROM constructor")
    void deleteAll();

    @Update
    void update(RoomConstructor user);

    @Delete
    void deleteRecord(RoomConstructor user);

    @Query("SELECT * FROM constructor WHERE constructorId = :id")
    RoomConstructor findDriver(int id);

    @Query("SELECT * FROM constructor")
    LiveData<List<RoomConstructor>> getAllConstructors();
}
