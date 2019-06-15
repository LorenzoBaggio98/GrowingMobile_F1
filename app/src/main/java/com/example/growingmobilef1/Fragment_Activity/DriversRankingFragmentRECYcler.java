package com.example.growingmobilef1.Fragment_Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import com.example.growingmobilef1.Adapter.DriverAdapterRECycler;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.DriversRankingHelper;
import com.example.growingmobilef1.Model.Driver;
import com.example.growingmobilef1.Model.DriverStandings;
import com.example.growingmobilef1.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class DriversRankingFragmentRECYcler extends Fragment {
    private static final String SAVE_LISTPILOTS = "SAVE_LISTPILOTS";

    private ArrayList<DriverStandings> mArrayListPilots;
    private RecyclerView mRecyclerViewList;
    private ProgressBar mProgressBar;

    DriversRankingFragmentRECYcler.PilotsApiAsync vPilotsApiAsync = new DriversRankingFragmentRECYcler.PilotsApiAsync();

    public static DriversRankingFragmentRECYcler newInstance() {
        return new DriversRankingFragmentRECYcler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_pilots_ranking, container, false);

        mRecyclerViewList = (RecyclerView)vView.findViewById(R.id.recyclerViewPiloti);
//        mRecyclerViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Driver vdriver = mArrayListPilots.get(position).getDriver();
//
//                Intent vIntent = new Intent(getContext(), DriverDetailActivity.class);
//                vIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                Bundle vBundle = new Bundle();
//                vBundle.putSerializable("SAVE_ID", vdriver);
//                vIntent.putExtras(vBundle);
//                startActivity(vIntent);

           // }
     //   });
        mProgressBar = vView.findViewById(R.id.frag_calendar_progress_bar);
        if (savedInstanceState != null) {

            mArrayListPilots = (ArrayList<DriverStandings>) savedInstanceState.getSerializable(SAVE_LISTPILOTS);
            DriverAdapterRECycler vDriversAdapter = new DriverAdapterRECycler(mArrayListPilots);
            mRecyclerViewList.setAdapter(vDriversAdapter);

        } else {
            vPilotsApiAsync.execute();
        }


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
        private Driver pilotRaceItem = new Driver();

        @Override
        protected String doInBackground(String... params) {
            mProgressBar.setVisibility(View.VISIBLE);

            vJsonObjectToParse = vApiRequestHelper.getContentFromUrl("https://ergast.com/api/f1/current/driverStandings.json");

            mArrayListPilots = DriversRankingHelper.getArrayListPilotsPoints(vJsonObjectToParse);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            DriverAdapterRECycler vDriversAdapter = new DriverAdapterRECycler(mArrayListPilots);
            mRecyclerViewList.setAdapter(vDriversAdapter);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }


}
