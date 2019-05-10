package com.example.growingmobilef1;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.example.growingmobilef1.Fragment.CalendarFragment;
import com.example.growingmobilef1.Fragment.ConstructorsRankingFragment;
import com.example.growingmobilef1.Fragment.PilotsRankingFragment;

public class MainActivity extends AppCompatActivity {

    private static final String CALENDAR_FRAGMENT = "tag for launching the calendar fragment";
    private static final String PILOTS_RANKING_FRAGMENT = "tag for launching the pilots ranking fragment";
    private static final String CONSTRUCTORS_RANKING_FRAGMENT = "tag for launching the constructors ranking fragment";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab_bar_calendar:
                    launchCalendarFragment();
                    return true;
                case R.id.tab_bar_pilots_ranking:
                    launchPilotsRankingFragment();
                    return true;
                case R.id.tab_bar_constructors_ranking:
                    launchConstructorsRankingFragment();
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

    private void launchPilotsRankingFragment(){
        FragmentTransaction vFT = getFragmentManager().beginTransaction();
        PilotsRankingFragment vPilotsFragment = PilotsRankingFragment.newInstance();
        vFT.replace(R.id.main_act_fragment_container, vPilotsFragment, PILOTS_RANKING_FRAGMENT);
        vFT.commit();
    }

    private void launchConstructorsRankingFragment() {
        FragmentTransaction vFT = getFragmentManager().beginTransaction();
        ConstructorsRankingFragment vConstructorsFragment = ConstructorsRankingFragment.newInstance();
        vFT.replace(R.id.main_act_fragment_container, vConstructorsFragment, CONSTRUCTORS_RANKING_FRAGMENT);
        vFT.commit();
    }

}
