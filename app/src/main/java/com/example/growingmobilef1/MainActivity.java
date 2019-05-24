package com.example.growingmobilef1;
import android.content.res.ColorStateList;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
    private static final String SAVED_TITLE = "title of the support action bar";

    private String mSupportActionBarTitle;

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
    if (savedInstanceState == null){
        launchFragment(CALENDAR_FRAGMENT, CalendarFragment.newInstance());
    }
   else {
        getSupportActionBar().setTitle(savedInstanceState.getString(SAVED_TITLE));
   }

        setNavMenuItemThemeColors();

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSupportActionBarTitle= getSupportActionBar().getTitle().toString();

        outState.putString(SAVED_TITLE, mSupportActionBarTitle);
    }

    private void launchFragment(String tag, Fragment aFragment){

        // Set the Action Bar title
        getSupportActionBar().setTitle(tag);

        // Set the correct fragment
        FragmentTransaction vFT = getSupportFragmentManager().beginTransaction();
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
