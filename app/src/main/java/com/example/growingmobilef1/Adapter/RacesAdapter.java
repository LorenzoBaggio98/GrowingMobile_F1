package com.example.growingmobilef1.Adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.growingmobilef1.Model.RaceResults;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.time.Instant;

public class RacesAdapter extends BaseAdapter {

    private ArrayList<Races> mRacesArrayList;
    private Map<String, ArrayList<RaceResults>> mRaceResultsMap;

    public RacesAdapter(ArrayList<Races> aRacesArrayList, Map<String, ArrayList<RaceResults>> aRaceResultsMap) {
        mRacesArrayList = aRacesArrayList;
        mRaceResultsMap = aRaceResultsMap;
    }

    @Override
    public int getCount() {
        return mRacesArrayList.size();
    }

    @Override
    public Races getItem(int position) {
        return mRacesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mRacesArrayList.get(position).getmId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vView;
        if (convertView == null){
            LayoutInflater vInflater = LayoutInflater.from(parent.getContext());

            ViewHolder vViewHolder = new ViewHolder();
            vView = vInflater.inflate(R.layout.list_item_calendar, parent, false);

            vViewHolder.mRaceLabel = vView.findViewById(R.id.list_item_calendar_label_race_name);
            vViewHolder.mPodiumLabel = vView.findViewById(R.id.list_item_calendar_label_podium);
            vViewHolder.mDateLabel = vView.findViewById(R.id.list_item_calendar_label_date);
            vViewHolder.mTimeLabel = vView.findViewById(R.id.list_item_calendar_label_hour);
            vViewHolder.mContainerLayout = vView.findViewById(R.id.list_item_calendar_container);

            vView.setTag(vViewHolder);

        } else
            vView = convertView;

        ViewHolder vHolder = (ViewHolder)vView.getTag();

        // ----------------CORVAGLIA
        // Change list item layout if race has already happened
        /*Calendar vCalendarConvertRaceDate = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));

        vCalendarConvertRaceDate.setTime(mRacesArrayList.get(position).getTimezoneDate());

        long raceMilliSecondDate = vCalendarConvertRaceDate.getTimeInMillis();
        Calendar vCalendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
        vCalendar.setTime(vCalendar.getTime());

        // CONTROL IF PAST
        if (raceMilliSecondDate > vCalendar.getTimeInMillis()) {
            vHolder.mPodiumLabel.setTextColor(Color.BLACK);
            vHolder.mRaceLabel.setTextColor(Color.BLACK);
            vHolder.mDateLabel.setTextColor(Color.BLACK);
            vHolder.mTimeLabel.setTextColor(Color.BLACK);
            vHolder.mContainerLayout.setBackgroundResource(R.drawable.rectangle_shadow);
        } else {
            vHolder.mPodiumLabel.setTextColor(Color.WHITE);
            vHolder.mRaceLabel.setTextColor(Color.WHITE);
            vHolder.mDateLabel.setTextColor(Color.WHITE);
            vHolder.mTimeLabel.setTextColor(Color.WHITE);
            vHolder.mContainerLayout.setBackgroundResource(R.drawable.last_race_rectangle);
        }*/

        // PODIUM
        // Set the podium results (if the race has already occurred)
        //String vPositionLabelString = "";
        /*if (mRaceResultsMap.containsKey(getItem(position).getRaceName())) {
            for (int i = 0; i < 3; i++){
                String vPosition = mRaceResultsMap.get(getItem(position).getRaceName()).get(i).getDriver().getCode();
                if (i < 2)
                    vPositionLabelString += vPosition + " / ";
                else
                    vPositionLabelString += vPosition;
            }
        }*/
        //vHolder.mPodiumLabel.setText(vPositionLabelString);
        /// --- DATE ---

        //Calendar vCalendarDate = getItem(position).getCalendarDate();

        // Set date and time of the race
        //vHolder.mDateLabel.setText(vCalendarConvertRaceDate.get(Calendar.DAY_OF_MONTH) + " " + vCalendarConvertRaceDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));

        /// --- TIME ---
        //Calendar vCalendarTime = getItem(position).getCalendarTime();
        //vHolder.mTimeLabel.setText(vCalendarConvertRaceDate.get(Calendar.HOUR_OF_DAY) + ":" + vCalendarConvertRaceDate.get(Calendar.MINUTE));


        // --------------- MIO

        Date raceDate = mRacesArrayList.get(position).getDate();
        LocalDateTime localDate = raceDate.toInstant().atZone(ZoneId.of("Europe/Rome")).toLocalDateTime();

        long raceMilliSecondDate = raceDate.getTime();

        // CONTROL IF PAST
        if (raceMilliSecondDate > new Date().getTime()) {
            vHolder.mPodiumLabel.setTextColor(Color.BLACK);
            vHolder.mRaceLabel.setTextColor(Color.BLACK);
            vHolder.mDateLabel.setTextColor(Color.BLACK);
            vHolder.mTimeLabel.setTextColor(Color.BLACK);
            vHolder.mContainerLayout.setBackgroundResource(R.drawable.rectangle_shadow);
        } else {
            vHolder.mPodiumLabel.setTextColor(Color.WHITE);
            vHolder.mRaceLabel.setTextColor(Color.WHITE);
            vHolder.mDateLabel.setTextColor(Color.WHITE);
            vHolder.mTimeLabel.setTextColor(Color.WHITE);
            vHolder.mContainerLayout.setBackgroundResource(R.drawable.last_race_rectangle);
        }

        vHolder.mRaceLabel.setText("" + getItem(position).getmMainInformation());

        // PODIUM
        // Set the podium results (if the race has already occurred)
        String vPositionLabelString = "";
        if (mRaceResultsMap.containsKey(getItem(position).getRaceName())) {
            for (int i = 0; i < 3; i++){
                String vPosition = mRaceResultsMap.get(getItem(position).getRaceName()).get(i).getDriver().getCode();
                if (i < 2)
                    vPositionLabelString += vPosition + " / ";
                else
                    vPositionLabelString += vPosition;
            }
        }
        vHolder.mPodiumLabel.setText(vPositionLabelString);

        /// --- DATE ---
        // Set date and time of the race
        vHolder.mDateLabel.setText(""+localDate.getDayOfMonth());


        /// --- TIME ---
        vHolder.mTimeLabel.setText(localDate.getHour()+ ":" + localDate.getMinute());


        return vView;
    }

    private class ViewHolder {
        TextView mRaceLabel, mPodiumLabel, mDateLabel, mTimeLabel;
        LinearLayout mContainerLayout;
    }
}
