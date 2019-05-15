package com.example.growingmobilef1.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class Driver implements Serializable {

    private String driverId;
    private int permanentNumber;
    private String code;
    private URL url;
    private String givenName;
    private String familyName;
    private String dateOfBirth;
    private String nationality;

    public Driver fromJson(JSONObject json){

        Driver tempDriver = new Driver();

        if(json.length() != 0){
            try{

                tempDriver.driverId = json.getString("driverId");
                tempDriver.permanentNumber = json.getInt("permanentNumber");
                tempDriver.code = json.getString("code");
                tempDriver.url = new URL(json.getString("url"));
                tempDriver.givenName = json.getString("givenName");
                tempDriver.familyName = json.getString("familyName");
                tempDriver.dateOfBirth = json.getString("dateOfBirth");
                tempDriver.nationality = json.getString("nationality");

            }catch (JSONException e){
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return tempDriver;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public int getPermanentNumber() {
        return permanentNumber;
    }

    public void setPermanentNumber(int permanentNumber) {
        this.permanentNumber = permanentNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
