package com.example.growingmobilef1.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.growingmobilef1.Database.InterfaceDao.ConstructorDao;
import com.example.growingmobilef1.Database.InterfaceDao.DriverDao;
import com.example.growingmobilef1.Database.InterfaceDao.QualifyingResultDao;
import com.example.growingmobilef1.Database.InterfaceDao.RaceDao;
import com.example.growingmobilef1.Database.InterfaceDao.RaceResultsDao;
import com.example.growingmobilef1.Database.ModelRoom.RoomConstructor;
import com.example.growingmobilef1.Database.ModelRoom.RoomDriver;
import com.example.growingmobilef1.Database.ModelRoom.RoomQualifyingResult;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ModelRoom.RoomRaceResult;

import java.util.concurrent.Executors;

@Database(
        entities = {
                RoomRace.class,
                RoomRaceResult.class,
                RoomQualifyingResult.class,
                RoomDriver.class,
                RoomConstructor.class
        }, version = 5
)
public abstract class FormulaDatabase extends RoomDatabase {

    //DAO
    public abstract RaceDao raceDao();
    public abstract RaceResultsDao raceResultsDao();
    public abstract QualifyingResultDao qualifyingResultDao();
    public abstract DriverDao driverDao();
    public abstract ConstructorDao constructorDao();

    // SINGLETON
    private static volatile FormulaDatabase INSTANCE;

    static FormulaDatabase getDatabase(final Context context){

        if(INSTANCE == null){
            synchronized (FormulaDatabase.class){

                if(INSTANCE == null){

                    // Create database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FormulaDatabase.class, "formula_database")
                            .fallbackToDestructiveMigration()
                            .build();

                }

            }
        }

        return INSTANCE;
    }
}
