package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Model.QualifyingResults;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QualifyingResultsDataHelper {

    public ArrayList<QualifyingResults> getQualResults(JSONObject aJson){

        ArrayList<QualifyingResults> vQualResultsArray = new ArrayList<>();

        if(aJson.length() != 0){
            try{

                JSONArray vRaces = aJson.getJSONObject("MRData")
                        .getJSONObject("RaceTable").getJSONArray("Races");

                for(int i = 0; i < vRaces.length(); i++){

                    // Qual Result
                    JSONArray vQResults = vRaces.getJSONObject(i).getJSONArray("QualifyingResults");

                    for(int j = 0; j < vQResults.length(); j++){

                        QualifyingResults vQRes = QualifyingResults.fromJson(vQResults.getJSONObject(j));
                        vQualResultsArray.add(vQRes);
                    }
                }

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return vQualResultsArray;
    }
}
