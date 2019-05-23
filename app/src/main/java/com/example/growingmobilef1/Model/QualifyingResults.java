package com.example.growingmobilef1.Model;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class QualifyingResults implements Serializable {

    private int number;
    private int position;

    private Driver driver;
    private Constructor constructor;

    private String Q1;
    private String Q2;
    private String Q3;

    public QualifyingResults(){

    }

    public static QualifyingResults fromJson(JSONObject json){

        QualifyingResults temp = new QualifyingResults();

        Driver tempDriver = new Driver();
        Constructor tempConst = new Constructor();

        if(json.length() != 0){
            try{

                temp.setNumber(json.getInt("number"));
                temp.setPosition(json.getInt("position"));
                temp.setDriver(tempDriver.fromJson(json.getJSONObject("Driver")));
                temp.setConstructor(tempConst.fromJson(json.getJSONObject("Constructor")));

                temp.setQ1(json.optString("Q1"));
                temp.setQ2(json.optString("Q2"));
                temp.setQ3(json.optString("Q3"));

            }catch (JSONException e){
                e.printStackTrace();
            }
        }


        return temp;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public void setConstructor(Constructor constructor) {
        this.constructor = constructor;
    }

    public String getQ1() {
        return Q1;
    }

    public void setQ1(String q1) {
        Q1 = q1;
    }

    public String getQ2() {
        return Q2;
    }

    public void setQ2(String q2) {
        Q2 = q2;
    }

    public String getQ3() {
        return Q3;
    }

    public void setQ3(String q3) {
        Q3 = q3;
    }
}
