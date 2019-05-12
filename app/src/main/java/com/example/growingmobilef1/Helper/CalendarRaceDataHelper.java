package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Interface.IListableObject;
import com.example.growingmobilef1.Model.CalendarRaceItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CalendarRaceDataHelper {

    public CalendarRaceDataHelper() {
    }

    public ArrayList<IListableObject> getArraylist(JSONObject aJsonToParse){
        ArrayList<IListableObject> vCalendarRaceItemArraylist = new ArrayList<>();

        if(aJsonToParse.length() != 0){

            try {
                JSONObject vMRDataObject = aJsonToParse.getJSONObject("MRData");
                JSONObject vRaceTableObject = vMRDataObject.getJSONObject("RaceTable");
                // This array contains all the races
                JSONArray vRacesArray = vRaceTableObject.getJSONArray("Races");

                // Create a JSONObject for each race
                for (int i = 0; i < vRacesArray.length(); i++) {
                    JSONObject vRacesObject  = vRacesArray.getJSONObject(i);
                    JSONObject vCircuitObject = vRacesObject.getJSONObject("Circuit");
                    JSONObject vLocationObject = vCircuitObject.getJSONObject("Location");

                    // Create a CalendarRaceItem object with the datas retrieved
                    // Populate an arrayList with all these objects
                    CalendarRaceItem vCalendarRaceItem = new CalendarRaceItem();
                    vCalendarRaceItem.setmLat(vLocationObject.getString("lat"));
                    vCalendarRaceItem.setmLong(vLocationObject.getString("long"));
                    vCalendarRaceItem.setmLocality(vLocationObject.getString("locality"));
                    vCalendarRaceItem.setmCountry(vLocationObject.getString("country"));
                    vCalendarRaceItem.setmCircuitId(vCircuitObject.getString("circuitId"));
                    vCalendarRaceItem.setmWiki(vCircuitObject.getString("url"));
                    vCalendarRaceItem.setmCircuitName(vCircuitObject.getString("circuitName"));
                    vCalendarRaceItem.setmRaceSeason(vRacesObject.getString("season"));
                    vCalendarRaceItem.setmRound(vRacesObject.getString("round"));
                    vCalendarRaceItem.setmCurrentWiki(vRacesObject.getString("url"));
                    vCalendarRaceItem.setmRaceName(vRacesObject.getString("raceName"));
                    vCalendarRaceItem.setmDate(vRacesObject.getString("date"));
                    vCalendarRaceItem.setmTime(vRacesObject.getString("time"));

                    vCalendarRaceItemArraylist.add(vCalendarRaceItem);
                }

            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        return vCalendarRaceItemArraylist;
    }


}

