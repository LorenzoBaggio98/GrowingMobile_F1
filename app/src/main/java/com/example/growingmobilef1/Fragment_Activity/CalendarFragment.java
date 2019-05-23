package com.example.growingmobilef1.Fragment_Activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.growingmobilef1.Adapter.RacesAdapter;
import com.example.growingmobilef1.Helper.CalendarRaceDataHelper;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.RaceResultsDataHelper;
import com.example.growingmobilef1.Model.RaceResults;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CalendarFragment extends Fragment {

    private ArrayList<Races> mCalendarRaceItemArraylist;
    private HashMap<String, ArrayList<RaceResults>> mRaceResultsMap;
    private ArrayList<RaceResults> mRaceResultsArrayList;

    private ListView mListView;
    private ProgressBar mPgsBar;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_calendar, container, false);

        mListView = vView.findViewById(R.id.frag_calendar_listview);
        mPgsBar = vView.findViewById(R.id.frag_calendar_progress_bar);

        // Call the async class to perform the api call
        CalendarApiAsyncCaller vLongOperation = new CalendarApiAsyncCaller();
        vLongOperation.execute();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Races vRaceItem = new Races();

                // Looks for the clicked item in the ArrayList, then pass it to the detail fragment
                for (int i = 0; i < mCalendarRaceItemArraylist.size(); i++) {
                    if (mCalendarRaceItemArraylist.get(i).getmId() == id){
                        vRaceItem = (Races)mCalendarRaceItemArraylist.get(position);
                    }
                }
                launchRaceDetailFragment(vRaceItem);
            }
        });
       return vView;
    }

    /**
     *
     * @param aRaceItem
     */
    private void launchRaceDetailFragment(Races aRaceItem){

        Intent intent = new Intent(getContext(), RaceDetailActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable(RaceDetailFragment.RACE_ITEM, aRaceItem);

        intent.putExtras(bundle);
        startActivity(intent);
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

            RaceResultsDataHelper vRaceResultsDataHelper = new RaceResultsDataHelper();
            mJsonCalendarToParse = vApiRequestHelper.getContentFromUrl("http://ergast.com/api/f1/current.json");
            if (mJsonCalendarToParse != null) {
                mCalendarRaceItemArraylist =  mCalendarRaceDataHelper.getArraylist(mJsonCalendarToParse);
            }

            mRaceResultsMap = new HashMap<String, ArrayList<RaceResults>>() {
            };
           /* for (int i = 0; i < mCalendarRaceItemArraylist.size(); i++) {
                JSONObject vJsonToParse = vApiRequestHelper.getContentFromUrl("http://ergast.com/api/f1/current/"
                        + mCalendarRaceItemArraylist.get(i).getRound() + "/results.json");
                mRaceResultsArrayList = vRaceResultsDataHelper.getRaceResults(vJsonToParse);
                mRaceResultsMap.put(mCalendarRaceItemArraylist.get(i).getRaceName(), mRaceResultsArrayList);
            }*/

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            RacesAdapter vCalendarListAdapter = new RacesAdapter(mCalendarRaceItemArraylist, mRaceResultsMap);
            mListView.setAdapter(vCalendarListAdapter);
            mPgsBar.setVisibility(View.GONE);
        }
    }
}
