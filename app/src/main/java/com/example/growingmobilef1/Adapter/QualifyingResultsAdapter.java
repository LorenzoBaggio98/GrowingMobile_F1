package com.example.growingmobilef1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.growingmobilef1.Model.QualifyingResults;
import com.example.growingmobilef1.R;

import java.util.ArrayList;

public class QualifyingResultsAdapter extends RecyclerView.Adapter<QualifyingResultsAdapter.ViewHolder> {

    private ArrayList<QualifyingResults> mData;

    public QualifyingResultsAdapter(ArrayList<QualifyingResults> aData){
        mData = aData;
    }

    public void updateData(ArrayList<QualifyingResults> viewModels) {
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
    public void addAll(ArrayList<QualifyingResults> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QualifyingResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_qualifying_result, viewGroup, false);
        return new QualifyingResultsAdapter.ViewHolder(vView);
    }

    @Override
    public void onBindViewHolder(@NonNull QualifyingResultsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mPosition.setText("" + mData.get(i).getPosition());
        viewHolder.mDriver.setText("" + mData.get(i).getDriver().getFamilyName());

        if(!TextUtils.isEmpty(mData.get(i).getQ1())){
            viewHolder.mQ1.setText(mData.get(i).getQ1());
        } else {
            viewHolder.mQ1.setText("");
        }

        if(!TextUtils.isEmpty(mData.get(i).getQ2())){
            viewHolder.mQ2.setText(mData.get(i).getQ2());
        } else {
            viewHolder.mQ2.setText("");
        }

        if(!TextUtils.isEmpty(mData.get(i).getQ3())){
            viewHolder.mQ3.setText(mData.get(i).getQ3());
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