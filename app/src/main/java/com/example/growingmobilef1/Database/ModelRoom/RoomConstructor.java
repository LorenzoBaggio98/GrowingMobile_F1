package com.example.growingmobilef1.Database.ModelRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.growingmobilef1.Model.Constructor;
import com.example.growingmobilef1.Model.ConstructorStandings;
import com.example.growingmobilef1.Model.IListableModel;

@Entity(tableName = "constructor")
public class RoomConstructor implements IListableModel {

    @PrimaryKey
    @NonNull
    public String constructorId;

    public String name;
    public String nationality;

    public int rankPosition;
    public int rankPoints;

    public ConstructorStandings toConstructorStanding(){

        // build constructor
        Constructor constructor = new Constructor();
        constructor.setName(name);
        constructor.setNationality(nationality);
        constructor.setConstructorId(constructorId);

        // build ConstructorStandings
        ConstructorStandings temp = new ConstructorStandings();

        temp.setPoints(rankPoints);
        temp.setPosition(rankPosition);
        temp.setConstructor(constructor);


        return temp;
    }

}
