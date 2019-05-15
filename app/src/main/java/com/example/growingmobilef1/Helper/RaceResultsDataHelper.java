package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Model.RaceResultsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RaceResultsDataHelper {

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

                        // Get the Json Object
                        JSONObject vSingleResult = vResults.getJSONObject(j);

                        // Parsing of the json to the Object
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
