package com.example.growingmobilef1.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Constructor {

    private String constructorId;
    private String url;
    private String name;
    private String nationality;

    public static Constructor fromJson(JSONObject json){

        Constructor tempCons = new Constructor();

        if(json.length() != 0){
            try{

                tempCons.setConstructorId(json.getString("constructorId"));
                tempCons.setUrl(json.getString("url"));
                tempCons.setName(json.getString("name"));
                tempCons.setNationality(json.getString("nationality"));

            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        return tempCons;
    }

    public String getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(String constructorId) {
        this.constructorId = constructorId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
