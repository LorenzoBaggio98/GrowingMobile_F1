package com.example.growingmobilef1.Model;

import com.example.growingmobilef1.Interface.IListableObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

// Ua
public class Races implements Serializable, IListableObject {

    private int season;
    private int round;
    private URL url;
    private String raceName;
    private Circuit Circuit;

    private String mDate;
    private String time;

    private Boolean isNotificationScheduled = false;
    private ArrayList<RaceResults> Results;

    public static Races fromJson(JSONObject json){

        Races tempRaces = new Races();
        Circuit tempC = new Circuit();

        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd");

        if(json != null) {
            if (json.length() != 0) {
                try {

                    tempRaces.setSeason(json.getInt("season"));
                    tempRaces.setRound(json.getInt("round"));
                    tempRaces.setUrl(new URL(json.getString("url")));
                    tempRaces.setRaceName(json.getString("raceName"));
                    tempRaces.setCircuit(tempC.fromJson(json.getJSONObject("Circuit")));
                    //tempRaces.setmDate(sDF.parse(json.getString("date")));
                    tempRaces.setmDate(json.getString("date"));
                    tempRaces.setTime(json.getString("time"));

                    ArrayList<RaceResults> vRaceResultsArrayList = new ArrayList<>();

                    JSONArray temp = json.optJSONArray("Results");

                    if (temp != null) {
                        for (int i = 0; i < temp.length(); i++) {
                            vRaceResultsArrayList.add(RaceResults.fromJson(temp.getJSONObject(i)));
                        }
                        tempRaces.setResults(vRaceResultsArrayList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } /*catch (ParseException e) {
                e.printStackTrace();
            }*/
            }
        }

        return tempRaces;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public Circuit getCircuit() {
        return Circuit;
    }

    public void setCircuit(Circuit circuit) {
        Circuit = circuit;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String date) {
        this.mDate = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<RaceResults> getResults() {
        return Results;
    }

    public void setResults(ArrayList<RaceResults> results) {
        Results = results;
    }

    /// IS LISTABLE OBJECT METHODS
    @Override
    public int getmId() {
        return 0;
    }

    @Override
    public String getmMainInformation() {
        return getRaceName();
    }

    @Override
    public String getmOptionalInformation() {
        return getmDate().toString();
    }

    @Override
    public String getmSecondaryInformation() {
        return getTime();
    }

    @Override
    public Boolean isButtonRequired() {
        return true;
    }

    public Boolean getNotificationScheduled() {
        return isNotificationScheduled;
    }

    public void setNotificationScheduled(Boolean notificationScheduled) {
        isNotificationScheduled = notificationScheduled;
    }

    public Date getDate(){

        SimpleDateFormat tempFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tempFormat.setTimeZone(TimeZone.getDefault());

        Date tempDate = new Date();

        try {
            tempDate = tempFormat.parse(getmDate() + " " + getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tempDate;
    }

}
