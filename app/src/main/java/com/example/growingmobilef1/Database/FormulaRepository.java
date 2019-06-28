package com.example.growingmobilef1.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.widget.ListView;

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

import java.util.List;

public class FormulaRepository {

    // DAO
    static private RaceDao raceDao;
    private RaceResultsDao raceResultsDao;
    private QualifyingResultDao qualifyingResultDao;
    private DriverDao driverDao;
    private ConstructorDao constructorDao;

    //
    private LiveData<List<RoomRace>> allRaces;
    private LiveData<List<RoomRaceResult>> allRaceResults;
    private LiveData<List<RoomQualifyingResult>> allQualResults;
    private LiveData<List<RoomDriver>> allDrivers;
    private LiveData<List<RoomConstructor>> allConstructors;

    // Constructor
    public FormulaRepository(Application application){
        FormulaDatabase db = FormulaDatabase.getDatabase(application);

        raceDao = db.raceDao();
        allRaces = raceDao.getAllRaces();

        raceResultsDao = db.raceResultsDao();
        allRaceResults = raceResultsDao.getAllRaceResults();

        qualifyingResultDao = db.qualifyingResultDao();
        allQualResults = qualifyingResultDao.getAllQualResults();

        driverDao = db.driverDao();
        allDrivers = driverDao.getAllDrivers();

        constructorDao = db.constructorDao();
        allConstructors = constructorDao.getAllConstructors();
    }

    // WRAPPERS
    public LiveData<List<RoomRace>> getAllRaces() {
        return allRaces;
    }

    public LiveData<List<RoomRaceResult>> getAllRaceResults() {
        return allRaceResults;
    }

    public LiveData<List<RoomQualifyingResult>> getAllQualResults() {
        return allQualResults;
    }

    public LiveData<List<RoomDriver>> getAllDrivers() {
        return allDrivers;
    }

    public LiveData<List<RoomConstructor>> getAllConstructors() {
        return allConstructors;
    }

    // ASYNC
    public<T> void insertItem(T item){

        if(item instanceof RoomRace) {
            new InsertRaceAsyncTask(raceDao).execute((RoomRace)item);
        }
        else if(item instanceof RoomRaceResult) {
            new InsertRaceResultsAsyncTask(raceResultsDao).execute((RoomRaceResult)item);
        }
        else if(item instanceof RoomQualifyingResult) {
            new InsertQualResultsAsyncTask(qualifyingResultDao).execute((RoomQualifyingResult)item);
        }
        else if(item instanceof RoomDriver) {
            new InsertDriverAsyncTask(driverDao).execute((RoomDriver)item);
        }
        else if(item instanceof RoomConstructor) {
            new InsertConstructorAsyncTask(constructorDao).execute((RoomConstructor)item);
        }
    }


    public void deleteAll() {
        new DeleteConstructorAsyncTask(constructorDao).execute();
    }



    /**
     * ASYNC TASK
     */

    /* Metodo alternativo
    static private void insertItem(RoomRace currentRace){
        new AsyncTask<RoomRace, Void, Void>(){

            @Override
            protected Void doInBackground(RoomRace... roomRaces) {
                raceDao.insert(roomRaces[0]);
                return null;
            }
        }.execute(currentRace);
    }*/

    private static class InsertRaceAsyncTask extends AsyncTask<RoomRace, Void, Void>{

        private RaceDao asyncTaskDao;

        public InsertRaceAsyncTask(RaceDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RoomRace... ts) {

            asyncTaskDao.insert(ts[0]);
            return null;
        }
    }

    private static class InsertRaceResultsAsyncTask extends AsyncTask<RoomRaceResult, Void, Void>{

        private RaceResultsDao asyncTaskDao;

        public InsertRaceResultsAsyncTask(RaceResultsDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RoomRaceResult... ts) {

            asyncTaskDao.insert(ts[0]);
            return null;
        }
    }

    private static class InsertQualResultsAsyncTask extends AsyncTask<RoomQualifyingResult, Void, Void>{

        private QualifyingResultDao asyncTaskDao;

        public InsertQualResultsAsyncTask(QualifyingResultDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RoomQualifyingResult... ts) {
            asyncTaskDao.insert(ts[0]);
            return null;
        }
    }

    private static class InsertDriverAsyncTask extends AsyncTask<RoomDriver, Void, Void>{

        private DriverDao asyncTaskDao;

        public InsertDriverAsyncTask(DriverDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RoomDriver... ts) {
            asyncTaskDao.insert(ts[0]);
            return null;
        }
    }

    private static class InsertConstructorAsyncTask extends AsyncTask<RoomConstructor, Void, Void>{

        private ConstructorDao asyncTaskDao;

        public InsertConstructorAsyncTask(ConstructorDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RoomConstructor... ts) {

            asyncTaskDao.insert(ts[0]);
            return null;
        }
    }

    private static class DeleteConstructorAsyncTask extends AsyncTask<RoomConstructor, Void, Void>{

        private ConstructorDao asyncTaskDao;

        public DeleteConstructorAsyncTask(ConstructorDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RoomConstructor... ts) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }
}
