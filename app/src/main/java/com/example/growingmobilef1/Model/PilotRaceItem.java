package com.example.growingmobilef1.Model;

public class PilotRaceItem  {
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

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getUrl() {
        return url;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public int getPoints() {
        return points;
    }
}
