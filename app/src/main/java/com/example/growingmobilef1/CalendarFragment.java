package com.example.growingmobilef1;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileReader;

public class CalendarFragment extends Fragment {

    public static Handler handler = new Handler();

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_calendar, null);

        ApiRequestHelper vApiRequestHelper = new ApiRequestHelper(getActivity()
                .getApplicationContext(), "http://ergast.com/api/f1/current.json");

        Thread t = new Thread(vApiRequestHelper);
        t.start();

        return vView;
    }
}
