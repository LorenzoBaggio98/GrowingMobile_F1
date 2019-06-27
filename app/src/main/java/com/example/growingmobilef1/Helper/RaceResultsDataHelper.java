package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Model.RaceResults;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RaceResultsDataHelper {

    public ArrayList<RaceResults> getRaceResults(JSONObject aJsonToParse){

        ArrayList<RaceResults> vRaceResultsArray = new ArrayList<>();

        if(aJsonToParse != null) {
            if (aJsonToParse.length() != 0) {

                try {
                    JSONObject vMRData = aJsonToParse.getJSONObject("MRData");
                    JSONObject vRaceTable = vMRData.getJSONObject("RaceTable");

                    JSONArray vRaces = vRaceTable.getJSONArray("Races");

                    for (int i = 0; i < vRaces.length(); i++) {

                        // Single Race
                        JSONObject vRace = vRaces.getJSONObject(i);
                        JSONArray vResults = vRace.getJSONArray("Results");

                        // Iterate the Results
                        for (int j = 0; j < vResults.length(); j++) {

                            // Get the Json Object
                            JSONObject vSingleResult = vResults.getJSONObject(j);

                            // Parsing of the json to the Object
                            RaceResults vRaceResults = RaceResults.fromJson(vSingleResult);

                            vRaceResultsArray.add(vRaceResults);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return vRaceResultsArray;
    }
}
