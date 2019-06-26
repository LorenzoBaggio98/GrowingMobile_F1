package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.growingmobilef1.Model.Races;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "race")
public class RoomRace {

    @PrimaryKey
    @NonNull
    public String circuitId;

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
}