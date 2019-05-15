package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Interface.IListableObject;
import com.example.growingmobilef1.Model.ConstructorsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConstructorsDataHelper {

    public ArrayList<IListableObject> getArraylist(JSONObject aJsonToParse){
        ArrayList<IListableObject> vCalendarRaceItemArraylist = new ArrayList<>();

        try {
            JSONObject vMRDataObject = aJsonToParse.getJSONObject("MRData");
            JSONObject vRaceTableObject = vMRDataObject.getJSONObject("StandingsTable");
            // This array contains all the races

            JSONArray vStandsArray = vRaceTableObject.getJSONArray("StandingsLists");

            if(vStandsArray.length() > 0) {
                JSONArray vConstructorsStandingArray = vStandsArray.getJSONObject(0).getJSONArray("ConstructorStandings");

                // Create a JSONObject for each race
                for (int i = 0; i < vConstructorsStandingArray.length(); i++) {
                    JSONObject vConstructorStandingObject = vConstructorsStandingArray.getJSONObject(i);
                    JSONObject vConstructorObject = vConstructorStandingObject.getJSONObject("Constructor");

                    // Create a ConstructorsRaceItem object with the datas retrieved
                    // Populate an arrayList with all these objects
                    ConstructorsItem vConstructorsItem = new ConstructorsItem();
                    vConstructorsItem.setName(vConstructorObject.getString("name"));
                    vConstructorsItem.setUrl(vConstructorObject.getString("url"));
                    vConstructorsItem.setNationality(vConstructorObject.getString("nationality"));
                    vConstructorsItem.setPosition(vConstructorStandingObject.getInt("position"));
                    vConstructorsItem.setPoints(vConstructorStandingObject.getInt("points"));
                    vConstructorsItem.setWins(vConstructorStandingObject.getInt("wins"));
                    vConstructorsItem.setConstructorId(vConstructorObject.getString("constructorId"));

                    vCalendarRaceItemArraylist.add(vConstructorsItem);
                }
            }

        } catch (JSONException e){
            e.printStackTrace();
        }

        return vCalendarRaceItemArraylist;
    }
}
