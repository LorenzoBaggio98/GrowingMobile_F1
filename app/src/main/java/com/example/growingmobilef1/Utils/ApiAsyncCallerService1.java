package com.example.growingmobilef1.Utils;

import android.app.Application;
import android.app.Service;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.growingmobilef1.Database.FormulaRepository;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Helper.CalendarRaceDataHelper;
import com.example.growingmobilef1.Helper.ConstructorsDataHelper;
import com.example.growingmobilef1.Helper.DriversRankingHelper;
import com.example.growingmobilef1.Helper.IGenericHelper;
import com.example.growingmobilef1.Helper.QualifyingResultsDataHelper;
import com.example.growingmobilef1.Helper.RaceResultsDataHelper;
import com.example.growingmobilef1.Model.IListableModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ApiAsyncCallerService1 extends Service {

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
                ApiAsyncCallerService1.this,
                new CalendarRaceDataHelper(),
                getApplication());
        vRacesAsyncCaller.startCall("https://ergast.com/api/f1/current.json",getApplicationContext());

        ApiAsyncCaller vConstructorAsyncCaller = new ApiAsyncCaller(
                ApiAsyncCallerService1.this,
                new ConstructorsDataHelper(),
                getApplication());
        vConstructorAsyncCaller.startCall("https://ergast.com/api/f1/current/constructorStandings.json",getApplicationContext());

        ApiAsyncCaller vDriverAsyncCaller = new ApiAsyncCaller(
                ApiAsyncCallerService1.this,
                new DriversRankingHelper(),
                getApplication());
        vDriverAsyncCaller.startCall("https://ergast.com/api/f1/current/driverStandings.json",getApplicationContext());

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
                                    ApiAsyncCallerService1.this,
                                    new RaceResultsDataHelper(),
                                    getApplication(),
                                    vRoomRaceList);
                    vRaceResultsAsyncCaller.startCall("http://ergast.com/api/f1/current/%s/results.json",getApplicationContext());
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
                            ApiAsyncCallerService1.this,
                            new QualifyingResultsDataHelper(),
                            getApplication(),
                            vRoomRaceList);
                    vRaceResultsAsyncCaller.startCall("https://ergast.com/api/f1/current/%s/qualifying.json",getApplicationContext());
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

    /**
     * ALLA FINE CHIAMO POPULATE RACE DETAILS
     */
    private static class ApiAsyncCaller  {

        private WeakReference<ApiAsyncCallerService1> mApiService;
        private JSONObject vJsonToParse;
        private ArrayList<IListableModel> mHelperArrayList;
        private IGenericHelper mApiGenericHelper;
        private FormulaRepository mRepository;
        JsonObjectRequest mJsonObjectRequest;

        public ApiAsyncCaller(ApiAsyncCallerService1 aApiService, IGenericHelper aApiGenericHelper, Application aApplication) {
            mApiService = new WeakReference<>(aApiService);
            mApiGenericHelper = aApiGenericHelper;
            mRepository = new FormulaRepository(aApplication);
        }
            public void startCall(String aUrl, Context context) {


        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, aUrl, null, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    mHelperArrayList = mApiGenericHelper.getArrayList(jsonObject);
                    for(int i=0; i< mHelperArrayList.size(); i++){
                        mRepository.insertItem(mHelperArrayList.get(i));
                    }
                    if (mApiGenericHelper.getClass().getName().equals(DriversRankingHelper.class.getName())) {
                        if (mApiService.get() != null){
                            mApiService.get().populateRaceDetails();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        if (mJsonObjectRequest != null) {
            Volley.newRequestQueue(context).add(mJsonObjectRequest);
        }
    }




    }

    private static class RaceResultsApiAsyncCaller  {

        private WeakReference<ApiAsyncCallerService1> mApiService;
        private JSONObject vJsonToParse;
        private ArrayList<IListableModel> mHelperArrayList;
        private IGenericHelper mApiGenericHelper;
        private FormulaRepository mRepository;
        private List<RoomRace> mRaceResultsList;
        private  JsonObjectRequest mJsonObjectRequest;

        public RaceResultsApiAsyncCaller(ApiAsyncCallerService1 aApiService,
                                         IGenericHelper aApiGenericHelper,
                                         Application aApplication,
                                         List<RoomRace> aRaceResultsList) {

            mApiService = new WeakReference<>(aApiService);
            mApiGenericHelper = aApiGenericHelper;
            mRepository = new FormulaRepository(aApplication);
            mRaceResultsList = aRaceResultsList;
        }

        public void startCall(String url, Context context) {
            for (RoomRace vRoomRace: mRaceResultsList) {
                mHelperArrayList = new ArrayList<>();
                String downloadUrl = String.format(url, vRoomRace.round);

                mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, downloadUrl, null, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            mHelperArrayList = mApiGenericHelper.getArrayList(jsonObject);
                            for(int i=0; i< mHelperArrayList.size(); i++){
                                mRepository.insertItem(mHelperArrayList.get(i));
                            }
                            if (mApiService.get() != null)
                                mApiService.get().removeRoomRaceListObserver();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                if (mJsonObjectRequest != null) {
                    Volley.newRequestQueue(context).add(mJsonObjectRequest);
                }

            }



        }


    }

    public class ApiCallerBinder extends Binder {
        public ApiAsyncCallerService1 getAsyncService(){
            return ApiAsyncCallerService1.this;
        };
    }


//    public void startCall(String aUrl, IGenericHelper aApiGenericHelper) {
//
//        this.url = aUrl;
//        this.mApiGenericHelper = aApiGenericHelper;
//        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
//                    mHelperArrayList = mApiGenericHelper.getArrayList(jsonObject);
//                    mElementListener.onApiCalled(mHelperArrayList);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        if (mJsonObjectRequest != null) {
//            Volley.newRequestQueue(getContext()).add(mJsonObjectRequest);
//        }
//    }

}
