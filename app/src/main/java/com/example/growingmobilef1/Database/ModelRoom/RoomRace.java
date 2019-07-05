package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.Model.Races;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Entity(tableName = "race")
public class RoomRace implements IListableModel, Serializable {

    @PrimaryKey
    @NonNull
    public String circuitId;

    public int round;

    public String name;
    public String dateTime;
    public int notification;

    public Races toRace(){

        Races temp = new Races();
        temp.setRaceName(name);
        temp.setDateTime(Races.getCalendarDate(dateTime));
        temp.setNotificationScheduled(notification == 1);

        return temp;
    }

    /**
     *
     * @return
     */
    public Calendar dateToCalendar(){

        // Istanza di Calendar
        Calendar calendar = Calendar.getInstance();

        // DateFormat con la TimeZone
        SimpleDateFormat tempFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tempFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        // Istanza di Date
        Date tempDate = new Date();

        // Parsing della data in Date
        try {
            tempDate = tempFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Da Date a Calendar
        calendar.setTime(tempDate);

        return calendar;
    }

    public String qualifyingDate(){

        String temp;
        Calendar today = dateToCalendar();
        today.add(Calendar.DATE, -1);
        temp = today.get(Calendar.DAY_OF_MONTH) + " " +
                today.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        return temp;
    }
}
