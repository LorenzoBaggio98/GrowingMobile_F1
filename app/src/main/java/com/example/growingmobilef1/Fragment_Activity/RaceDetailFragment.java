package com.example.growingmobilef1.Fragment_Activity;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.growingmobilef1.Model.CalendarRaceItem;
import com.example.growingmobilef1.R;

public class RaceDetailFragment extends Fragment {

    public static final String RACE_ITEM = "Tag to pass the calendar race item to the fragment";

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

        return vView;
    }

    private class RaceDetailApiAsyncCaller extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
