package com.example.growingmobilef1.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.growingmobilef1.Database.InterfaceDao.RaceResultsDao;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.Model.RaceResults;
import com.example.growingmobilef1.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RacesAdapter extends RecyclerView.Adapter<RacesAdapter.ViewHolder> {

    private int offsetOccurred = 0;
    private List<RoomRace> mRacesArrayList;
    private Map<String, List<RaceResultsDao.RoomPodium>> mRaceResultsMap;

    private Context mContext;

    // For loading different layouts
    private final int RACE_OCCURED = 0;
    private final int RACE_NOT_OCCURRED = 1;

    // For click handling
    private IOnRaceClicked mItemListener;
    private IOnNotificationIconClicked mNotificationListener;

    public RacesAdapter(Context aContext,
                        ArrayList<? extends IListableModel> aData,
                        Map<String, List<RaceResultsDao.RoomPodium>> aPodium,
                        IOnRaceClicked aListener,
                        IOnNotificationIconClicked aNotificationListener) {

        mContext = aContext;
        mRaceResultsMap = aPodium;
        mRacesArrayList = (ArrayList<RoomRace>) aData;
        mItemListener = aListener;
        mNotificationListener = aNotificationListener;
    }

    /**
     *
     * @param aData
     */
    public void updateData(List<? extends IListableModel> aData, Map<String, List<RaceResultsDao.RoomPodium>> aRacePodium) {

        if(aData != null) {
            mRacesArrayList.clear();
            mRacesArrayList.addAll((Collection<? extends RoomRace>) aData);
        }

        if(aRacePodium != null){
            mRaceResultsMap.clear();
            mRaceResultsMap.putAll(aRacePodium);
        }

        notifyDataSetChanged();
    }

    // Clean all elements of the recycler
    public void clear() {
        mRacesArrayList.clear();
        mRaceResultsMap.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(ArrayList<RoomRace> aData, Map<String, ArrayList<RaceResultsDao.RoomPodium>> aRaceResultsMap) {
        mRacesArrayList.addAll(aData);
        mRaceResultsMap.putAll(aRaceResultsMap);
        notifyDataSetChanged();
    }

    // Used to assign different layouts to viewholder
    @Override
    public int getItemViewType(int position) {

        Calendar vCalendarConvertRaceDate = mRacesArrayList.get(position).dateToCalendar();
        long raceMilliSecondDate = vCalendarConvertRaceDate.getTimeInMillis();

        Calendar vCalendar = Calendar.getInstance();
        vCalendar.setTime(vCalendar.getTime());
        if (raceMilliSecondDate > vCalendar.getTimeInMillis()) {
            return RACE_NOT_OCCURRED;
        } else {
            offsetOccurred = position;
            return RACE_OCCURED;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View vView;
        switch (viewType) {
            case RACE_OCCURED:
                vView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_calendar_last_race,
                        viewGroup,
                        false);
                break;

            default:
                vView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_calendar,
                        viewGroup,
                        false);
        }
        return new ViewHolder(mContext, vView, mItemListener, mNotificationListener);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder vHolder,int position){

        Calendar vCalendarDate = mRacesArrayList.get(position).dateToCalendar();

        vHolder.mRaceLabel.setText("" + mRacesArrayList.get(position).name);

        // Set the podium results (if the race has already occurred)
        String vPositionLabelString = "";
        if (mRaceResultsMap.containsKey(mRacesArrayList.get(position).circuitId)) {
            for (int i = 0; i < 3; i++) {
                String vPosition = mRaceResultsMap.get(mRacesArrayList.get(position).circuitId).get(i).dri_code;
                if (i < 2)
                    vPositionLabelString += vPosition + " / ";
                else
                    vPositionLabelString += vPosition;
            }
        }
        vHolder.mPodiumLabel.setText(vPositionLabelString);

        // Set date and time of the race
        vHolder.mDateLabel.setText(vCalendarDate.get(Calendar.DAY_OF_MONTH) + " " +
                vCalendarDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));

        vHolder.mTimeLabel.setText(vCalendarDate.get(Calendar.HOUR_OF_DAY) + ":" + vCalendarDate.get(Calendar.MINUTE));

        // Change notification icon if notification is scheduled
        if (mRacesArrayList.get(position).notification != 0) {
            ColorStateList vPrimaryColor = AppCompatResources.getColorStateList(mContext, R.color.colorPrimary);
            ImageViewCompat.setImageTintList(vHolder.mNotificationIcon, vPrimaryColor);
        }
    }

    @Override
    public int getItemCount () {
        return mRacesArrayList.size();
    }

    public int getOffsetOccurred(){
        return offsetOccurred;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mRaceLabel, mPodiumLabel, mDateLabel, mTimeLabel;
        IOnRaceClicked mItemListener;
        IOnNotificationIconClicked mNotificationListener;
        ImageView mNotificationIcon;

        private ViewHolder(final Context aContext, final View vView, IOnRaceClicked aListener, IOnNotificationIconClicked aNotificationListener) {
            super(vView);

            mRaceLabel = vView.findViewById(R.id.list_item_calendar_label_race_name);
            mPodiumLabel = vView.findViewById(R.id.list_item_calendar_label_podium);
            mDateLabel = vView.findViewById(R.id.list_item_calendar_label_date);
            mTimeLabel = vView.findViewById(R.id.list_item_calendar_label_hour);
            mNotificationIcon = vView.findViewById(R.id.list_item_calendar_notification_icon);
            mItemListener = aListener;
            mNotificationListener = aNotificationListener;

            vView.setOnClickListener(this);
            if (mNotificationIcon != null) {
                mNotificationIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNotificationListener.onNotificationScheduled(getAdapterPosition());
                        manageNotificationIconColor(aContext, mNotificationIcon);
                    }
                });
            }
        }

        @Override
        public void onClick(View v) {
            mItemListener.onRaceClicked(getAdapterPosition());
        }

        // Change the notifiation color
        private void manageNotificationIconColor(Context aContext, ImageView aNotificationIcon) {
            ColorStateList vPrimaryColor = AppCompatResources.getColorStateList(aContext, R.color.colorPrimary);
            ColorStateList vUnselectedColor = AppCompatResources.getColorStateList(aContext, R.color.colorSecondaryLight);

            if (aNotificationIcon.getImageTintList() == vUnselectedColor)
                ImageViewCompat.setImageTintList(aNotificationIcon, vPrimaryColor);
            else
                ImageViewCompat.setImageTintList(aNotificationIcon, vUnselectedColor);
        }
    }

    // For click handling
    public interface IOnRaceClicked {
        void onRaceClicked(int aPosition);
    }

    public interface IOnNotificationIconClicked {
        void onNotificationScheduled(int aPosition);
    }
}
