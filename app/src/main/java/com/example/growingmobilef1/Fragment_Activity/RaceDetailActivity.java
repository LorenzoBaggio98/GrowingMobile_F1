package com.example.growingmobilef1.Fragment_Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;


import com.example.growingmobilef1.Adapter.ViewPagerAdapter;
import com.example.growingmobilef1.MainActivity;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;

/**
 * Activity of the Race Detail -
 *
 */
import java.io.IOException;
import java.io.InputStream;

public class RaceDetailActivity extends AppCompatActivity {
    private static final String ERROR_TAG = "ERROR_TAG";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private  ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Races raceItem = new Races();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_detail);

        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        mImageView = (ImageView)findViewById(R.id.circuit_img);

        Intent intent = getIntent();
        Bundle startBundle = intent.getExtras();

        if(startBundle != null){
            raceItem = (Races) startBundle.getSerializable(RaceDetailFragment.RACE_ITEM);
        }

        // Set the tabBar with ViewPageAdapter and TabLayout
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment("FP", RaceDetailFragment.newInstance(raceItem));
        viewPagerAdapter.addFragment("QUALI", new TwoFragmentDetail());
        viewPagerAdapter.addFragment("RACE", new ThreeFragmentDetail());

        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        // Set the Action Bar back button and the title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(raceItem.getRaceName());

        // Set the image circuit
        try {

            String vCircuitId = raceItem.getCircuit().getCircuitId();

            // get input stream
            InputStream ims = getApplicationContext().getAssets().open("circuits/" + vCircuitId + ".png");

            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);

            // set image to ImageView
            mImageView.setImageDrawable(d);
            ims .close();

        } catch(IOException ex) {
            Log.e(ERROR_TAG,"Error on circuit image reading");
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
