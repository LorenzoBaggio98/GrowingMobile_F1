package com.example.growingmobilef1.Fragment_Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.growingmobilef1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragmentDetail extends Fragment {


    public ThreeFragmentDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three_fragment_detail, container, false);
    }

}
