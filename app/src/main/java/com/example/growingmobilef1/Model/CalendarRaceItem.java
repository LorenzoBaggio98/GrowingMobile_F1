package com.example.growingmobilef1.Model;

import android.os.Parcelable;

import com.example.growingmobilef1.Interface.IListableObject;

import java.io.Serializable;

public class CalendarRaceItem implements Serializable, IListableObject {

    private int mId;
    private String mSeason;
    private String mDate;
    private String mTime;
    private String mRaceSeason;
    private String mRound;
    private String mCurrentWiki;
    private String mRaceName;
    private String mCircuitId;
    private String mWiki;
    private String mCircuitName;
    private String mLat;
    private String mLong;
    private String mLocality;
    private String mCountry;

    public CalendarRaceItem() {
    }

    public CalendarRaceItem(int aId, String aSeason, String aDate, String aTime, String aRaceSeason,
                            String aRound, String aCurrentWiki, String aRaceName, String aCircuitId,
                            String aWiki, String aCircuitName, String aLat, String aLong,
                            String aLocality, String aCountry) {
        this.mId = aId;
        this.mSeason = aSeason;
        this.mDate = aDate;
        this.mTime = aTime;
        this.mRaceSeason = aRaceSeason;
        this.mRound = aRound;
        this.mCurrentWiki = aCurrentWiki;
        this.mRaceName = aRaceName;
        this.mCircuitId = aCircuitId;
        this.mWiki = aWiki;
        this.mCircuitName = aCircuitName;
        this.mLat = aLat;
        this.mLong = aLong;
        this.mLocality = aLocality;
        this.mCountry = aCountry;
    }



    public int getmId() {
        return mId;
    }

    @Override
    public String getmMainInformation() {
        return mRaceName;
    }

    @Override
    public String getmOptionalInformation() {
        return mDate;
    }

    @Override
    public String getmSecondaryInformation() {
        return mTime;
    }

    @Override
    public Boolean isButtonRequired() {
        return true;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmSeason() {
        return mSeason;
    }

    public void setmSeason(String mSeason) {
        this.mSeason = mSeason;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmRaceSeason() {
        return mRaceSeason;
    }

    public void setmRaceSeason(String mRaceSeason) {
        this.mRaceSeason = mRaceSeason;
    }

    public String getmRound() {
        return mRound;
    }

    public void setmRound(String mRound) {
        this.mRound = mRound;
    }

    public String getmCurrentWiki() {
        return mCurrentWiki;
    }

    public void setmCurrentWiki(String mCurrentWiki) {
        this.mCurrentWiki = mCurrentWiki;
    }

    public String getmRaceName() {
        return mRaceName;
    }

    public void setmRaceName(String mRaceName) {
        this.mRaceName = mRaceName;
    }

    public String getmCircuitId() {
        return mCircuitId;
    }

    public void setmCircuitId(String mCircuitId) {
        this.mCircuitId = mCircuitId;
    }

    public String getmWiki() {
        return mWiki;
    }

    public void setmWiki(String mWiki) {
        this.mWiki = mWiki;
    }

    public String getmCircuitName() {
        return mCircuitName;
    }

    public void setmCircuitName(String mCircuitName) {
        this.mCircuitName = mCircuitName;
    }

    public String getmLat() {
        return mLat;
    }

    public void setmLat(String mLat) {
        this.mLat = mLat;
    }

    public String getmLong() {
        return mLong;
    }

    public void setmLong(String mLong) {
        this.mLong = mLong;
    }

    public String getmLocality() {
        return mLocality;
    }

    public void setmLocality(String mLocality) {
        this.mLocality = mLocality;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }
}
