package com.example.growingmobilef1.Model;

import com.example.growingmobilef1.Interface.IListableObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class Constructor implements Serializable {

    private String constructorId;
    private URL url;
    private String name;
    private String nationality;

    public Constructor fromJson(JSONObject json){

        Constructor tempCons = new Constructor();

        if(json != null){
            if(json.length() != 0){
                try{

                    tempCons.setConstructorId(json.getString("constructorId"));
                    tempCons.setUrl(new URL(json.getString("url")));
                    tempCons.setName(json.getString("name"));
                    tempCons.setNationality(json.getString("nationality"));

                }catch(JSONException e){
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
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

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
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
