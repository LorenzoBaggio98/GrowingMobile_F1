package com.example.growingmobilef1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
        if(!mData.get(i).getQ1().isEmpty()){
            viewHolder.mQ1.setText(mData.get(i).getQ1());
        }

        if(!mData.get(i).getQ2().isEmpty()){
            viewHolder.mQ2.setText(mData.get(i).getQ2());
        }

        if(!mData.get(i).getQ3().isEmpty()){
            viewHolder.mQ3.setText(mData.get(i).getQ3());
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
/*
public class QualifyingResultsAdapter extends BaseAdapter {

    private ArrayList<QualifyingResults> dataList;

    public QualifyingResultsAdapter(ArrayList<QualifyingResults> data){ this.dataList = data;}

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public QualifyingResults getItem(int position) {
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
            cellView = inflater.inflate(R.layout.list_item_qualifying_result, parent, false);

            QualifyViewHolder viewHolder = new QualifyViewHolder();
            viewHolder.txt_position = cellView.findViewById(R.id.txt_qual_result_position);
            viewHolder.txt_driver = cellView.findViewById(R.id.txt_qual_result_driver);

            viewHolder.txt_q1 = cellView.findViewById(R.id.txt_qual_result_q1_time);
            viewHolder.txt_q2 = cellView.findViewById(R.id.txt_qual_result_q2_time);
            viewHolder.txt_q3 = cellView.findViewById(R.id.txt_qual_result_q3_time);

            cellView.setTag(viewHolder);

        }else{
            cellView = convertView;
        }

        //
        QualifyViewHolder vHolder = (QualifyViewHolder) cellView.getTag();
        QualifyingResults tempItem = getItem(position);

        vHolder.txt_position.setText(""+tempItem.getPosition());
        vHolder.txt_driver.setText(tempItem.getDriver().getDriverId());

        // Controllo se ci sono i tempi delle qualifiche
        if(!tempItem.getQ1().isEmpty()){
            vHolder.txt_q1.setText(tempItem.getQ1());
        }

        if(!tempItem.getQ2().isEmpty()){
            vHolder.txt_q2.setText(tempItem.getQ2());
        }

        if(!tempItem.getQ3().isEmpty()){
            vHolder.txt_q3.setText(tempItem.getQ3());
        }


        return cellView;
    }

    private class QualifyViewHolder{

        TextView txt_position;
        TextView txt_driver;
        TextView txt_q1;
        TextView txt_q2;
        TextView txt_q3;

    }
}*/
