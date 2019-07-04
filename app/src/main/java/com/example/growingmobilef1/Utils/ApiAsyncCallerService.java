package com.example.growingmobilef1.Utils;

import android.app.Application;
import android.app.Service;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.example.growingmobilef1.Database.FormulaRepository;
import com.example.growingmobilef1.Database.InterfaceDao.RaceDao;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ViewModel.RaceResultsViewModel;
import com.example.growingmobilef1.Database.ViewModel.RacesViewModel;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.CalendarRaceDataHelper;
import com.example.growingmobilef1.Helper.ConstructorsDataHelper;
import com.example.growingmobilef1.Helper.DriversRankingHelper;
import com.example.growingmobilef1.Helper.IGenericHelper;
import com.example.growingmobilef1.Helper.RaceResultsDataHelper;
import com.example.growingmobilef1.Model.IListableModel;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ApiAsyncCallerService extends Service {

    public static final String INTERFACE_GENERIC_HELPER = "generic helper";
    public static final String SERVICE_URL_TO_CALL = "url to call";

    private ApiAsyncCaller mAsyncCaller;
    private IGenericHelper mGenericHelper;
    private String mUrl;

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
        ApiAsyncCaller vRacesAsyncCaller = new ApiAsyncCaller(new CalendarRaceDataHelper(), getApplication());
        vRacesAsyncCaller.execute("https://ergast.com/api/f1/current.json");

        ApiAsyncCaller vConstructorAsyncCaller = new ApiAsyncCaller(new ConstructorsDataHelper(), getApplication());
        vConstructorAsyncCaller.execute("https://ergast.com/api/f1/current/constructorStandings.json");

        ApiAsyncCaller vDriverAsyncCaller = new ApiAsyncCaller(new DriversRankingHelper(), getApplication());
        vDriverAsyncCaller.execute("https://ergast.com/api/f1/current/driverStandings.json");

        // TODO: metti a posto tutto RaceResults
        ApiAsyncCaller vRaceResultsAsyncCaller = new ApiAsyncCaller(new RaceResultsDataHelper(), getApplication());
        vRaceResultsAsyncCaller.execute("http://ergast.com/api/f1/current/1/results.json");
    }

    private static class ApiAsyncCaller extends AsyncTask<String, Void, String> {

        private JSONObject vJsonToParse;
        private ArrayList<IListableModel> mHelperArrayList;
        private IGenericHelper mApiGenericHelper;
        private FormulaRepository mRepository;

        public ApiAsyncCaller(IGenericHelper aApiGenericHelper, Application aApplication) {
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
        protected void onPostExecute(String result) {
        }
    }

    public class ApiCallerBinder extends Binder {
        public ApiAsyncCallerService getAsyncService(){
            return ApiAsyncCallerService.this;
        };
    }
}
