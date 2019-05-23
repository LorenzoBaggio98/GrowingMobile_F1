package com.example.growingmobilef1;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.example.growingmobilef1.Fragment_Activity.CalendarFragment;
import com.example.growingmobilef1.Fragment_Activity.ConstructorsRankingFragment;
import com.example.growingmobilef1.Fragment_Activity.DriversRankingFragment;

public class MainActivity extends AppCompatActivity {

    private static final String CALENDAR_FRAGMENT = "Calendar";
    private static final String PILOTS_RANKING_FRAGMENT = "Pilots";
    private static final String CONSTRUCTORS_RANKING_FRAGMENT = "Constructors";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab_bar_calendar:
                    launchFragment(CALENDAR_FRAGMENT, CalendarFragment.newInstance());
                    return true;
                case R.id.tab_bar_pilots_ranking:
                    launchFragment(PILOTS_RANKING_FRAGMENT, DriversRankingFragment.newInstance());
                    return true;
                case R.id.tab_bar_constructors_ranking:
                    launchFragment(CONSTRUCTORS_RANKING_FRAGMENT, ConstructorsRankingFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.main_act_nav_view);

        launchFragment(CALENDAR_FRAGMENT, CalendarFragment.newInstance());
        setNavMenuItemThemeColors();

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void launchFragment(String tag, Fragment aFragment){

        // Set the Action Bar title
        getSupportActionBar().setTitle(tag);

        // Set the correct fragment
        FragmentTransaction vFT = getFragmentManager().beginTransaction();
        vFT.replace(R.id.main_act_fragment_container, aFragment, tag);
        vFT.commit();
    }

    public void setNavMenuItemThemeColors(){
        BottomNavigationView vNavView = findViewById(R.id.main_act_nav_view);
        //Setting default colors for menu item Text and Icon
        int navPrimaryIconColor = getColor(R.color.colorPrimary);
        int navBlackTextColor = getColor(R.color.colorBlack);

        //Defining ColorStateList for menu item Text
        ColorStateList navMenuTextList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{-android.R.attr.state_checked},
                },
                new int[] {
                        navPrimaryIconColor,
                        navBlackTextColor,
                        navBlackTextColor,
                        navBlackTextColor,
                        navBlackTextColor,
                        navBlackTextColor
                }
        );

        //Defining ColorStateList for menu item Icon
        ColorStateList navMenuIconList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{-android.R.attr.state_checked},
                },
                new int[] {
                        navPrimaryIconColor,
                        navBlackTextColor,
                        navBlackTextColor,
                        navBlackTextColor,
                        navBlackTextColor,
                        navBlackTextColor
                }
        );

        vNavView.setItemTextColor(navMenuTextList);
        vNavView.setItemIconTintList(navMenuIconList);
    }
}
