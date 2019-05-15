package com.example.growingmobilef1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.growingmobilef1.Model.DriverStandings;
import com.example.growingmobilef1.R;

import java.util.ArrayList;

public class DriversAdapter extends BaseAdapter {
    private ArrayList<DriverStandings> mArrayListDrivers;

    public DriversAdapter(ArrayList<DriverStandings> mArrayListDrivers) {
        this.mArrayListDrivers = mArrayListDrivers;
    }

    @Override
    public int getCount() {
        return mArrayListDrivers.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayListDrivers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mArrayListDrivers.get(position).getDriver().getPermanentNumber();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vView;
        ViewHolder vHolder;

        if (convertView == null){
            LayoutInflater vInflater = LayoutInflater.from(parent.getContext());

            vView = vInflater.inflate(R.layout.list_item_drivers, parent,false);
            vHolder = new ViewHolder();

            vHolder.mtxtName = vView.findViewById(R.id.textViewNameDriver);
            vHolder.mtxtSurnale = vView.findViewById(R.id.textViewSurnameDriver);
            vHolder.mtxtPoints = vView.findViewById(R.id.textViewPointsDriver);

            vView.setTag(vHolder);

        } else{
            vView = convertView;
            vHolder = (ViewHolder)vView.getTag();
        }

        vHolder.mtxtName.setText(mArrayListDrivers.get(position).getDriver().getGivenName());
        vHolder.mtxtSurnale.setText(mArrayListDrivers.get(position).getDriver().getFamilyName());
        vHolder.mtxtPoints.setText(""+mArrayListDrivers.get(position).getPoints());

        return vView;
    }
    private class ViewHolder{
        public TextView mtxtName, mtxtSurnale, mtxtPoints;
    }
}
