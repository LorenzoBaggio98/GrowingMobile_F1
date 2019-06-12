package com.example.growingmobilef1.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class Circuit implements Serializable {

    private String circuitId;
    private URL url;
    private String circuitName;
    private Location Location;

    public Circuit fromJson(JSONObject json){

        Circuit tempCircuit = new Circuit();
        Location tempL = new Location();

        if(json != null) {
            if (json.length() != 0) {
                try {

                    tempCircuit.setCircuitId(json.getString("circuitId"));
                    tempCircuit.setUrl(new URL(json.getString("url")));
                    tempCircuit.setCircuitName(json.getString("circuitName"));
                    tempCircuit.setLocation(tempL.fromJson(json.getJSONObject("Location")));

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        return tempCircuit;
    }

    public String getCircuitId() {
        return circuitId;
    }

    public void setCircuitId(String circuitId) {
        this.circuitId = circuitId;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getCircuitName() {
        return circuitName;
    }

    public void setCircuitName(String circuitName) {
        this.circuitName = circuitName;
    }

    public Location getLocation() {
        return Location;
    }

    public void setLocation(Location location) {
        this.Location = location;
    }
}
