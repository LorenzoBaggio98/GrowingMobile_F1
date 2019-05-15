package com.example.growingmobilef1.Model;

import com.example.growingmobilef1.Interface.IListableObject;

public class ConstructorsItem implements IListableObject {

    private String constructorId;
    private String url;
    private String name;
    private String nationality;
    private int position;
    private int points;
    private int wins;

    public ConstructorsItem() {}

    public ConstructorsItem(String constructorId, String url, String name,
                       String nationality)
    {
        this.constructorId = constructorId;
        this.url = url;
        this.name = name;
        this.nationality = nationality;
        this.position = position;
        this.points = points;
        this.wins = wins;
        }


        public int getPosition()
        {
            return position;
        }


        public void setPosition(int position)
        {
            this.position = position;
        }


        public int getPoints()
        {
            return points;
        }


        public void setPoints(int points)
        {
            this.points = points;
        }


        public int getWins()
        {
            return wins;
        }


        public void setWins(int wins)
        {
            this.wins = wins;
        }

    public String getConstructorId()
    {
        return constructorId;
    }

    public void setConstructorId(String constructorId)
    {
        this.constructorId = constructorId;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNationality()
    {
        return nationality;
    }

    public void setNationality(String nationality)
    {
        this.nationality = nationality;
    }

    @Override
    public int getmId() {
        return 0;
    }

    @Override
    public Boolean isButtonRequired() {
        return false;
    }

    @Override
    public String getmMainInformation() {
        return name;
    }

    @Override
    public String getmOptionalInformation() {
        return "" + position;
    }

    @Override
    public String getmSecondaryInformation() {
        return "" + points;
    }
}
