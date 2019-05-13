package com.example.growingmobilef1.Fragment_Activity;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.RaceDetailDataHelper;
import com.example.growingmobilef1.Model.CalendarRaceItem;
import com.example.growingmobilef1.Model.RaceResultsItem;
import com.example.growingmobilef1.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class RaceDetailFragment extends Fragment {

    public static final String RACE_ITEM = "Tag to pass the calendar race item to the fragment";

    private ArrayList<RaceResultsItem> mRaceResultsItem;
    private TextView mTitleLabel;

    public static RaceDetailFragment newInstance(CalendarRaceItem aCalendarRaceItem) {

        Bundle vBundle = new Bundle();
        vBundle.putSerializable(RACE_ITEM, aCalendarRaceItem);

        RaceDetailFragment vRaceDetailFrag = new RaceDetailFragment();
        vRaceDetailFrag.setArguments(vBundle);
        return vRaceDetailFrag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_race_detail, container, false);

        mTitleLabel = vView.findViewById(R.id.frag_race_detail_name);

        Bundle vStartingBundle = getArguments();
        if (vStartingBundle != null) {
            CalendarRaceItem vRaceItem = (CalendarRaceItem)vStartingBundle.getSerializable(RACE_ITEM);
            mTitleLabel.setText(vRaceItem.getmRaceName());
        }

        RaceDetailApiAsyncCaller vLongOperation = new RaceDetailApiAsyncCaller();
        vLongOperation.execute();

        return vView;
    }

    private class RaceDetailApiAsyncCaller extends AsyncTask<String, Void, String>{

        private JSONObject vJsonToParse;
        private RaceDetailDataHelper vRaceDetailDataHelper;

        @Override
        protected String doInBackground(String... strings) {

            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();
            vRaceDetailDataHelper = new RaceDetailDataHelper();

            vJsonToParse = vApiRequestHelper.getContentFromUrl("http://ergast.com/api/f1/current/1/results.json");
            mRaceResultsItem = vRaceDetailDataHelper.getRaceResults(vJsonToParse);

            return null;
        }
    }
}
