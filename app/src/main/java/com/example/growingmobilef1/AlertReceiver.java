package com.example.growingmobilef1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.growingmobilef1.Database.FormulaRepository;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ViewModel.RacesViewModel;
import com.example.growingmobilef1.Fragment_Activity.RaceDetailActivity;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.Utils.NotificationUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Date;

public class AlertReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "channelId";
    private static final String ALERT_RACE_DETAIL_FRAGMENT = "Tag to send the race item to the detail fragment";

    private RoomRace mRaceItem;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent vNotificationIntent = new Intent(context, RaceDetailActivity.class);

        // Get the Race detail object as an array of bytes from the bundle, then cast it to the proper object
        Bundle vStartBundle = intent.getExtras();
        if (vStartBundle != null) {
            ByteArrayInputStream vByteArrayInput = new ByteArrayInputStream(intent.getByteArrayExtra(NotificationUtil.RACE_ALERT));
            ObjectInput vObjectInput = null;
            try {
                vObjectInput = new ObjectInputStream(vByteArrayInput);
                mRaceItem = (RoomRace) vObjectInput.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (vObjectInput != null) {
                        vObjectInput.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            // Pass the Race detail object to the activity opened on notification click
            Bundle vRaceFragmentBundle = new Bundle();
            vRaceFragmentBundle.putSerializable(RaceDetailActivity.RACE_ITEM, mRaceItem);
            vRaceFragmentBundle.putInt(RaceDetailActivity.REMOVE_NOTIFICATION, 0);
            vNotificationIntent.putExtras(vRaceFragmentBundle);
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(vNotificationIntent);

        PendingIntent vPendingIntent = stackBuilder.getPendingIntent(mRaceItem.round, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder vNotificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("GrowingMobile F1")
                .setContentText(mRaceItem.name + " starting in 10 minutes!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(vPendingIntent)
                .setGroup("GrowingMobileFormula1")
                .setGroupSummary(true)
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationF1",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Create a random not repeatable number to display multiple notifications
        int vRandomNotRepeatable = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(vRandomNotRepeatable, vNotificationBuilder.build());

        // set the notification in the database to 0
        mRaceItem.notification = 0;
    }

}
