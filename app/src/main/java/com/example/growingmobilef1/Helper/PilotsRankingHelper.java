package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Model.DriverStandings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PilotsRankingHelper {


    public static ArrayList<DriverStandings> getArrayListPilotsPoints(JSONObject aJsonsToParse) {

        ArrayList<DriverStandings> arrayListPilotsPoints = new ArrayList<>();

        try {
            JSONObject vMdataObject = aJsonsToParse.getJSONObject("MRData");
            JSONObject vStandingTable=vMdataObject.getJSONObject("StandingsTable");

            JSONArray vStandingsLists = vStandingTable.getJSONArray("StandingsLists");


            for(int j = 0; j < vStandingsLists.length(); j++){
                JSONObject vResultRaces =  vStandingsLists.getJSONObject(j);

                JSONArray vDriverStandings = vResultRaces.getJSONArray("DriverStandings");

                for (int i = 0; i < vDriverStandings.length(); i++) {

                    // Get DriverStanding object
                    JSONObject vDriverStanding = vDriverStandings.getJSONObject(i);

                    DriverStandings vTempDriverS = DriverStandings.fromJson(vDriverStanding);
                    arrayListPilotsPoints.add(vTempDriverS);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayListPilotsPoints;
    }
}
