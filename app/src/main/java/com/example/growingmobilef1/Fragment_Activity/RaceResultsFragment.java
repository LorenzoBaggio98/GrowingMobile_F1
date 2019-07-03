package com.example.growingmobilef1.Fragment_Activity;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.growingmobilef1.Adapter.ConstructorsAdapter;
import com.example.growingmobilef1.Adapter.RaceResultsAdapter;
import com.example.growingmobilef1.Database.ModelRoom.RoomRaceResult;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.RaceResultsDataHelper;
import com.example.growingmobilef1.Model.ConstructorStandings;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.Model.RaceResults;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;
import com.example.growingmobilef1.Utils.LayoutAnimations;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Fragment Result List -
 * Container of the Race Results List
 */
public class RaceResultsFragment extends Fragment {

   // private RaceResultsApiAsyncCaller mRaceAsync;

    public static final String RACE_ITEM = "RI";

    // Array containing the race's results
    private ArrayList<RoomRaceResult> mRaceResultsArrayList;
    private Races mCalendarRace;
    private LayoutAnimations mLayoutAnimation;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RaceResultsAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    // Pass the calendar to perform the URL query
    public static RaceResultsFragment newInstance(Races aCalendarRaceItem) {

        Bundle vBundle = new Bundle();
        vBundle.putSerializable(RACE_ITEM, aCalendarRaceItem);

        RaceResultsFragment vRaceResultsFrag = new RaceResultsFragment();
        vRaceResultsFrag.setArguments(vBundle);

        return vRaceResultsFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vView = inflater.inflate(R.layout.fragment_race_results, container, false);

        mRecyclerView = vView.findViewById(R.id.list_race_results);
        mRefreshLayout = vView.findViewById(R.id.race_results_frag_swipe);

        mLayoutAnimation = new LayoutAnimations();

        Bundle vStartingBundle = getArguments();
        if (vStartingBundle != null) {
            // Item passed on CalendarList's click
            mCalendarRace = (Races)vStartingBundle.getSerializable(RACE_ITEM);
        }

  //      mRaceAsync = new RaceResultsApiAsyncCaller();
    //    mRaceAsync.execute();

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RaceResultsAdapter(new ArrayList<RaceResults>());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // refreshList();
            }
        });

        return vView;
    }
/*
    private void refreshList(){
        RaceResultsApiAsyncCaller vResultAsync = new RaceResultsApiAsyncCaller();
        vResultAsync.execute();
    }

    private class RaceResultsApiAsyncCaller extends AsyncTask<String, Void, String> {

        private JSONObject vJsonToParse;
        private RaceResultsDataHelper vRaceDetailDataHelper;

        @Override
        protected String doInBackground(String... strings) {

            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();
            vRaceDetailDataHelper = new RaceResultsDataHelper();

            String downloadUrl = String.format("http://ergast.com/api/f1/current/%s/results.json", mCalendarRace.getRound());

            vJsonToParse = vApiRequestHelper.getContentFromUrl(downloadUrl);
            mRaceResultsArrayList = vRaceDetailDataHelper.getArrayList(vJsonToParse);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            mLayoutAnimation.runLayoutAnimation(mRecyclerView);
            mRefreshLayout.setRefreshing(false);
            mAdapter.updateData(mRaceResultsArrayList);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }*/
}
