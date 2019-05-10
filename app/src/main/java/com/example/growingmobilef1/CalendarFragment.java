package com.example.growingmobilef1;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;


public class CalendarFragment extends Fragment {

    private static final String RACE_DETAIL_FRAGMENT = "Tag for launching RaceDetailFragment";

    private ArrayList<CalendarRaceItem> mCalendarRaceItemArraylist;

    private ListView mListView;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_calendar, container, false);
        mListView = vView.findViewById(R.id.frag_calendar_listview);

        // Call the async class to perform the api call
        CalendarApiAsyncCaller vLongOperation = new CalendarApiAsyncCaller();
        vLongOperation.execute();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalendarRaceItem vRaceItem = new CalendarRaceItem();

                // Looks for the clicked item in the ArrayList, then pass it to the detail fragment
                for (int i = 0; i < mCalendarRaceItemArraylist.size(); i++) {
                    if (mCalendarRaceItemArraylist.get(i).getmId() == id){
                        vRaceItem = mCalendarRaceItemArraylist.get(position);
                    }
                }
                launchRaceDetailFragment(vRaceItem);
            }
        });
       return vView;
    }

    private void launchRaceDetailFragment(CalendarRaceItem aRaceItem){
        FragmentTransaction vFT = getFragmentManager().beginTransaction();
        RaceDetailFragment vRaceDetailFrag = RaceDetailFragment.newInstance(aRaceItem);
        vFT.replace(R.id.main_act_fragment_container, vRaceDetailFrag, RACE_DETAIL_FRAGMENT);
        vFT.commit();
    }

    // Private class needed to perform the API call asynchronously
    private class CalendarApiAsyncCaller extends AsyncTask<String, Void, String> {

        private JSONObject vJsonToParse;
        private CalendarRaceDataHelper vCalendarRaceDataHelper;
        @Override
        protected String doInBackground(String... params) {
            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper("http://ergast.com/api/f1/current.json");
            vCalendarRaceDataHelper = new CalendarRaceDataHelper();

            vJsonToParse = vApiRequestHelper.getContentFromUrl("http://ergast.com/api/f1/current.json");
            mCalendarRaceItemArraylist =  vCalendarRaceDataHelper.getArraylist(vJsonToParse);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            CalendarListAdapter vCalendarListAdapter = new CalendarListAdapter(mCalendarRaceItemArraylist);
            mListView.setAdapter(vCalendarListAdapter);
        }
    }
}
