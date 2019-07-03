package com.example.growingmobilef1.Fragment_Activity;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.growingmobilef1.Adapter.RaceResultsAdapter;
import com.example.growingmobilef1.Database.ModelRoom.RoomDriver;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ModelRoom.RoomRaceResult;
import com.example.growingmobilef1.Database.ViewModel.DriverViewModel;
import com.example.growingmobilef1.Database.ViewModel.RaceResultsViewModel;
import com.example.growingmobilef1.Helper.ConnectionStatusHelper;
import com.example.growingmobilef1.Database.ModelRoom.RoomRaceResult;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.RaceResultsDataHelper;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.Model.ConstructorStandings;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.Model.RaceResults;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;
import com.example.growingmobilef1.Utils.LayoutAnimations;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment Result List -
 * Container of the Race Results List
 */
public class RaceResultsFragment extends Fragment implements ApiAsyncCallerFragment.IOnApiCalled {

    private static final String RESULTS_API_CALLER = "RESULTS API CALLER";
    public static final String RACE_ITEM = "RI";

    // Array containing the race's results
    private ArrayList<RoomRaceResult> mRaceResultsArrayList;
    private RoomRace mCalendarRace;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RaceResultsAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefresh;
    private LayoutAnimations mLayoutAnimation;

    // Database and API
    private RaceResultsViewModel raceResultsViewModel;
    private DriverViewModel driverViewModel;

    private ApiAsyncCallerFragment mApiCallerFragment;

    // Pass the calendar to perform the URL query
    public static RaceResultsFragment newInstance(RoomRace aCalendarRaceItem) {

        Bundle vBundle = new Bundle();
        vBundle.putSerializable(RACE_ITEM, aCalendarRaceItem);

        RaceResultsFragment vRaceResultsFrag = new RaceResultsFragment();
        vRaceResultsFrag.setArguments(vBundle);

        return vRaceResultsFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new RaceResultsAdapter(new ArrayList<RoomRaceResult>(), new ArrayList<RoomDriver>());

        // VIEW MODELS
        raceResultsViewModel = ViewModelProviders.of(this).get(RaceResultsViewModel.class);
        driverViewModel = ViewModelProviders.of(this).get(DriverViewModel.class);

        Bundle vStartingBundle = getArguments();
        if (vStartingBundle != null) {
            // Item passed on CalendarList's click
            mCalendarRace = (RoomRace)vStartingBundle.getSerializable(RACE_ITEM);
        }

        // Prendo tutti i risultati
        raceResultsViewModel.getRaceResults(mCalendarRace.circuitId).observe(this, new Observer<List<RoomRaceResult>>() {
            @Override
            public void onChanged(List<RoomRaceResult> roomRaceResults) {
                mRaceResultsArrayList = (ArrayList<RoomRaceResult>) roomRaceResults;
                mAdapter.updateData(roomRaceResults);
            }
        });

        // Prendo tutti i driver
        driverViewModel.getAllDriver().observe(this, new Observer<List<RoomDriver>>() {
            @Override
            public void onChanged(@Nullable List<RoomDriver> roomDrivers) {
                mAdapter.addAllDriver(roomDrivers);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vView = inflater.inflate(R.layout.fragment_race_results, container, false);

        mRecyclerView = vView.findViewById(R.id.list_race_results);
        mSwipeRefresh = vView.findViewById(R.id.race_results_frag_swipe);

        mLayoutAnimation = new LayoutAnimations();

        // API
        mApiCallerFragment = (ApiAsyncCallerFragment) getFragmentManager().findFragmentByTag(RESULTS_API_CALLER);
        if(mApiCallerFragment == null){
            launchApiCallerFragment();
        }

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Se c'è connessione faccio il refresh
                if (ConnectionStatusHelper.statusConnection(getContext())){
                    if (mApiCallerFragment == null){
                        launchApiCallerFragment();
                    }
                    startCall();

                }else{
                    Toast.makeText(getContext(),"Non c'è connessione Internet", Toast.LENGTH_SHORT).show();
                    mSwipeRefresh.setRefreshing(false);
                }
            }
        });

        return vView;
    }


    @Override
    public void onDetach() {
        super.onDetach();

        if(mApiCallerFragment != null){
            mApiCallerFragment.stopCall();
        }
    }

    private void launchApiCallerFragment(){
        FragmentTransaction vFT = getChildFragmentManager().beginTransaction();
        mApiCallerFragment = ApiAsyncCallerFragment.getInstance();
        vFT.add(mApiCallerFragment, RESULTS_API_CALLER);
        vFT.commit();
    }
    public void startCall(){
        RaceResultsDataHelper vDataHelper = new RaceResultsDataHelper();
        mApiCallerFragment.startCall("https://ergast.com/api/f1/current/"+mCalendarRace.round+"/results.json", vDataHelper);
    }
    @Override
    public void onApiCalled(ArrayList<IListableModel> aReturnList) {

        // Da IListableModel a RoomRace
        for (IListableModel temp: aReturnList) {
            mRaceResultsArrayList.add((RoomRaceResult) temp);
        }

        // Inserisco su DB
        insertRaceResultsToDb();

        listBeforeViewing();
        mApiCallerFragment.stopCall();
    }

    //
    void insertRaceResultsToDb(){

        for(int i=0; i< mRaceResultsArrayList.size(); i++){
            raceResultsViewModel.insertResults(mRaceResultsArrayList.get(i));
        }
    }

    public void listBeforeViewing(){
        mLayoutAnimation.runLayoutAnimation(mRecyclerView);
        mSwipeRefresh.setRefreshing(false);
    }
}
