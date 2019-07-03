package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ModelRoom.RoomRaceResult;
import com.example.growingmobilef1.Model.Circuit;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.Model.RaceResults;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RaceResultsDataHelper implements IGenericHelper{

    public RaceResultsDataHelper(){

    }

    @Override
    public ArrayList<IListableModel> getArrayList(JSONObject aJsonToParse) {

        Circuit tempCircuit = new Circuit();

        ArrayList<IListableModel> vRaceResultsArray = new ArrayList<>();

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

                        Circuit temp = tempCircuit.fromJson(vRace.getJSONObject("Circuit"));

                        // Iterate the Results
                        for (int j = 0; j < vResults.length(); j++) {

                            // Get the Json Object
                            JSONObject vSingleResult = vResults.getJSONObject(j);

                            // Parsing of the json to the Object
                            RoomRaceResult vRaceResults = RaceResults.fromJson(vSingleResult, j).toRoomRaceResults(temp.getCircuitId());

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
