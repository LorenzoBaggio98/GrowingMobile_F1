package com.example.growingmobilef1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.growingmobilef1.Model.Driver;
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

            vHolder.mPositionLabel = vView.findViewById(R.id.list_item_driver_position);
            vHolder.mSurnameLabel = vView.findViewById(R.id.list_item_driver_surname);
            vHolder.mNameLabel = vView.findViewById(R.id.list_item_driver_name);
            vHolder.mTeamLabel = vView.findViewById(R.id.list_item_driver_team);
            //vHolder.mNationalityLabel = vView.findViewById(R.id.list_item_driver_nationality);
            vHolder.mPointsLabel = vView.findViewById(R.id.list_item_driver_points);
            vHolder.mNationalityImage = vView.findViewById(R.id.list_item_driver_nationality);

            vView.setTag(vHolder);

        } else{
            vView = convertView;
            vHolder = (ViewHolder)vView.getTag();
        }

        DriverStandings stand = mArrayListDrivers.get(position);

        vHolder.mPositionLabel.setText("" + stand.getPositionText() );
        vHolder.mSurnameLabel.setText(stand.getDriver().getFamilyName());
        vHolder.mNameLabel.setText(" " + stand.getDriver().getGivenName());
        vHolder.mTeamLabel.setText(stand.getConstructor().getName());
        vHolder.mPointsLabel.setText(stand.getPoints() + " Pts");

        String flag_name = "flag_" + mArrayListDrivers.get(position).getDriver().getNationality().toLowerCase();
        int flag_drawable = parent.getResources().getIdentifier(flag_name, "drawable", parent.getContext().getPackageName());

        vHolder.mNationalityImage.setImageResource(flag_drawable);

        return vView;
    }
    private class ViewHolder{
        public TextView mPositionLabel, mSurnameLabel, mNameLabel, mTeamLabel, mPointsLabel;
        public ImageView mNationalityImage;
    }
}