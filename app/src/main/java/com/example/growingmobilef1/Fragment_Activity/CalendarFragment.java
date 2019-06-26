package com.example.growingmobilef1.Fragment_Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.growingmobilef1.Adapter.QualifyingResultsAdapter;
import com.example.growingmobilef1.Adapter.RacesAdapter;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.RacesViewModel;
import com.example.growingmobilef1.Helper.CalendarRaceDataHelper;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.RaceResultsDataHelper;
import com.example.growingmobilef1.Model.QualifyingResults;
import com.example.growingmobilef1.Model.RaceResults;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;
import com.example.growingmobilef1.Utils.LayoutAnimations;
import com.example.growingmobilef1.Utils.NotificationUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CalendarFragment extends Fragment implements RacesAdapter.IOnRaceClicked, RacesAdapter.IOnNotificationIconClicked{

    private ArrayList<Races> mCalendarRaceItemArraylist;
    private HashMap<String, ArrayList<RaceResults>> mRaceResultsMap;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView.LayoutManager mLayoutManager;
    private RacesAdapter mAdapter;
    private ProgressBar mPgsBar;

    // Notification
    NotificationUtil mNotificationUtil;
    private LayoutAnimations mLayoutAnimations;

    // Database
    private RacesViewModel racesViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewModel creato da Provider
        racesViewModel = ViewModelProviders.of(this).get(RacesViewModel.class);

        //todo vedere se serve e fare il wrapper tra model db e model rest
        /*racesViewModel.getAllRaces().observe(this, new Observer<List<RoomRace>>() {
            @Override
            public void onChanged(List<RoomRace> roomRaces) {

                ArrayList<Races> temp = new ArrayList<>();

                for(int i = 0; i< roomRaces.size(); i++){
                    temp.add(roomRaces.get(i).toRace());
                }

                mAdapter.updateData(temp);
            }
        });*/
    }

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_calendar, container, false);

        mRecyclerView = vView.findViewById(R.id.frag_calendar_listview);
        mPgsBar = vView.findViewById(R.id.frag_calendar_progress_bar);
        mSwipeRefresh = vView.findViewById(R.id.frag_calendar_refresh_layout);
        mLayoutAnimations = new LayoutAnimations();

        // Call the async class to perform the api call
        CalendarApiAsyncCaller vCalendarAsyncCaller = new CalendarApiAsyncCaller();
        vCalendarAsyncCaller.execute();

        CalendarPodiumApiAsyncCaller vPodiumAsyncCaller = new CalendarPodiumApiAsyncCaller();
        vPodiumAsyncCaller.execute();

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RacesAdapter(getContext(),
                new ArrayList<Races>(),
                new HashMap<String, ArrayList<RaceResults>>(),
                this,
                this);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        return vView;
    }

    private void setListAdapter(){
        RacesAdapter vCalendarListAdapter = new RacesAdapter(getContext(),
                mCalendarRaceItemArraylist,
                mRaceResultsMap,
                this,
                this);
        mRecyclerView.setAdapter(vCalendarListAdapter);
    }

    private void refreshList(){
        CalendarApiAsyncCaller vCalendarAsync = new CalendarApiAsyncCaller();
        CalendarPodiumApiAsyncCaller vPodiumAsync = new CalendarPodiumApiAsyncCaller();
        vCalendarAsync.execute();
        vPodiumAsync.execute();
    }

    private void launchRaceDetailActivity(Races aRaceItem){

        Intent intent = new Intent(getContext(), RaceDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle bundle = new Bundle();

        bundle.putSerializable(RaceDetailActivity.RACE_ITEM, aRaceItem);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRaceClicked(int aPosition) {
        Races vRaceItem = new Races();
        long vId = mCalendarRaceItemArraylist.get(aPosition).getmId();
        // Looks for the clicked item in the ArrayList, then pass it to the detail fragment
        for (int i = 0; i < mCalendarRaceItemArraylist.size(); i++) {
            if (mCalendarRaceItemArraylist.get(i).getmId() == vId){
                vRaceItem = (Races)mCalendarRaceItemArraylist.get(aPosition);
            }
        }
        launchRaceDetailActivity(vRaceItem);
    }

    @Override
    public void onNotificationScheduled(int aPosition) {
        mNotificationUtil = new NotificationUtil(
                mCalendarRaceItemArraylist.get(aPosition).getDateTime(),
                getContext(),
                mCalendarRaceItemArraylist.get(aPosition)
        );
        mNotificationUtil.sendNotification();
    }

    // Private class needed to perform the API call asynchronously
    private class CalendarApiAsyncCaller extends AsyncTask<String, Void, String> {

        // Races calendar variables
        private JSONObject mJsonCalendarToParse;
        private CalendarRaceDataHelper mCalendarRaceDataHelper;

        @Override
        protected String doInBackground(String... params) {

            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();
            mCalendarRaceDataHelper = new CalendarRaceDataHelper();
            mRaceResultsMap = new HashMap<>();

            mJsonCalendarToParse = vApiRequestHelper.getContentFromUrl("http://ergast.com/api/f1/current.json");
            if (mJsonCalendarToParse != null) {
                mCalendarRaceItemArraylist =  mCalendarRaceDataHelper.getArraylist(mJsonCalendarToParse);

                // Inserire su db
                populateDb();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (!mRaceResultsMap.isEmpty()){
                setListAdapter();
            }
        }
    }

    private class CalendarPodiumApiAsyncCaller extends AsyncTask<String, Void, String> {
        CalendarRaceDataHelper vCalendarRaceDataHelper = new CalendarRaceDataHelper();

        @Override
        protected String doInBackground(String... params) {

            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();

            mRaceResultsMap = new HashMap<>();

            JSONObject vResultsObject = vApiRequestHelper.getContentFromUrl("http://ergast.com/api/f1/current/results.json?limit=10000");

            if(vResultsObject != null) {
                ArrayList<Races> vRacesArrayList = vCalendarRaceDataHelper.getArraylist(vResultsObject);

                if (vRacesArrayList != null) {
                    for (Races vRaceResult : vRacesArrayList) {
                        for (Races vRace : mCalendarRaceItemArraylist) {
                            if (vRaceResult.getRaceName().equals(vRace.getRaceName())) {
                                mRaceResultsMap.put(vRace.getRaceName(), vRaceResult.getResults());
                            }
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            setListAdapter();
            mPgsBar.setVisibility(View.GONE);
            mLayoutAnimations.runLayoutAnimation(mRecyclerView);
            mSwipeRefresh.setRefreshing(false);
            mAdapter.updateData(mCalendarRaceItemArraylist, mRaceResultsMap);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    void populateDb(){

        racesViewModel.getAllRaces();

        for(int i=0; i< mCalendarRaceItemArraylist.size(); i++){

             RoomRace vRoomRace = mCalendarRaceItemArraylist.get(i).toRoomRace();
             boolean isRaceSaved = false;

             for (Races vRace: mCalendarRaceItemArraylist) {
                 if (vRace.getCircuit().getCircuitId() == vRoomRace.circuitId)
                     isRaceSaved = true;
             }

             if (!isRaceSaved)
                racesViewModel.insert(mCalendarRaceItemArraylist.get(i).toRoomRace());
        }

    }
}
