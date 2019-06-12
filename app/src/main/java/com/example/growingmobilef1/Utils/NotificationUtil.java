package com.example.growingmobilef1.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.growingmobilef1.AlertReceiver;
import com.example.growingmobilef1.Model.Races;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class NotificationUtil {

    public static final String RACE_ALERT = "Tag to send the race item to the AlertReceiver";

    private Date mDate;
    private Context mContext;
    private Races mRace;

    public NotificationUtil(Date aDate, Context context, Races race){
        this.mDate = aDate;
        this.mContext = context;
        this.mRace = race;
    }

    public void sendNotification(){

        AlarmManager vAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent vNotificationIntent = new Intent(mContext, AlertReceiver.class);

        // Trasform the CalendarRaceItem object in a byte array to send it to the AlertReceiver
        Bundle vBundle = new Bundle();
        ByteArrayOutputStream vBiteArrayOutStream = new ByteArrayOutputStream();
        ObjectOutputStream vOutputStream = null;
        try {
            vOutputStream = new ObjectOutputStream(vBiteArrayOutStream);
            vOutputStream.writeObject(mRace);
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

        PendingIntent vBroadcast = PendingIntent.getBroadcast(mContext,
                100,
                vNotificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if (!mRace.getNotificationScheduled()){
            Date vDate = new Date();
            vDate.setTime(new Date().getTime());
            vDate.setMinutes(vDate.getMinutes() + 1);
           // mDate.setMinutes(mDate.getMinutes() - 10);
            vAlarmManager.setExact(AlarmManager.RTC_WAKEUP, vDate.getTime(), vBroadcast);
            mRace.setNotificationScheduled(true);
            Toast.makeText(mContext, "Notification scheduled for " + mDate, Toast.LENGTH_LONG).show();

        } else {
            vAlarmManager.cancel(vBroadcast);
            mRace.setNotificationScheduled(false);
            Toast.makeText(mContext, "Notification cancelled", Toast.LENGTH_LONG).show();
        }
    }

}
