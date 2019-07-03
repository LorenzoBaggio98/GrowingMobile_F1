package com.example.growingmobilef1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.growingmobilef1.Database.ModelRoom.RoomRaceResult;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RaceResultsAdapter extends RecyclerView.Adapter<RaceResultsAdapter.ViewHolder> {

    private String firstPositionTime;
    private List<RoomRaceResult> mData;

    public RaceResultsAdapter(ArrayList<? extends IListableModel> aData){
        mData = (ArrayList<RoomRaceResult>) aData;
    }

    public void updateData(List<? extends IListableModel> aData) {
        mData.clear();
        mData.addAll((Collection<? extends RoomRaceResult>) aData);
        notifyDataSetChanged();
    }

    // Clean all elements of the recycler
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(ArrayList<RoomRaceResult> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View vView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_race_result, viewGroup, false);
        return new ViewHolder(vView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        RoomRaceResult data = mData.get(i);

        viewHolder.mPosition.setText(""+data.position);
        viewHolder.mDriver.setText(data.driverId);
        viewHolder.mTime.setText(data.time != null ? data.time : "");

        if(data.position != 1){
            viewHolder.mTimeSep.setText(data != null ? data.time : "");
        }else{

            firstPositionTime = data.time;
            viewHolder.mTimeSep.setText("--");
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mPosition, mDriver, mTime, mTimeSep;

        private ViewHolder(View vView) {
            super(vView);

            mPosition = vView.findViewById(R.id.txt_race_result_position);
            mDriver = vView.findViewById(R.id.txt_race_result_driver);
            mTime = vView.findViewById(R.id.txt_race_result_time);
            mTimeSep = vView.findViewById(R.id.txt_race_result_time_sep);
        }
    }
}