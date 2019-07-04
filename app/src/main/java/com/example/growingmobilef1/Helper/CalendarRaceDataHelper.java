package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.Model.Races;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CalendarRaceDataHelper implements IGenericHelper{

    public CalendarRaceDataHelper() {
    }

    public ArrayList<IListableModel> getArrayList(JSONObject aJsonToParse){

        ArrayList<IListableModel> vCalendarRaceItemArraylist = new ArrayList<>();

        if(aJsonToParse != null){
            if(aJsonToParse.length() != 0){

                try {
                    JSONObject vMRDataObject = aJsonToParse.getJSONObject("MRData");
                    JSONObject vRaceTableObject = vMRDataObject.getJSONObject("RaceTable");

                    // This array contains all the races
                    JSONArray vRacesArray = vRaceTableObject.getJSONArray("Races");

                    // Create a JSONObject for each race
                    for (int i = 0; i < vRacesArray.length(); i++) {

                        JSONObject vRace  = vRacesArray.getJSONObject(i);

                        RoomRace vTempRace = Races.fromJson(vRace).toRoomRace();
                        vCalendarRaceItemArraylist.add(vTempRace);
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }

        return vCalendarRaceItemArraylist;
    }


}

