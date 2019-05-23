package com.example.growingmobilef1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.growingmobilef1.Interface.IListableObject;
import com.example.growingmobilef1.Model.CalendarRaceItem;
import com.example.growingmobilef1.Model.RaceResults;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

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
            Calendar vCalendar = Calendar.getInstance();
            vCalendar.setTime(vCalendar.getTime());
           /* if (mRaceResultsMap.get(getItem(position).getRaceName()).size() != 0) {
                vView = vInflater.inflate(R.layout.list_item_calendar_last_race, parent, false);
            }else {*/
                vView = vInflater.inflate(R.layout.list_item_calendar, parent, false);
          //  }

            vViewHolder.mRaceLabel = vView.findViewById(R.id.list_item_calendar_label_race_name);
            vViewHolder.mPodiumLabel = vView.findViewById(R.id.list_item_calendar_label_podium);
            vViewHolder.mDateLabel = vView.findViewById(R.id.list_item_calendar_label_date);
            vViewHolder.mTimeLabel = vView.findViewById(R.id.list_item_calendar_label_hour);

            vView.setTag(vViewHolder);

        } else
            vView = convertView;

        ViewHolder vHolder = (ViewHolder)vView.getTag();

        Calendar vCalendarDate = getItem(position).getCalendarDate();
        Calendar vCalendarTime = getItem(position).getCalendarTime();
        int vCalendarMonth = vCalendarDate.get(Calendar.MONTH);

        vHolder.mRaceLabel.setText("" + getItem(position).getmMainInformation());

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

        if (vCalendarMonth != 10 && vCalendarMonth != 11 && vCalendarMonth != 12){
            vHolder.mDateLabel.setText(vCalendarDate.get(Calendar.DAY_OF_MONTH) + " " + vCalendarDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));
        } else {
            vHolder.mDateLabel.setText(vCalendarDate.get(Calendar.DAY_OF_MONTH) + " " + vCalendarDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));
        }
        vHolder.mTimeLabel.setText(vCalendarTime.get(Calendar.HOUR_OF_DAY) + ":" + vCalendarTime.get(Calendar.MINUTE));

        return vView;
    }

    private class ViewHolder {
        TextView mRaceLabel, mPodiumLabel, mDateLabel, mTimeLabel;
    }
}
