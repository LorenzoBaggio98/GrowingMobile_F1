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

import com.example.growingmobilef1.Adapter.ListableObjectsAdapter;
import com.example.growingmobilef1.Helper.CalendarRaceDataHelper;
import com.example.growingmobilef1.Interface.IListableObject;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;

import org.json.JSONObject;

import java.util.ArrayList;


public class CalendarFragment extends Fragment {

    private ArrayList<IListableObject> mCalendarRaceItemArraylist;

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

        private JSONObject mJsonToParse;
        private CalendarRaceDataHelper mCalendarRaceDataHelper;

        @Override
        protected String doInBackground(String... params) {

            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();
            mCalendarRaceDataHelper = new CalendarRaceDataHelper();

            mJsonToParse = vApiRequestHelper.getContentFromUrl("http://ergast.com/api/f1/current.json");
            mCalendarRaceItemArraylist =  mCalendarRaceDataHelper.getArraylist(mJsonToParse);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ListableObjectsAdapter vCalendarListAdapter = new ListableObjectsAdapter(mCalendarRaceItemArraylist);
            mListView.setAdapter(vCalendarListAdapter);
            mPgsBar.setVisibility(View.GONE);
        }
    }
}
