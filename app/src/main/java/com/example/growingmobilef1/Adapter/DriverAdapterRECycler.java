package com.example.growingmobilef1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.growingmobilef1.Model.DriverStandings;
import com.example.growingmobilef1.R;

import java.util.ArrayList;

public class DriverAdapterRECycler extends RecyclerView.Adapter<DriverAdapterRECycler.MyWiewHolder> {

    private ArrayList<DriverStandings> mArrayListDrivers;

    public DriverAdapterRECycler(ArrayList<DriverStandings> mArrayList) {
        mArrayListDrivers = mArrayList;
    }

    public static class MyWiewHolder extends RecyclerView.ViewHolder {
        public TextView mPositionLabel, mSurnameLabel, mNameLabel, mTeamLabel, mPointsLabel;
        public ImageView mNationalityImage;


        private MyWiewHolder(@NonNull View vView) {
            super(vView);
           mPositionLabel = vView.findViewById(R.id.list_item_driver_position);
          mSurnameLabel = vView.findViewById(R.id.list_item_driver_surname);
            mNameLabel = vView.findViewById(R.id.list_item_driver_name);
         mTeamLabel = vView.findViewById(R.id.list_item_driver_team);
            //mNationalityLabel = vView.findViewById(R.id.list_item_driver_nationality);
            mPointsLabel = vView.findViewById(R.id.list_item_driver_points);
          //  mNationalityImage = vView.findViewById(R.id.list_item_driver_nationality);


        }
    }

    @NonNull
    @Override
    public MyWiewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_drivers, viewGroup, false);
        MyWiewHolder vh=new MyWiewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyWiewHolder vHolder, int position) {

        DriverStandings stand = mArrayListDrivers.get(position);

        vHolder.mPositionLabel.setText("" + stand.getPositionText() );
        vHolder.mSurnameLabel.setText(stand.getDriver().getFamilyName());
        vHolder.mNameLabel.setText(" " + stand.getDriver().getGivenName());
        vHolder.mTeamLabel.setText(stand.getConstructor().getName());
        vHolder.mPointsLabel.setText(stand.getPoints() + " Pts");
//        String flag_name = "flag_" + mArrayListDrivers.get(position).getDriver().getNationality().toLowerCase();
//        int flag_drawable = parent.getResources().getIdentifier(flag_name, "drawable", parent.getContext().getPackageName());
//
//        vHolder.mNationalityImage.setImageResource(flag_drawable);


    }

    @Override
    public int getItemCount() {
        return mArrayListDrivers.size();
    }


}
