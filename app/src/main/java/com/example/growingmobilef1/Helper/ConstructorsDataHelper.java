package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Database.ModelRoom.RoomConstructor;
import com.example.growingmobilef1.Model.ConstructorStandings;
import com.example.growingmobilef1.Model.IListableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ConstructorsDataHelper implements IGenericHelper {

    public ArrayList<IListableModel> getArrayList(JSONObject aJsonToParse){
        ArrayList<IListableModel> vCalendarRaceItemArraylist = new ArrayList<>();

        if(aJsonToParse != null) {
            if (aJsonToParse.length() != 0) {
                try {
                    JSONObject vMRDataObject = aJsonToParse.getJSONObject("MRData");
                    JSONObject vRaceTableObject = vMRDataObject.getJSONObject("StandingsTable");
                    // This array contains all the races

                    JSONArray vStandingsLists = vRaceTableObject.getJSONArray("StandingsLists");

                    for (int i = 0; i < vStandingsLists.length(); i++) {
                        JSONObject vStanding = vStandingsLists.getJSONObject(i);

                        JSONArray vConstructorStandings = vStanding.getJSONArray("ConstructorStandings");

                        for (int j = 0; j < vConstructorStandings.length(); j++) {

                            JSONObject vConstStand = vConstructorStandings.getJSONObject(j);

                            // Trasformo da ConstructorStandings a RoomConstructor
                            RoomConstructor vTempConst = ConstructorStandings.fromJson(vConstStand).toRoomConstructor();
                            vCalendarRaceItemArraylist.add(vTempConst);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return vCalendarRaceItemArraylist;
    }
}
