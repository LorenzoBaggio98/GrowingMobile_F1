package com.example.growingmobilef1.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.growingmobilef1.Database.ModelRoom.RoomRace;

import java.util.List;

public class RacesViewModel extends AndroidViewModel {

    private FormulaRepository repository;
    private LiveData<List<RoomRace>> allRaces;

    public RacesViewModel(Application application) {
        super(application);

        repository = new FormulaRepository(application);
        allRaces = repository.getAllRaces();
    }

    public LiveData<List<RoomRace>> getAllRaces() {
        return allRaces;
    }

    public void insert(RoomRace race){ repository.insertRace(race);}

}
