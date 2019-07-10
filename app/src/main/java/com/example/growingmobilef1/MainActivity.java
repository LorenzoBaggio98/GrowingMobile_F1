package com.example.growingmobilef1;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

//import com.crashlytics.android.Crashlytics;
import com.example.growingmobilef1.Fragment_Activity.CalendarFragment;
import com.example.growingmobilef1.Fragment_Activity.ConstructorsRankingFragment;
import com.example.growingmobilef1.Fragment_Activity.DriversRankingFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends SplashActivity {

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
        //mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(mToolbar);

     /*  if( FirebaseAuth.getInstance().getCurrentUser() != null)
       {
           Log.d("TAG",FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString());
          // Crashlytics.setUserIdentifier();
       }
*/

        if (savedInstanceState == null){
            launchFragment(CALENDAR_FRAGMENT, CalendarFragment.newInstance());
        }
        else {
            getSupportActionBar().setTitle(savedInstanceState.getString(SAVED_TITLE));
        }

        setNavMenuItemThemeColors();

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // set dialog profile
        //myDialog = new Dialog(this);
        //printKeyHash();
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

    public void setNavMenuItemThemeColors() {
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

    // Load the notification icon only if the race hasn't happened yet
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);

        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Back to Main Activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                super.signOut();
                //ShowProfilePopup(getCurrentFocus());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    // facebook hash
    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().
                    getPackageInfo("com.example.growingmobilef1", PackageManager.GET_SIGNATURES);

            for(Signature signature:info.signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.e("KEYHASH", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
