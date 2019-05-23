package com.example.growingmobilef1.Fragment_Activity;

import android.support.v4.app.Fragment;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.AlertReceiver;
import com.example.growingmobilef1.R;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;

public class RaceDetailFragment extends Fragment {

    public interface OnFragmentLoad{
        void onFragmentLoaded();
    }
    private OnFragmentLoad mListener;

    public static final String RESULTS_FRAGMENT = "ResultsFragment";
    public static final String RACE_ITEM = "Tag to pass the calendar race item to the fragment";
    public static final String RACE_ALERT = "Tag to send the race item to the AlertReceiver";

    private RaceResultsFragment mListResultsFragment;
    // The race's info
    private Races mCalendarRace;
    private Button mNotificationButton;
    private Date mRaceDate;

    public static RaceDetailFragment newInstance(Races aCalendarRaceItem) {

        Bundle vBundle = new Bundle();
        vBundle.putSerializable(RACE_ITEM, aCalendarRaceItem);

        RaceDetailFragment vRaceDetailFrag = new RaceDetailFragment();
        vRaceDetailFrag.setArguments(vBundle);
        return vRaceDetailFrag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_race_detail, container, false);

        mNotificationButton = vView.findViewById(R.id.btn_race_notify);


        Bundle vStartingBundle = getArguments();
        if (vStartingBundle != null) {

            // Item passed on CalendarList's click
            mCalendarRace = (Races)vStartingBundle.getSerializable(RACE_ITEM);
            // Get the race time
            mRaceDate = mCalendarRace.getmDate();

            // Check if the race occurred, in case disable the notification button
            checkDate(mRaceDate);
        }

        // Inizialize the Results List Fragment
        mListResultsFragment = (RaceResultsFragment) getChildFragmentManager().findFragmentByTag(RESULTS_FRAGMENT);

        if(mListResultsFragment == null){
            launchRaceResultsFragments();

            mListener.onFragmentLoaded();
        }

        mNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarRace.setNotificationScheduled(!mCalendarRace.getNotificationScheduled());
                sendNotification(mRaceDate);
                changeNotificationButtonLabel(mCalendarRace.getNotificationScheduled());
            }
        });

        return vView;
    }

    private void launchRaceResultsFragments(){
        FragmentTransaction vFT = getChildFragmentManager().beginTransaction();
        mListResultsFragment = RaceResultsFragment.newInstance(mCalendarRace);

        vFT.replace(R.id.container_race_results, mListResultsFragment, RESULTS_FRAGMENT);
        vFT.commit();

    }

    private void sendNotification(Date aDate){

        AlarmManager vAlarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent vNotificationIntent = new Intent(getContext(), AlertReceiver.class);

        // Trasform the CalendarRaceItem object in a byte array to send it to the AlertReceiver
        Bundle vBundle = new Bundle();
        ByteArrayOutputStream vBiteArrayOutStream = new ByteArrayOutputStream();
        ObjectOutputStream vOutputStream = null;
        try {
            vOutputStream = new ObjectOutputStream(vBiteArrayOutStream);
            vOutputStream.writeObject(mCalendarRace);
            vOutputStream.flush();
            byte[] data = vBiteArrayOutStream.toByteArray();
            vNotificationIntent.putExtra(RACE_ALERT, data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                vBiteArrayOutStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        vNotificationIntent.putExtras(vBundle);

        PendingIntent vBroadcast = PendingIntent.getBroadcast(getContext(), 100, vNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (mCalendarRace.getNotificationScheduled()){
            aDate.setMinutes(aDate.getMinutes() - 10);

            vAlarmManager.setExact(AlarmManager.RTC_WAKEUP, aDate.getTime(), vBroadcast);
            Toast.makeText(getContext(), "Notification scheduled for " + aDate, Toast.LENGTH_LONG).show();

        } else {
            vAlarmManager.cancel(vBroadcast);
            Toast.makeText(getContext(), "Notification cancelled", Toast.LENGTH_LONG).show();
        }
    }

    // Disable the notification button if the race already occured
    private void checkDate(Date aDate){
        Date vCurrentDate = Calendar.getInstance().getTime();

        if (vCurrentDate.after(aDate)) {
            mNotificationButton.setText("Race already occurred");
            mNotificationButton.setEnabled(false);
        }
    }

    // Swap the notification button label from remind to cancel notification
    private void changeNotificationButtonLabel(Boolean isNotificationSchedule){

        if (isNotificationSchedule)
            mNotificationButton.setText("Cancel notification");
        else
            mNotificationButton.setText("Remind me");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentLoad){
            mListener = (OnFragmentLoad)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}

