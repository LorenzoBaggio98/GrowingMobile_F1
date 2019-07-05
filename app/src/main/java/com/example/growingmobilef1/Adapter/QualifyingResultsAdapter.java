package com.example.growingmobilef1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.growingmobilef1.Database.ModelRoom.RoomDriver;
import com.example.growingmobilef1.Database.ModelRoom.RoomQualifyingResult;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QualifyingResultsAdapter extends RecyclerView.Adapter<QualifyingResultsAdapter.ViewHolder> {

    private List<RoomQualifyingResult> mData;
    private List<RoomDriver> mDriverData;

    public QualifyingResultsAdapter(ArrayList<? extends IListableModel> aData, ArrayList<? extends IListableModel> aDriverData){
        mData = (ArrayList<RoomQualifyingResult>) aData;
        mDriverData = (ArrayList<RoomDriver>) aDriverData;

    }

    public void updateData(List<? extends IListableModel> aData) {
        mData.clear();
        mData.addAll((Collection<? extends RoomQualifyingResult>) aData);
        notifyDataSetChanged();
    }

    // Clean all elements of the recycler
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAllDriver(List<? extends IListableModel> list) {
        mDriverData.addAll((Collection<? extends RoomDriver>) list);
    }
    @NonNull
    @Override
    public QualifyingResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_qualifying_result, viewGroup, false);
        return new QualifyingResultsAdapter.ViewHolder(vView);
    }

    @Override
    public void onBindViewHolder(@NonNull QualifyingResultsAdapter.ViewHolder viewHolder, int i) {

        RoomQualifyingResult data = mData.get(i);

        viewHolder.mPosition.setText("" + data.position);

        if(mDriverData != null){
            RoomDriver temp = mDriverData
                    .stream()
                    .filter(driver -> driver.driverId.equals(data.driverId))
                    .findFirst()
                    .orElse(null);

            if(temp != null) {
                viewHolder.mDriver.setText(temp.name + " " + temp.surname);
            }
        }

        if(!TextUtils.isEmpty(data.q1)){
            viewHolder.mQ1.setText(data.q1);
        } else {
            viewHolder.mQ1.setText("");
        }

        if(!TextUtils.isEmpty(data.q2)){
            viewHolder.mQ2.setText(data.q2);
        } else {
            viewHolder.mQ2.setText("");
        }

        if(!TextUtils.isEmpty(data.q3)){
            viewHolder.mQ3.setText(data.q3);
        } else {
            viewHolder.mQ3.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mPosition, mDriver, mQ1, mQ2, mQ3;

        private ViewHolder(View vView) {
            super(vView);

            mPosition = vView.findViewById(R.id.txt_qual_result_position);
            mDriver = vView.findViewById(R.id.txt_qual_result_driver);
            mQ1 = vView.findViewById(R.id.txt_qual_result_q1_time);
            mQ2 = vView.findViewById(R.id.txt_qual_result_q2_time);
            mQ3 = vView.findViewById(R.id.txt_qual_result_q3_time);
        }
    }
}