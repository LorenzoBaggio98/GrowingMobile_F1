package com.example.growingmobilef1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.growingmobilef1.Model.ConstructorStandings;
import com.example.growingmobilef1.Model.RaceResults;
import com.example.growingmobilef1.R;

import java.util.ArrayList;
import java.util.List;

public class RaceResultsAdapter extends RecyclerView.Adapter<RaceResultsAdapter.ViewHolder> {

    private ArrayList<RaceResults> mData;

    public RaceResultsAdapter(ArrayList<RaceResults> aData){
        mData = aData;
    }

    public void updateData(ArrayList<RaceResults> viewModels) {
        mData.clear();
        mData.addAll(viewModels);
        //notifyDataSetChanged();
    }

    // Clean all elements of the recycler
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(ArrayList<RaceResults> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_race_result, viewGroup, false);
        return new ViewHolder(vView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mPosition.setText("" + mData.get(i).getPosition());
        viewHolder.mDriver.setText("" + mData.get(i).getDriver().getFamilyName());
        viewHolder.mTime.setText("" + mData.get(i).getTime().getTime() != null ? mData.get(i).getTime().getTime() : "");

        if(mData.get(i).getPosition() != 1){
            viewHolder.mTimeSep.setText("" + mData.get(i).getTime().getTime() != null ? mData.get(i).getTime().getTime() : "");
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