package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Database.ModelRoom.RoomQualifyingResult;
import com.example.growingmobilef1.Model.Circuit;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.Model.QualifyingResults;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QualifyingResultsDataHelper  implements IGenericHelper{

    @Override
    public ArrayList<IListableModel> getArrayList(JSONObject aJsonToParse) {

        ArrayList<IListableModel> vQualResultsArray = new ArrayList<>();

        if(aJsonToParse != null) {
            if (aJsonToParse.length() != 0) {
                try {

                    JSONArray vRaces = aJsonToParse.getJSONObject("MRData")
                            .getJSONObject("RaceTable").getJSONArray("Races");

                    for (int i = 0; i < vRaces.length(); i++) {

                        Circuit temp = Circuit.fromJson(vRaces.getJSONObject(i).getJSONObject("Circuit"));

                        // Qual Result
                        JSONArray vQResults = vRaces.getJSONObject(i).getJSONArray("QualifyingResults");

                        for (int j = 0; j < vQResults.length(); j++) {

                            RoomQualifyingResult vQRes = QualifyingResults.fromJson(vQResults.getJSONObject(j), j).toRoomQualifyingResult(temp.getCircuitId());
                            vQualResultsArray.add(vQRes);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return vQualResultsArray;
    }
}
