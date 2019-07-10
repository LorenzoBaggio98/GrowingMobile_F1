package com.example.growingmobilef1.Fragment_Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ImageViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.growingmobilef1.Adapter.ViewPagerAdapter;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ViewModel.RacesViewModel;
import com.example.growingmobilef1.MainActivity;
import com.example.growingmobilef1.Model.RaceResults;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;
import com.example.growingmobilef1.Utils.LayoutAnimations;
import com.example.growingmobilef1.Utils.NotificationUtil;

/**
 * Activity of the Race Detail -
 *
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

public class RaceDetailActivity extends AppCompatActivity {

    public static final String RACE_ITEM = "Tag to pass the calendar race item to the fragment";
    public static final String REMOVE_NOTIFICATION = "The activity was opened from a notification, remove that from the db";

    private static final String ERROR_TAG = "ERROR_TAG";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView mImageView;
    private Toolbar mToolbar;
    private RoomRace mRace;
    private NotificationUtil mNotificationUtil;
    private Button mLogOutButton;

    ViewPagerAdapter mPageAdapter;
    private int mNotificationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_detail);
        mRace = new RoomRace();

        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        mImageView = findViewById(R.id.circuit_img);
        mToolbar = findViewById(R.id.toolbar);

        Intent intent = getIntent();
        Bundle startBundle = intent.getExtras();
        if (startBundle != null) {
            mRace = (RoomRace) startBundle.getSerializable(RaceDetailActivity.RACE_ITEM);
            mNotificationStatus = startBundle.getInt(REMOVE_NOTIFICATION);

            updateNotificationStatus(mNotificationStatus);
        }

        ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
        layoutParams.height = (int)getApplicationContext().getResources().getDimension(R.dimen.TabLayout_height);
        mToolbar.setLayoutParams(layoutParams);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        // Set the tabBar with ViewPageAdapter and TabLayout
        mPageAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mPageAdapter.addFragment("Race", RaceResultsFragment.newInstance(mRace));
        mPageAdapter.addFragment("Qualifying", QualifyingResultsFragment.newInstance(mRace));

        setPagerAdapter();

        // Set the Action Bar back button and the title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mRace.name);

        // Set the image circuit
        try {
            String vCircuitId = mRace.circuitId;

            // get input stream
            InputStream ims = getApplicationContext().getAssets().open("circuits/" + vCircuitId + ".png");

            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mImageView.setImageDrawable(d);
            ims.close();

        } catch (IOException ex) {
            Log.e(ERROR_TAG, "Error on circuit image reading");
        }
    }

    // Load the notification icon only if the race hasn't happened yet
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Calendar vDate =  Calendar.getInstance();
        vDate.setTime(Calendar.getInstance().getTime());

        if (mRace.dateToCalendar().after(vDate)) {
            getMenuInflater().inflate(R.menu.race_detail_notification, menu);
            Drawable drawable = menu.getItem(0).getIcon();
            drawable.mutate();
            drawable.setColorFilter(getResources().getColor(R.color.colorSecondaryLight), PorterDuff.Mode.SRC_ATOP);
        }

        return super.onCreateOptionsMenu(menu);
    }*/

    /**
     * Back to Main Activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* switch (item.getItemId()) {
            case android.R.id.home: */

                Intent intent = new Intent(RaceDetailActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;

        /*    case R.id.race_detail_notification:
                // todo
                //mNotificationUtil = new NotificationUtil(mRace.dateToCalendar(), this, mRace);
                manageNotificationIconColor(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
    }

    // TODO: Manage the notificaiton icon colors - need to save the scheduled notifications somewhere
    private void manageNotificationIconColor(MenuItem item){
        Drawable drawable = item.getIcon();
        drawable.mutate();
        if (mRace.notification == 1)
            drawable.setColorFilter(getResources().getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP);
        else
            drawable.setColorFilter(getResources().getColor(R.color.colorSecondaryLight), PorterDuff.Mode.SRC_ATOP);
    }

    private void updateNotificationStatus(int aNotificationStatus){
        RacesViewModel vRacesViewModel = new RacesViewModel(getApplication());
        vRacesViewModel.updateRaceNotification(mRace, aNotificationStatus);
    }

    private void setPagerAdapter(){
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
