package com.example.growingmobilef1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PilotsRankingFragment extends Fragment {

    public static PilotsRankingFragment newInstance(){
        return new PilotsRankingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_pilots_ranking, container, false);
        return vView;
    }
}
