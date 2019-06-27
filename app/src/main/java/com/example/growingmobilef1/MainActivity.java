package com.example.growingmobilef1;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.ColorStateList;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.growingmobilef1.Fragment_Activity.CalendarFragment;
import com.example.growingmobilef1.Fragment_Activity.ConstructorsRankingFragment;
import com.example.growingmobilef1.Fragment_Activity.DriversRankingFragment;
import com.example.growingmobilef1.Fragment_Activity.LoginActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private static final String CALENDAR_FRAGMENT = "Calendar";
    private static final String PILOTS_RANKING_FRAGMENT = "Pilots";
    private static final String CONSTRUCTORS_RANKING_FRAGMENT = "Constructors";
    private static final String SAVED_TITLE = "title of the support action bar";

    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener  mAuthStateListener;
    private String mSupportActionBarTitle;
    private Toolbar mToolbar;
    private Dialog myDialog;

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

        // firebase auth
       /* mFirebaseAuth = FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser()==null){
            Intent vIntent = new Intent(getApplicationContext(), LoginActivity.class);
            vIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(vIntent);
            finish();
        }*/

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        };


        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.main_act_nav_view);
        //mToolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(mToolbar);

        if (savedInstanceState == null){
            launchFragment(CALENDAR_FRAGMENT, CalendarFragment.newInstance());
        }
        else {
            getSupportActionBar().setTitle(savedInstanceState.getString(SAVED_TITLE));
        }


        setNavMenuItemThemeColors();

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // set dialog profile
        myDialog = new Dialog(this);
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


    @Override
    protected void onStart() {
        super.onStart();
        // check auth
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener!=null){
            // remove auth listener
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

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
                signOut();
                //ShowProfilePopup(getCurrentFocus());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void ShowProfilePopup(View v) {
        TextView txtclose;
        Button btnLogout;
        myDialog.setContentView(R.layout.profile_popup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("M");
        btnLogout = (Button) myDialog.findViewById(R.id.btnLogout);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut(); // facebook logout, fatto bene
    }
}
