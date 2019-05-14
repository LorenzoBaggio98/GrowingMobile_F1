package com.example.growingmobilef1.Fragment_Activity;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.growingmobilef1.Adapter.RaceResultsAdapter;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.RaceResultsDataHelper;
import com.example.growingmobilef1.Model.CalendarRaceItem;
import com.example.growingmobilef1.Model.RaceResultsItem;
import com.example.growingmobilef1.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class RaceResultsFragment extends Fragment {

    public static final String RACE_ITEM = "RI";

    // Array containing the race's results
    private ArrayList<RaceResultsItem> mRaceResultsArrayList;
    private CalendarRaceItem mCalendarRace;

    private ListView mListViewResult;

    // Pass the calendar to perform the URL query
    public static RaceResultsFragment newInstance(CalendarRaceItem aCalendarRaceItem) {

        Bundle vBundle = new Bundle();
        vBundle.putSerializable(RACE_ITEM, aCalendarRaceItem);

        RaceResultsFragment vRaceResultsFrag = new RaceResultsFragment();
        vRaceResultsFrag.setArguments(vBundle);

        return vRaceResultsFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vView = inflater.inflate(R.layout.fragment_race_results, container, false);

        mListViewResult = vView.findViewById(R.id.list_race_results);

        Bundle vStartingBundle = getArguments();
        if (vStartingBundle != null) {

            // Item passed on CalendarList's click
            mCalendarRace = (CalendarRaceItem)vStartingBundle.getSerializable(RACE_ITEM);
        }

        RaceResultsApiAsynCaller vLongOperation = new RaceResultsApiAsynCaller();
        vLongOperation.execute();

        return vView;
    }

    private class RaceResultsApiAsynCaller extends AsyncTask<String, Void, String> {

        private JSONObject vJsonToParse;
        private RaceResultsDataHelper vRaceDetailDataHelper;

        @Override
        protected String doInBackground(String... strings) {

            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();
            vRaceDetailDataHelper = new RaceResultsDataHelper();

            String downloadUrl = String.format("http://ergast.com/api/f1/current/%s/results.json", mCalendarRace.getmRound());

            vJsonToParse = vApiRequestHelper.getContentFromUrl(downloadUrl);
            mRaceResultsArrayList = vRaceDetailDataHelper.getRaceResults(vJsonToParse);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            RaceResultsAdapter vCalendarListAdapter = new RaceResultsAdapter(mRaceResultsArrayList);
            mListViewResult.setAdapter(vCalendarListAdapter);
        }
    }
}
