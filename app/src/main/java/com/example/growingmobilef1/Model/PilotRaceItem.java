package com.example.growingmobilef1.Model;

import com.example.growingmobilef1.Interface.IListableObject;



public class PilotRaceItem implements IListableObject {
    private int driverId;
    private String url;
    private String givenName;
    private String familyName;
    private String dateOfBirth;
    private String nationality;
    private int points;



    public PilotRaceItem() {
    }

    public PilotRaceItem(int driverId, String givenName, String familyName, int points) {
        this.driverId = driverId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.points = points;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
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

    @Override
    public int getmId() {
        return driverId;
    }

    @Override
    public String getmMainInformation() {
        return givenName;
    }

    @Override
    public String getmOptionalInformation() {
        return familyName;
    }

    @Override
    public String getmSecondaryInformation() {
        return  ""+points;
    }

    @Override
    public Boolean isButtonRequired() {
        return false;
    }
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
