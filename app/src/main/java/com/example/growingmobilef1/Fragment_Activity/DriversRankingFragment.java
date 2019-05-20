package com.example.growingmobilef1.Fragment_Activity;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.growingmobilef1.Adapter.DriversAdapter;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.DriversRankingHelper;
import com.example.growingmobilef1.Model.Driver;
import com.example.growingmobilef1.Model.DriverStandings;
import com.example.growingmobilef1.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class DriversRankingFragment extends Fragment {

    private ArrayList<DriverStandings> mArrayListPilots;
    private ListView mListView;
    private ProgressBar mProgressBar;

    PilotsApiAsync vPilotsApiAsync = new PilotsApiAsync();

    public static DriversRankingFragment newInstance() {
        return new DriversRankingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_pilots_ranking, container, false);

        mListView = vView.findViewById(R.id.listViewPilots);
        mProgressBar=vView.findViewById(R.id.frag_calendar_progress_bar);



        vPilotsApiAsync.execute();

        return vView;
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
            DriversAdapter vDriversAdapter = new DriversAdapter(mArrayListPilots);
            mListView.setAdapter(vDriversAdapter);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
