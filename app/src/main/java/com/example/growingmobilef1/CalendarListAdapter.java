package com.example.growingmobilef1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarListAdapter extends BaseAdapter {

    private ArrayList<CalendarRaceItem> mCalendarRaceItemArrayList;

    public CalendarListAdapter(ArrayList<CalendarRaceItem> aCalendarRaceItemArrayList) {
        mCalendarRaceItemArrayList = aCalendarRaceItemArrayList;
    }

    @Override
    public int getCount() {
        return mCalendarRaceItemArrayList.size();
    }

    @Override
    public CalendarRaceItem getItem(int position) {
        return mCalendarRaceItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mCalendarRaceItemArrayList.get(position).getmId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vView;
        if (convertView == null){
            LayoutInflater vInflater = LayoutInflater.from(parent.getContext());
            vView = vInflater.inflate(R.layout.list_item_calendar, parent, false);
            ViewHolder vViewHolder = new ViewHolder();
            vViewHolder.mCircuitRaceNameLabel = vView.findViewById(R.id.list_item_calendar_label_race_name);
            vViewHolder.mDateLabel = vView.findViewById(R.id.list_item_calendar_label_date);
            vViewHolder.mHourLabel = vView.findViewById(R.id.list_item_calendar_label_hour);

            vView.setTag(vViewHolder);

        } else
            vView = convertView;

        ViewHolder vHolder = (ViewHolder)vView.getTag();

        vHolder.mCircuitRaceNameLabel.setText("" + getItem(position).getmRaceName());
        vHolder.mDateLabel.setText(getItem(position).getmDate());
        vHolder.mHourLabel.setText("" + getItem(position).getmTime());

        return vView;
    }

    private class ViewHolder {
        TextView mCircuitRaceNameLabel, mDateLabel, mHourLabel;
    }
}
