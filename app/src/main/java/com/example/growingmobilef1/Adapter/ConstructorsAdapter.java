package com.example.growingmobilef1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.growingmobilef1.Database.ModelRoom.RoomConstructor;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// must be paramitized to access to viewHolder and use custom layout widjet
public class ConstructorsAdapter extends RecyclerView.Adapter<ConstructorsAdapter.ViewHolder> {

    List<RoomConstructor> mValues;

    // class used to protect from the calling findViewById on each bind
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mConstructorPosition, mConstructorName,mConstructorScore;

        private ViewHolder(View vView) {
            super(vView);

            // sets label widgets
            mConstructorPosition = vView.findViewById(R.id.list_item_constructor_position);
            mConstructorName = vView.findViewById(R.id.list_item_constructor_name);
            mConstructorScore = vView.findViewById(R.id.list_item_constructor_score);
        }
    }

    public ConstructorsAdapter(ArrayList<? extends IListableModel> vValues) {
        mValues = (ArrayList<RoomConstructor>) vValues;
    }

    public ConstructorsAdapter() {
        mValues = new ArrayList<>();
    }

    public void updateData(List<? extends IListableModel> viewModels) {
        mValues.clear();
        mValues.addAll((Collection<? extends RoomConstructor>) viewModels);
        notifyDataSetChanged();
    }

    // Clean all elements of the recycler
    public void clear() {
        mValues.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<RoomConstructor> list) {
        mValues.addAll(list);
        notifyDataSetChanged();
    }

    // Create a new row object whenever the list needs a new one
    // (each child view can be found and stored, recycled)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // row layout
        View vItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_constructors, viewGroup, false);
        return new ViewHolder(vItemView);
    }

    // Sets the data for each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder vViewHolder, int i) {
        vViewHolder.mConstructorPosition.setText(mValues.get(i).rankPosition + "");
        vViewHolder.mConstructorName.setText(mValues.get(i).name);
        vViewHolder.mConstructorScore.setText(mValues.get(i).rankPoints + " Pts");
    }


    // SIZE OF THE LIST, total rows
    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
