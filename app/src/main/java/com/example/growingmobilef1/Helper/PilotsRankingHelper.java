package com.example.growingmobilef1.Helper;

import android.util.Log;

import com.example.growingmobilef1.Interface.IListableObject;
import com.example.growingmobilef1.Model.PilotRaceItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PilotsRankingHelper {


    public static ArrayList<PilotRaceItem> getArayListPilotsPoints(JSONObject aJsonsToParse) {

        ArrayList<PilotRaceItem> arrayListPilotsPoints = new ArrayList<>();

        try {
            JSONObject vMdataObject = aJsonsToParse.getJSONObject("MRData");
            JSONObject vStandingTable=vMdataObject.getJSONObject("StandingsTable");
            JSONArray vStandingsLists=vStandingTable.getJSONArray("StandingsLists");
            JSONObject vResultRaces =  vStandingsLists.getJSONObject(0);
            JSONArray vDriverStandings = vResultRaces.getJSONArray("DriverStandings");
//            JSONObject vRaceTableObject = vMdataObject.getJSONObject("RaceTable");
//            JSONArray vRacesArray = vRaceTableObject.getJSONArray("Races");
//            JSONObject vResultRaces =  vRacesArray.getJSONObject(0);
//            JSONArray vResults =vResultRaces.getJSONArray("Results");

            for (int i = 0; i < vDriverStandings.length(); i++) {

                JSONObject vObjectItemDriversStandings = vDriverStandings.getJSONObject(i);


                int vResultsPoints = vObjectItemDriversStandings.getInt("points");
                JSONObject vResultsInfoDriver = vObjectItemDriversStandings.getJSONObject("Driver");

                PilotRaceItem pilotRaceItem = new PilotRaceItem();

                pilotRaceItem.setGivenName(vResultsInfoDriver.getString("givenName"));
                pilotRaceItem.setFamilyName(vResultsInfoDriver.getString("familyName"));
                pilotRaceItem.setDateOfBirth(vResultsInfoDriver.getString("dateOfBirth"));
                pilotRaceItem.setNationality(vResultsInfoDriver.getString("nationality"));


                pilotRaceItem.setPoints(vResultsPoints);

                arrayListPilotsPoints.add(pilotRaceItem);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return arrayListPilotsPoints;
    }
}
