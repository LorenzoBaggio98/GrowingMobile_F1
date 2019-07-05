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

import com.example.growingmobilef1.Database.ModelRoom.RoomConstructor;
import com.example.growingmobilef1.Database.ModelRoom.RoomDriver;
import com.example.growingmobilef1.Database.ViewModel.DriverViewModel;
import com.example.growingmobilef1.Fragment_Activity.DriverDetailActivity;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.MyViewHolder> {

    ArrayList<RoomDriver> mArrayListDrivers;
    private List<RoomConstructor> mConstructorData;

    private Context context;

    DriverViewModel driverViewModel;

    public DriverAdapter(ArrayList<? extends IListableModel> mArrayList, Context context, ArrayList<? extends IListableModel> mConstructorList) {
        this.context = context;
        mArrayListDrivers = (ArrayList<RoomDriver>) mArrayList;
        mConstructorData = (ArrayList<RoomConstructor>) mConstructorList;
    }
    public void updateData(List<? extends IListableModel> viewModels) {
        mArrayListDrivers.clear();
        mArrayListDrivers.addAll((Collection<? extends RoomDriver>)viewModels);
        notifyDataSetChanged();
    }

    public void addAllConstructor(List<? extends IListableModel> list) {
        mConstructorData.addAll((Collection<? extends RoomConstructor>) list);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mPositionLabel, mSurnameLabel, mNameLabel, mTeamLabel, mPointsLabel;
        public ImageView mNationalityImage;
        private LinearLayout touch_layout;

        private MyViewHolder(@NonNull View vView) {
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_drivers, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder vHolder, final int position) {

        final RoomDriver stand = mArrayListDrivers.get(position);

        vHolder.mPositionLabel.setText("" + stand.rankPosition );
        vHolder.mSurnameLabel.setText(stand.name);
        vHolder.mNameLabel.setText(" " + stand.surname);

        if(mConstructorData != null){
            RoomConstructor temp = mConstructorData
                    .stream()
                    .filter(constructor -> constructor.constructorId.equals(stand.constructorId))
                    .findFirst()
                    .orElse(null);

            if(temp != null){
                vHolder.mTeamLabel.setText(temp.name);
            }
        }

        vHolder.mPointsLabel.setText(stand.rankPoints + " Pts");

        String flag_name = "flag_" + mArrayListDrivers.get(position).nationality.toLowerCase();
        int flag_drawable = vHolder.itemView.getResources().getIdentifier(flag_name, "drawable", vHolder.itemView.getContext().getPackageName());

        vHolder.mNationalityImage.setImageResource(flag_drawable);
        vHolder.touch_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO commentato perchè sarà da usare RoomDriver
                RoomDriver vdriver = stand;

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

}
