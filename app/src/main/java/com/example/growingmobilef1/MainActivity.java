package com.example.growingmobilef1;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String CALENDAR_FRAGMENT = "tag for launching the calendar fragment";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab_bar_calendar:
                    launchCalendarFragment();
                    return true;
                case R.id.tab_bar_pilots_ranking:
                    return true;
                case R.id.tab_bar_constructors_ranking:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        launchCalendarFragment();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void launchCalendarFragment() {
        FragmentTransaction vFT = getFragmentManager().beginTransaction();
        CalendarFragment vCalendarFragment = CalendarFragment.newInstance();
        vFT.replace(R.id.main_act_fragment_container, vCalendarFragment, CALENDAR_FRAGMENT);
        vFT.commit();
    }

}
