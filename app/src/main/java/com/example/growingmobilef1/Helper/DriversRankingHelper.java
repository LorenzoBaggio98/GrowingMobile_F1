package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Model.Constructor;
import com.example.growingmobilef1.Model.DriverStandings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DriversRankingHelper {

    public static ArrayList<DriverStandings> getArrayListPilotsPoints(JSONObject aJsonsToParse) {

        ArrayList<DriverStandings> arrayListPilotsPoints = new ArrayList<>();

        if(aJsonsToParse != null) {
            if (aJsonsToParse.length() != 0) {
                try {
                    JSONObject vMdataObject = aJsonsToParse.getJSONObject("MRData");
                    JSONObject vStandingTable = vMdataObject.getJSONObject("StandingsTable");

                    JSONArray vStandingsLists = vStandingTable.getJSONArray("StandingsLists");

                    for (int j = 0; j < vStandingsLists.length(); j++) {
                        JSONObject vResultRaces = vStandingsLists.getJSONObject(j);

                        JSONArray vDriverStandings = vResultRaces.getJSONArray("DriverStandings");

                        for (int i = 0; i < vDriverStandings.length(); i++) {

                            // Get DriverStanding object
                            JSONObject vDriverStanding = vDriverStandings.getJSONObject(i);

                            JSONArray vJSONArrayConstructor = vDriverStanding.getJSONArray("Constructors");
                            JSONObject vJSONObjectConstructor = vJSONArrayConstructor.getJSONObject(0);
                            DriverStandings vTempDriverS = DriverStandings.fromJson(vDriverStanding);
                            Constructor vConstructor = Constructor.fromJson(vJSONObjectConstructor);

                            vTempDriverS.setConstructor(vConstructor);
                            arrayListPilotsPoints.add(vTempDriverS);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return arrayListPilotsPoints;
    }
}
