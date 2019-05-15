package com.example.growingmobilef1.Fragment_Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.growingmobilef1.MainActivity;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;

public class RaceDetailActivity extends AppCompatActivity {

    public static String FRAGMENT_TAG = "RaceFragment";
    RaceDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Races raceItem = new Races();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_detail);

        //
        Intent intent = getIntent();
        Bundle startBundle = intent.getExtras();

        if(startBundle != null){
            raceItem = (Races) startBundle.getSerializable(RaceDetailFragment.RACE_ITEM);
        }

        // Set the Action Bar back button and the title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(raceItem.getRaceName());

        fragment = (RaceDetailFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if(fragment == null){

            FragmentTransaction vFT = getFragmentManager().beginTransaction();

            fragment = RaceDetailFragment.newInstance(raceItem);

            vFT.replace(R.id.container_race_fragment, fragment, FRAGMENT_TAG);
            vFT.commit();

        }
    }

    /**
     * Back to Main Activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(RaceDetailActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
