package com.example.growingmobilef1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.growingmobilef1.Fragment_Activity.DriverDetailActivity;
import com.example.growingmobilef1.Model.Driver;
import com.example.growingmobilef1.Model.DriverStandings;
import com.example.growingmobilef1.R;

import java.util.ArrayList;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.MyWiewHolder> {


    ArrayList<DriverStandings> mArrayListDrivers;
    private Context context;

    public DriverAdapter(ArrayList<DriverStandings> mArrayList, Context context) {
        this.context=context;

        mArrayListDrivers = mArrayList;
    }

    public static class MyWiewHolder extends RecyclerView.ViewHolder {
        public TextView mPositionLabel, mSurnameLabel, mNameLabel, mTeamLabel, mPointsLabel;
        public ImageView mNationalityImage;
        private LinearLayout touch_layout;


        private MyWiewHolder(@NonNull View vView) {
            super(vView);
           mPositionLabel = vView.findViewById(R.id.list_item_driver_position);
          mSurnameLabel = vView.findViewById(R.id.list_item_driver_surname);
            mNameLabel = vView.findViewById(R.id.list_item_driver_name);
         mTeamLabel = vView.findViewById(R.id.list_item_driver_team);

            mPointsLabel = vView.findViewById(R.id.list_item_driver_points);
           mNationalityImage = vView.findViewById(R.id.list_item_driver_nationality);
            touch_layout=vView.findViewById(R.id.linearLayoutListItemPiloti);


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
    public void onBindViewHolder(@NonNull MyWiewHolder vHolder, final int position) {

        final DriverStandings stand = mArrayListDrivers.get(position);

        vHolder.mPositionLabel.setText("" + stand.getPositionText() );
        vHolder.mSurnameLabel.setText(stand.getDriver().getFamilyName());
        vHolder.mNameLabel.setText(" " + stand.getDriver().getGivenName());
        vHolder.mTeamLabel.setText(stand.getConstructor().getName());
        vHolder.mPointsLabel.setText(stand.getPoints() + " Pts");
        String flag_name = "flag_" + mArrayListDrivers.get(position).getDriver().getNationality().toLowerCase();
        int flag_drawable = vHolder.itemView.getResources().getIdentifier(flag_name, "drawable", vHolder.itemView.getContext().getPackageName());

        vHolder.mNationalityImage.setImageResource(flag_drawable);
        vHolder.touch_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Driver vdriver = stand.getDriver();

                Intent vIntent = new Intent(context, DriverDetailActivity.class);
                vIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Bundle vBundle = new Bundle();
                vBundle.putSerializable("SAVE_ID", vdriver);
                vIntent.putExtras(vBundle);
               context.startActivity(vIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mArrayListDrivers.size();
    }
    public void updateData(ArrayList<DriverStandings> viewModels) {
        mArrayListDrivers.clear();
        mArrayListDrivers.addAll(viewModels);

    }


}
