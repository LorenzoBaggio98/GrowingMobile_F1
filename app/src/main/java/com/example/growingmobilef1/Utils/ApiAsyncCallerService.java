package com.example.growingmobilef1.Utils;

import android.app.Application;
import android.app.Service;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.growingmobilef1.Database.FormulaDatabase;
import com.example.growingmobilef1.Database.FormulaRepository;
import com.example.growingmobilef1.Database.InterfaceDao.RaceDao;
import com.example.growingmobilef1.Database.ModelRoom.RoomDriver;
import com.example.growingmobilef1.Database.ModelRoom.RoomQualifyingResult;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ViewModel.RaceResultsViewModel;
import com.example.growingmobilef1.Database.ViewModel.RacesViewModel;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.CalendarRaceDataHelper;
import com.example.growingmobilef1.Helper.ConstructorsDataHelper;
import com.example.growingmobilef1.Helper.DriversRankingHelper;
import com.example.growingmobilef1.Helper.IGenericHelper;
import com.example.growingmobilef1.Helper.QualifyingResultsDataHelper;
import com.example.growingmobilef1.Helper.RaceResultsDataHelper;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.Model.RaceResults;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ApiAsyncCallerService extends Service {

    private Observer<List<RoomRace>> mRoomRaceObserver;
    private Observer<List<RoomRace>> mQualifyingObserver;
    private LiveData<List<RoomRace>> mRoomRaceLiveData;
    private LiveData<List<RoomRace>> mQualifyingLiveData;

    ApiCallerBinder mBinder = new ApiCallerBinder();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void populateDatabaseOnCreate(){
        ApiAsyncCaller vRacesAsyncCaller = new ApiAsyncCaller(
                ApiAsyncCallerService.this,
                new CalendarRaceDataHelper(),
                getApplication());
        vRacesAsyncCaller.execute("https://ergast.com/api/f1/current.json");

        ApiAsyncCaller vConstructorAsyncCaller = new ApiAsyncCaller(
                ApiAsyncCallerService.this,
                new ConstructorsDataHelper(),
                getApplication());
        vConstructorAsyncCaller.execute("https://ergast.com/api/f1/current/constructorStandings.json");

        ApiAsyncCaller vDriverAsyncCaller = new ApiAsyncCaller(
                ApiAsyncCallerService.this,
                new DriversRankingHelper(),
                getApplication());
        vDriverAsyncCaller.execute("https://ergast.com/api/f1/current/driverStandings.json");

    }

    private void populateRaceDetails(){
        populateRaceResultsOnCreate();
        populateQualifyingResultsOnCreate();
    }

    // Create an observer
    private void populateRaceResultsOnCreate(){
        FormulaRepository vFR = new FormulaRepository(getApplication());
        mRoomRaceLiveData = vFR.getAllRaces();

        mRoomRaceLiveData.observeForever(mRoomRaceObserver = new Observer<List<RoomRace>>() {
            @Override
            public void onChanged(@Nullable List<RoomRace> roomRaces) {
                List<RoomRace> vRoomRaceList = mRoomRaceLiveData.getValue();

                if (vRoomRaceList != null) {
                    RaceResultsApiAsyncCaller vRaceResultsAsyncCaller =
                            new RaceResultsApiAsyncCaller(
                                    ApiAsyncCallerService.this,
                                    new RaceResultsDataHelper(),
                                    getApplication(),
                                    vRoomRaceList);
                    vRaceResultsAsyncCaller.execute("http://ergast.com/api/f1/current/%s/results.json");
                }
            }
        });
    }

    private void populateQualifyingResultsOnCreate(){
        FormulaRepository vFR = new FormulaRepository(getApplication());
        mQualifyingLiveData = vFR.getAllRaces();

        mQualifyingLiveData.observeForever(mQualifyingObserver = new Observer<List<RoomRace>>() {
            @Override
            public void onChanged(@Nullable List<RoomRace> roomQualifyingResults) {
                List<RoomRace> vRoomRaceList = mRoomRaceLiveData.getValue();

                if (vRoomRaceList != null){
                    RaceResultsApiAsyncCaller vRaceResultsAsyncCaller = new RaceResultsApiAsyncCaller(
                            ApiAsyncCallerService.this,
                            new QualifyingResultsDataHelper(),
                            getApplication(),
                            vRoomRaceList);
                    vRaceResultsAsyncCaller.execute("https://ergast.com/api/f1/current/%s/qualifying.json");
                }
            }
        });
    }

    private void removeRoomRaceListObserver(){
        if (mRoomRaceLiveData.hasObservers()) {
            mRoomRaceLiveData.removeObserver(mRoomRaceObserver);
        }

        if (mQualifyingLiveData.hasObservers()){
            mQualifyingLiveData.removeObserver(mQualifyingObserver);
        }
    }

    private static class ApiAsyncCaller extends AsyncTask<String, Void, String> {

        private WeakReference<ApiAsyncCallerService> mApiService;
        private JSONObject vJsonToParse;
        private ArrayList<IListableModel> mHelperArrayList;
        private IGenericHelper mApiGenericHelper;
        private FormulaRepository mRepository;

        public ApiAsyncCaller(ApiAsyncCallerService aApiService, IGenericHelper aApiGenericHelper, Application aApplication) {
            mApiService = new WeakReference<>(aApiService);
            mApiGenericHelper = aApiGenericHelper;
            mRepository = new FormulaRepository(aApplication);
        }

        @Override
        protected String doInBackground(String... params) {
            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();
            mHelperArrayList = new ArrayList<>();

            // get json from api
            vJsonToParse = vApiRequestHelper.getContentFromUrl(params[0]);

            // parse json to list
            mHelperArrayList =  mApiGenericHelper.getArrayList(vJsonToParse);

            for(int i=0; i< mHelperArrayList.size(); i++){
                mRepository.insertItem(mHelperArrayList.get(i));
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (mApiGenericHelper.getClass().getName().equals(CalendarRaceDataHelper.class.getName())) {
                if (mApiService.get() != null){
                    mApiService.get().populateRaceDetails();
                }
            }


        }
    }

    private static class RaceResultsApiAsyncCaller extends AsyncTask<String, Void, Void> {

        private WeakReference<ApiAsyncCallerService> mApiService;
        private JSONObject vJsonToParse;
        private ArrayList<IListableModel> mHelperArrayList;
        private IGenericHelper mApiGenericHelper;
        private FormulaRepository mRepository;
        private List<RoomRace> mRaceResultsList;

        public RaceResultsApiAsyncCaller(ApiAsyncCallerService aApiService,
                                         IGenericHelper aApiGenericHelper,
                                         Application aApplication,
                                         List<RoomRace> aRaceResultsList) {

            mApiService = new WeakReference<>(aApiService);
            mApiGenericHelper = aApiGenericHelper;
            mRepository = new FormulaRepository(aApplication);
            mRaceResultsList = aRaceResultsList;
        }

        @Override
        protected Void doInBackground(String... params) {
            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();

            for (RoomRace vRoomRace: mRaceResultsList) {
                mHelperArrayList = new ArrayList<>();

                // get json from api
                String downloadUrl = String.format(params[0], vRoomRace.round);
                vJsonToParse = vApiRequestHelper.getContentFromUrl(downloadUrl);

                // parse json to list
                mHelperArrayList = mApiGenericHelper.getArrayList(vJsonToParse);

                if (!mHelperArrayList.isEmpty())
                    mRepository.insertItems(mHelperArrayList);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mApiService.get() != null)
                mApiService.get().removeRoomRaceListObserver();
        }
    }

    public class ApiCallerBinder extends Binder {
        public ApiAsyncCallerService getAsyncService(){
            return ApiAsyncCallerService.this;
        };
    }
}
