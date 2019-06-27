package com.example.growingmobilef1.Fragment_Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.growingmobilef1.Adapter.RacesAdapter;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ViewModel.RaceResultsViewModel;
import com.example.growingmobilef1.Database.ViewModel.RacesViewModel;
import com.example.growingmobilef1.Helper.CalendarRaceDataHelper;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.Model.RaceResults;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;
import com.example.growingmobilef1.Utils.LayoutAnimations;
import com.example.growingmobilef1.Utils.NotificationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarFragment extends Fragment implements RacesAdapter.IOnRaceClicked, RacesAdapter.IOnNotificationIconClicked, ApiAsyncCallerFragment.IOnApiCalled{

    public final static String CALENDAR_API_CALLER = "Constructor api caller tag";

    private ArrayList<RoomRace> mCalendarRaceItemArraylist;
    private HashMap<String, List<RaceResults>> mRaceResultsMap;

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
    private RaceResultsViewModel raceResultsViewModel;

    private ApiAsyncCallerFragment mApiCallerFragment;


    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCalendarRaceItemArraylist = new ArrayList<>();

        //
        mAdapter = new RacesAdapter(getContext(),
                new ArrayList<RoomRace>(),
                new HashMap<String, List<RaceResults>>(),
                this,
                this
        );

        // ViewModel creato da Provider
        racesViewModel = ViewModelProviders.of(this).get(RacesViewModel.class);
        raceResultsViewModel = ViewModelProviders.of(this).get(RaceResultsViewModel.class);

        // Observer lista race
        racesViewModel.getAllRaces().observe(this, new Observer<List<RoomRace>>() {
            @Override
            public void onChanged(List<RoomRace> roomRaces) {

                // Race list
                mAdapter.updateData(roomRaces, null);
            }
        });

        //todo observer lista classifica
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_calendar, container, false);

        mRecyclerView = vView.findViewById(R.id.frag_calendar_listview);
        mPgsBar = vView.findViewById(R.id.frag_calendar_progress_bar);
        mSwipeRefresh = vView.findViewById(R.id.frag_calendar_refresh_layout);
        mLayoutAnimations = new LayoutAnimations();

        // Call the async class to perform the api call
        mApiCallerFragment = (ApiAsyncCallerFragment) getFragmentManager().findFragmentByTag(CALENDAR_API_CALLER);
        if (mApiCallerFragment == null){
            launchApiCallerFragment();
        }

        startCall();

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mApiCallerFragment = (ApiAsyncCallerFragment) getFragmentManager().findFragmentByTag(CALENDAR_API_CALLER);
                if (mApiCallerFragment == null){
                    launchApiCallerFragment();
                }

                startCall();
            }
        });
        return vView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mApiCallerFragment != null) {
            mApiCallerFragment.stopCall();
        }
    }

    private void launchApiCallerFragment(){
        FragmentTransaction vFT = getChildFragmentManager().beginTransaction();
        mApiCallerFragment = ApiAsyncCallerFragment.getInstance();
        vFT.add(mApiCallerFragment, CALENDAR_API_CALLER);
        vFT.commit();
    }

    /**
     * Override metodo RecycleView
     * @param aPosition
     */
    @Override
    public void onRaceClicked(int aPosition) {
        Races vRaceItem = new Races();
        long vId = mCalendarRaceItemArraylist.get(aPosition).round;

        boolean isFound = true;
        int i = 0;

        /* Looks for the clicked item in the ArrayList, then pass it to the detail fragment */
        while(isFound) {

            if (mCalendarRaceItemArraylist.get(i).round == vId) {
                vRaceItem = mCalendarRaceItemArraylist.get(aPosition).toRace();
                isFound = false;
            }
            i++;
        }
        launchRaceDetailActivity(vRaceItem);
    }

    /**
     * Avvio del dettaglio race
     * @param aRaceItem
     */
    private void launchRaceDetailActivity(Races aRaceItem){

        Intent intent = new Intent(getContext(), RaceDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle bundle = new Bundle();

        bundle.putSerializable(RaceDetailActivity.RACE_ITEM, aRaceItem);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Override metodo RecycleView
     * @param aPosition
     */
    @Override
    public void onNotificationScheduled(int aPosition) {
        mNotificationUtil = new NotificationUtil(
                mCalendarRaceItemArraylist.get(aPosition).dateToCalendar(),
                getContext(),
                mCalendarRaceItemArraylist.get(aPosition).toRace()
        );
        mNotificationUtil.sendNotification();
    }

    /**
     * Private class needed to perform the API call asynchronously
     */
    /*private class CalendarApiAsyncCaller extends AsyncTask<String, Void, String> {

        // Races calendar variables
        private JSONObject mJsonCalendarToParse;
        private CalendarRaceDataHelper mCalendarRaceDataHelper;

        @Override
        protected String doInBackground(String... params) {

            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();
            mCalendarRaceDataHelper = new CalendarRaceDataHelper();

            mJsonCalendarToParse = vApiRequestHelper.getContentFromUrl("http://ergast.com/api/f1/current.json");
            if (mJsonCalendarToParse != null) {

                ArrayList<Races> temp = mCalendarRaceDataHelper.getArraylist(mJsonCalendarToParse);

                for ( Races r: temp) {
                    mCalendarRaceItemArraylist.add(r.toRoomRace());
                }

                // Inserire su db
                insertRacesToDb();
            }
            return null;
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
                    // Mi servono i risultati
                    for (Races vRaceResult : vRacesArrayList) {
                        for (RoomRace vRace : mCalendarRaceItemArraylist) {
                            if (vRaceResult.getRaceName().equals(vRace.name)) {
                                mRaceResultsMap.put(vRace.circuitId, vRaceResult.getResults());
                            }
                        }
                    }
                    //
                    insertRaceResultsToDb();

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            mPgsBar.setVisibility(View.GONE);
            mLayoutAnimations.runLayoutAnimation(mRecyclerView);
            mSwipeRefresh.setRefreshing(false);
        }
    }*/

    /**
     * Database call
     */
    void insertRacesToDb(){

        for(int i=0; i< mCalendarRaceItemArraylist.size(); i++){
            racesViewModel.insertRace(mCalendarRaceItemArraylist.get(i));
        }
    }

    void insertRaceResultsToDb(){

        for(Map.Entry<String, List<RaceResults>> entry : mRaceResultsMap.entrySet()) {

            // Circuit id
            String keyCircuitId = entry.getKey();
            List<RaceResults> valueResults = entry.getValue();

            for(RaceResults results: valueResults){
                raceResultsViewModel.insertResults(results.toRoomRaceResults(keyCircuitId));
            }

        }

    }

    @Override
    public void onApiCalled(ArrayList<IListableModel> aConstructorList) {

        mAdapter.updateData(aConstructorList, null);

        mPgsBar.setVisibility(View.GONE);
        mLayoutAnimations.runLayoutAnimation(mRecyclerView);
        mSwipeRefresh.setRefreshing(false);
        mApiCallerFragment.stopCall();
    }

    public void startCall(){
        CalendarRaceDataHelper vDataHelper = new CalendarRaceDataHelper();
        mApiCallerFragment.startCall("https://ergast.com/api/f1/current.json", vDataHelper);
    }
}
