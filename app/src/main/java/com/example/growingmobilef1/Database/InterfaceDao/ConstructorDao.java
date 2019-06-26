package com.example.growingmobilef1.Database.InterfaceDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.growingmobilef1.Database.ModelRoom.RoomConstructor;

import java.util.List;

@Dao
public interface ConstructorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RoomConstructor constructor);

    @Query("SELECT * FROM constructor WHERE constructorId = :id")
    RoomConstructor findConstructor(String id);

    @Query("SELECT * FROM constructor")
    LiveData<List<RoomConstructor>> getAllConstructors();
}
