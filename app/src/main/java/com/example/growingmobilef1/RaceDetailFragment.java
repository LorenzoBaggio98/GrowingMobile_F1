package com.example.growingmobilef1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RaceDetailFragment extends Fragment {

    private static final String RACE_ITEM = "Tag to pass the calendar race item to the fragment";

    private TextView mTitleLabel;

    public static RaceDetailFragment newInstance(CalendarRaceItem aCalendarRaceItem) {
        RaceDetailFragment vRaceDetailFrag = new RaceDetailFragment();
        Bundle vBundle = new Bundle();
        vBundle.putSerializable(RACE_ITEM, aCalendarRaceItem);
        vRaceDetailFrag.setArguments(vBundle);
        return vRaceDetailFrag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_race_detail, container, false);

        mTitleLabel = vView.findViewById(R.id.frag_race_detail_label);

        Bundle vStartingBundle = getArguments();
        if (vStartingBundle != null) {
            CalendarRaceItem vRaceItem = (CalendarRaceItem)vStartingBundle.getSerializable(RACE_ITEM);
            mTitleLabel.setText(vRaceItem.getmRaceName());
        }

        return vView;
    }
}
