package com.example.growingmobilef1.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.widget.Toast;

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
import com.example.growingmobilef1.Helper.ConnectionStatusHelper;
import com.example.growingmobilef1.Utils.ApiAsyncCallerService;

@Database(
        entities = {
                RoomRace.class,
                RoomRaceResult.class,
                RoomQualifyingResult.class,
                RoomDriver.class,
                RoomConstructor.class
        }, version = 7
)
@TypeConverters({Converters.class})
public abstract class FormulaDatabase extends RoomDatabase {

    //DAO
    public abstract RaceDao raceDao();
    public abstract RaceResultsDao raceResultsDao();
    public abstract QualifyingResultDao qualifyingResultDao();
    public abstract DriverDao driverDao();
    public abstract ConstructorDao constructorDao();

    // SINGLETON
    private static volatile FormulaDatabase INSTANCE;

    public static FormulaDatabase getDatabase(final Context context){

        if(!ConnectionStatusHelper.statusConnection(context)){
            Toast.makeText(context,"Non c'è connessione Internet", Toast.LENGTH_SHORT).show();
        }

        if(INSTANCE == null){
            synchronized (FormulaDatabase.class){

                if(INSTANCE == null){

                    // Create database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FormulaDatabase.class,
                            "formula_database")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    // TODO: Manca l'unbind del service
                                    // Async call service
                                    ServiceConnection mConnection = new ServiceConnection() {
                                        @Override
                                        public void onServiceConnected(ComponentName name, IBinder service) {
                                           final ApiAsyncCallerService mAsyncCallerService =
                                                   ((ApiAsyncCallerService.ApiCallerBinder)service).getAsyncService();

                                           mAsyncCallerService.populateDatabaseOnCreate();
                                        }

                                        @Override
                                        public void onServiceDisconnected(ComponentName name) {

                                        }
                                    };

                                    Intent vIntent = new Intent(context, ApiAsyncCallerService.class);
                                    vIntent.setPackage(context.getPackageName());
                                    context.bindService(vIntent, mConnection, Context.BIND_AUTO_CREATE);
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }

            }
        }

        return INSTANCE;
    }
}