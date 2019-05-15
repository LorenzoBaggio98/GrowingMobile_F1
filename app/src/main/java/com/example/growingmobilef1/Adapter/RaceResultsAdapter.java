package com.example.growingmobilef1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.growingmobilef1.Model.RaceResults;
import com.example.growingmobilef1.R;

import java.util.ArrayList;

public class RaceResultsAdapter extends BaseAdapter {

    private ArrayList<RaceResults> dataList;

    public RaceResultsAdapter(ArrayList<RaceResults> data){
        this.dataList = data;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public RaceResults getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataList.get(position).getPosition();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View cellView;
        if(convertView == null){

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            cellView = inflater.inflate(R.layout.list_item_race_result, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txt_position = cellView.findViewById(R.id.txt_race_result_position);
            viewHolder.txt_driver = cellView.findViewById(R.id.txt_race_result_driver);
            viewHolder.txt_time = cellView.findViewById(R.id.txt_race_result_time);
            viewHolder.txt_time_sep = cellView.findViewById(R.id.txt_race_result_time_sep);

            cellView.setTag(viewHolder);

        }else{
            cellView = convertView;
        }

        ViewHolder vHolder = (ViewHolder) cellView.getTag();
        RaceResults tempItem = getItem(position);

        vHolder.txt_position.setText(""+tempItem.getPosition());
        vHolder.txt_driver.setText(tempItem.getDriver().getDriverId());
        vHolder.txt_time.setText(tempItem.getTime().getTime() != null ? tempItem.getTime().getTime() : "");

        if(tempItem.getPosition() != 1){
            vHolder.txt_time_sep.setText(tempItem.getTime().getTime());
        }

        return cellView;
    }

    private class ViewHolder{

        TextView txt_position;
        TextView txt_driver;
        TextView txt_time;
        TextView txt_time_sep;
    }
}
