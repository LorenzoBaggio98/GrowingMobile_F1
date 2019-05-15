package com.example.growingmobilef1.Helper;

import android.util.Log;

import com.example.growingmobilef1.Interface.IListableObject;
import com.example.growingmobilef1.Model.PilotRaceItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PilotsRankingHelper {


    public static ArrayList<IListableObject> getArayListPilotsPoints(JSONObject aJsonsToParse) {

        ArrayList<IListableObject> arrayListPilotsPoints = new ArrayList<>();

        try {
            JSONObject vMdataObject = aJsonsToParse.getJSONObject("MRData");
            JSONObject vRaceTableObject = vMdataObject.getJSONObject("RaceTable");
            JSONArray vRacesArray = vRaceTableObject.getJSONArray("Races");
            JSONObject vResultRaces =  vRacesArray.getJSONObject(0);
            JSONArray vResults =vResultRaces.getJSONArray("Results");

            for (int i = 0; i < vResults.length(); i++) {

                JSONObject vRaceObject = vResults.getJSONObject(i);


                int vResultsPoints = vRaceObject.getInt("points");
                JSONObject vResultsDriver = vRaceObject.getJSONObject("Driver");

                PilotRaceItem pilotRaceItem = new PilotRaceItem();
                //
               // pilotRaceItem.setDriverId(vResultsDriver.getInt("permanentNumber"));
                pilotRaceItem.setGivenName(vResultsDriver.getString("givenName"));
                pilotRaceItem.setFamilyName(vResultsDriver.getString("familyName"));
                pilotRaceItem.setDateOfBirth(vResultsDriver.getString("dateOfBirth"));
                pilotRaceItem.setNationality(vResultsDriver.getString("nationality"));


                pilotRaceItem.setPoints(vResultsPoints);

                arrayListPilotsPoints.add(pilotRaceItem);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return arrayListPilotsPoints;
    }
}
