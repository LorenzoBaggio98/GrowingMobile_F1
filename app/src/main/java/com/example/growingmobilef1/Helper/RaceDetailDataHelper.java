package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Model.Driver;
import com.example.growingmobilef1.Model.RaceResultsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RaceDetailDataHelper {

    public ArrayList<RaceResultsItem> getRaceResults(JSONObject aJsonToParse){

        ArrayList<RaceResultsItem> vRaceResultsArray = new ArrayList<>();

        if (aJsonToParse.length() != 0){

            try {
                //TODO uguale, fare uno per tutti
                JSONObject vMRData = aJsonToParse.getJSONObject("MRData");
                JSONObject vRaceTable = vMRData.getJSONObject("RaceTable");

                JSONArray vRaces = vRaceTable.getJSONArray("Races");

                for(int i = 0; i < vRaces.length(); i++){

                    // Single Race
                    JSONObject vRace = vRaces.getJSONObject(i);
                    JSONArray vResults = vRace.getJSONArray("Results");

                    // Iterate the Results
                    for(int j = 0; j < vResults.length(); j++) {

                        JSONObject vSingleResult = vResults.getJSONObject(i);

                        RaceResultsItem vRaceResultsItem = RaceResultsItem.fromJson(vSingleResult);

                        vRaceResultsArray.add(vRaceResultsItem);
                    }
                }


            }catch (JSONException e){
                e.printStackTrace();
            }
        }


        return vRaceResultsArray;
    }
}
