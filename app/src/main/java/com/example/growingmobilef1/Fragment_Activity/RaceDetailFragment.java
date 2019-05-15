package com.example.growingmobilef1.Fragment_Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;

public class RaceDetailFragment extends Fragment {

    public static String RESULTS_FRAGMENT = "ResultsFragment";
    public static final String RACE_ITEM = "Tag to pass the calendar race item to the fragment";

    // The race's info
    private Races mCalendarRace;

    private TextView mTitleLabel;

    public static RaceDetailFragment newInstance(Races aCalendarRaceItem) {

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

            // Item passed on CalendarList's click
            mCalendarRace = (Races)vStartingBundle.getSerializable(RACE_ITEM);
            mTitleLabel.setText(mCalendarRace != null ? mCalendarRace.getRaceName() : null);
        }

        // Inizialize the Results List Fragment
        RaceResultsFragment list_results_fragment = (RaceResultsFragment) getChildFragmentManager().findFragmentByTag(RESULTS_FRAGMENT);

        if(list_results_fragment == null){

            FragmentTransaction vFT = getChildFragmentManager().beginTransaction();
            list_results_fragment = RaceResultsFragment.newInstance(mCalendarRace);

            vFT.replace(R.id.container_race_results, list_results_fragment, RESULTS_FRAGMENT);
            vFT.commit();
        }

        return vView;
    }

}
