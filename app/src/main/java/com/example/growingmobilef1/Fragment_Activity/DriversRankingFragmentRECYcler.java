package com.example.growingmobilef1.Fragment_Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;

import com.example.growingmobilef1.Adapter.ConstructorsAdapter;
import com.example.growingmobilef1.Adapter.DriverAdapterRECycler;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.DriversRankingHelper;
import com.example.growingmobilef1.Model.ConstructorStandings;
import com.example.growingmobilef1.Model.Driver;
import com.example.growingmobilef1.Model.DriverStandings;
import com.example.growingmobilef1.R;
import com.example.growingmobilef1.Utils.LayoutAnimations;

import org.json.JSONObject;

import java.util.ArrayList;

public class DriversRankingFragmentRECYcler extends Fragment {
    private static final String SAVE_LISTPILOTS = "SAVE_LISTPILOTS";

    private ArrayList<DriverStandings> mArrayListPilots;
    private RecyclerView mRecyclerViewList;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LayoutAnimations mLayoutAnimation;
    private boolean stateProgresBar=true;

    DriversRankingFragmentRECYcler.PilotsApiAsync vPilotsApiAsync = new DriversRankingFragmentRECYcler.PilotsApiAsync();

    public static DriversRankingFragmentRECYcler newInstance() {
        return new DriversRankingFragmentRECYcler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_pilots_ranking, container, false);
        mArrayListPilots=new ArrayList<>();

        mRecyclerViewList = vView.findViewById(R.id.recyclerViewPiloti);
        mProgressBar = vView.findViewById(R.id.frag_calendar_progress_bar);
        mSwipeRefreshLayout=vView.findViewById(R.id.swipeRefreshPilots);

        mLayoutAnimation = new LayoutAnimations();


        mRecyclerViewList.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        mRecyclerViewList.setLayoutManager(linearLayoutManager);
       makeListRecyclerView(mArrayListPilots);

        if (savedInstanceState != null) {
            mArrayListPilots = (ArrayList<DriverStandings>) savedInstanceState.getSerializable(SAVE_LISTPILOTS);
            makeListRecyclerView(mArrayListPilots);

        } else {
            vPilotsApiAsync.execute();
        }


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
if(vPilotsApiAsync !=null){
    vPilotsApiAsync.isCancelled();
}
                  vPilotsApiAsync = new DriversRankingFragmentRECYcler.PilotsApiAsync();
                  vPilotsApiAsync.execute();
stateProgresBar=false;

            }
        });




        return vView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVE_LISTPILOTS, mArrayListPilots);
    }

    private class PilotsApiAsync extends AsyncTask<String, Void, String> {
        private JSONObject vJsonObjectToParse;
        private ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();


        @Override
        protected String doInBackground(String... params) {
            if (stateProgresBar)
           mProgressBar.setVisibility(View.VISIBLE);

            vJsonObjectToParse = vApiRequestHelper.getContentFromUrl("https://ergast.com/api/f1/current/driverStandings.json");

            mArrayListPilots = DriversRankingHelper.getArrayListPilotsPoints(vJsonObjectToParse);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            if(stateProgresBar){
                mProgressBar.setVisibility(View.INVISIBLE);
            }else{
                mSwipeRefreshLayout.setRefreshing(false);
                stateProgresBar=true;
            }

           makeListRecyclerView(mArrayListPilots);


        }
    }

    private void makeListRecyclerView(ArrayList<DriverStandings> mArrayListPilots){

        DriverAdapterRECycler vDriversAdapter = new DriverAdapterRECycler(mArrayListPilots,getContext());
        mRecyclerViewList.setAdapter(vDriversAdapter);
        mLayoutAnimation.runLayoutAnimation(mRecyclerViewList);

    }


}
