package com.example.growingmobilef1.Fragment_Activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.growingmobilef1.Database.ModelRoom.RoomDriver;
import com.example.growingmobilef1.Model.Driver;
import com.example.growingmobilef1.R;

import java.util.Calendar;

public class DriverDetailActivity extends AppCompatActivity {

    private static final String DETAILDRIVER = "DETAIL DRIVER";
    private static final String TIME ="Time" ;
   // private static final String PROVA ="WWW" ;
    private DriverDetailFragment mDriverDetailFragment;
    private static final String FRAGMENT_DETAGLIO="FRAGMENT_DETAGLIO";
    private RoomDriver mDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_detail);



        Bundle vBundle=getIntent().getExtras();
        if (vBundle !=null){
           mDriver = (RoomDriver) vBundle.getSerializable("SAVE_ID");
        }

        if (savedInstanceState ==null){
            FragmentTransaction vtF=getSupportFragmentManager().beginTransaction();
            mDriverDetailFragment = DriverDetailFragment.newinstance(mDriver);
            vtF.add(R.id.containerFragmentDrivers, mDriverDetailFragment,FRAGMENT_DETAGLIO);
            vtF.commit();
        }else{
            mDriverDetailFragment= (DriverDetailFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_DETAGLIO);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mDriver.name);



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            //    Crashlytics.setString(PROVA," prova crashylitics");
//Crashlytics.getInstance().crash();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
