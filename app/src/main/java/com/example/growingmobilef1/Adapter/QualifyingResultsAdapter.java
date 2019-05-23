package com.example.growingmobilef1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.growingmobilef1.Model.QualifyingResults;
import com.example.growingmobilef1.R;

import java.util.ArrayList;

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
}
