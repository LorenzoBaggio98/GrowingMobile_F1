package com.example.growingmobilef1.Fragment_Activity;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.growingmobilef1.Adapter.ListableObjectsAdapter;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.PilotsRankingHelper;
import com.example.growingmobilef1.Interface.IListableObject;
import com.example.growingmobilef1.Model.Driver;
import com.example.growingmobilef1.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class PilotsRankingFragment extends Fragment {

    private ArrayList<IListableObject> mArrayListPilots;
    private ListView mListView;

    public static PilotsRankingFragment newInstance() {
        return new PilotsRankingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_pilots_ranking, container, false);

        mListView = vView.findViewById(R.id.listViewPilots);

        PilotsApiAsync vPilotsApiAsync = new PilotsApiAsync();
        vPilotsApiAsync.execute();

        return vView;
    }


    private class PilotsApiAsync extends AsyncTask<String, Void, String> {
        private JSONObject vJsonObjectToParse;
        private ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();
        private Driver pilotRaceItem = new Driver();

        @Override
        protected String doInBackground(String... params) {

            vJsonObjectToParse = vApiRequestHelper.getContentFromUrl("https://ergast.com/api/f1/current/last/results.json");
            mArrayListPilots = PilotsRankingHelper.getArayListPilotsPoints(vJsonObjectToParse);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ListableObjectsAdapter vListableObjectsAdapterPilots = new ListableObjectsAdapter(mArrayListPilots);
            mListView.setAdapter(vListableObjectsAdapterPilots);
        }
    }
}

