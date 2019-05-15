package com.example.growingmobilef1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.growingmobilef1.Interface.IListableObject;
import com.example.growingmobilef1.Model.CalendarRaceItem;
import com.example.growingmobilef1.R;

import java.util.ArrayList;

public class ListableObjectsAdapter extends BaseAdapter {

    private ArrayList<IListableObject> mListableItemArrayList;

    public ListableObjectsAdapter(ArrayList<IListableObject> aCalendarRaceItemArrayList) {
        mListableItemArrayList = aCalendarRaceItemArrayList;
    }

    @Override
    public int getCount() {
        return mListableItemArrayList.size();
    }

    @Override
    public IListableObject getItem(int position) {
        return mListableItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mListableItemArrayList.get(position).getmId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vView;
        if (convertView == null){
            LayoutInflater vInflater = LayoutInflater.from(parent.getContext());

            // Check which cell layout to load

            ViewHolder vViewHolder = new ViewHolder();

            //if (getItem(position).isButtonRequired()) {
                vView = vInflater.inflate(R.layout.list_item_calendar, parent, false);

                vViewHolder.mMainInformationLabel = vView.findViewById(R.id.list_item_calendar_label_race_name);
                vViewHolder.mOptionalInformationLabel = vView.findViewById(R.id.list_item_calendar_label_date);
                vViewHolder.mSecondaryInformationLabel = vView.findViewById(R.id.list_item_calendar_label_hour);

            /*} else {
                vView = vInflater.inflate(R.layout.list_item_ranking, parent, false);

                vViewHolder.mMainInformationLabel = vView.findViewById(R.id.labelConstructorName);
                vViewHolder.mOptionalInformationLabel = vView.findViewById(R.id.labelConstructorScore);
            }*/


            vView.setTag(vViewHolder);

        } else
            vView = convertView;

        ViewHolder vHolder = (ViewHolder)vView.getTag();

        vHolder.mMainInformationLabel.setText("" + getItem(position).getmMainInformation());
        vHolder.mOptionalInformationLabel.setText(getItem(position).getmOptionalInformation());
        vHolder.mSecondaryInformationLabel.setText("" + getItem(position).getmSecondaryInformation());

        return vView;
    }

    private class ViewHolder {
        TextView mMainInformationLabel, mOptionalInformationLabel, mSecondaryInformationLabel;
    }
}
