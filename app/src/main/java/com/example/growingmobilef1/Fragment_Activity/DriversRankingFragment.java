package com.example.growingmobilef1.Fragment_Activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.growingmobilef1.Adapter.DriversAdapter;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.DriversRankingHelper;
import com.example.growingmobilef1.Model.Driver;
import com.example.growingmobilef1.Model.DriverStandings;
import com.example.growingmobilef1.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class DriversRankingFragment extends Fragment {

    private static final String SAVE_LISTPILOTS = "SAVE_LISTPILOTS";

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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


//                TextView txtnome=view.findViewById(R.id.list_item_driver_name);
//
//                String nome=(String)txtnome.getText();
//                for (int i=0;i<mArrayListPilots.size();i++){
//                 //   if ( mArrayListPilots.get(i).getDriver().getDriverId()==
//                }

              Driver vdriver=  mArrayListPilots.get(position).getDriver();

                Intent vIntent=new Intent(getContext(),DriverDetailActivity.class);
                Bundle vBundle=new Bundle();
                vBundle.putSerializable("SAVE_ID",vdriver);
                vIntent.putExtras(vBundle);
                startActivity(vIntent);

            }
        });
        mProgressBar=vView.findViewById(R.id.frag_calendar_progress_bar);
if(savedInstanceState !=null){

    mArrayListPilots = (ArrayList<DriverStandings>) savedInstanceState.getSerializable(SAVE_LISTPILOTS);
    DriversAdapter vDriversAdapter = new DriversAdapter(mArrayListPilots);
    mListView.setAdapter(vDriversAdapter);

}else{
    vPilotsApiAsync.execute();

}


        return vView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVE_LISTPILOTS,mArrayListPilots);
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

