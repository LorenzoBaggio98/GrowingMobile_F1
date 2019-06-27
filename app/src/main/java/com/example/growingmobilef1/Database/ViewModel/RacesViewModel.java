package com.example.growingmobilef1.Database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.growingmobilef1.Database.FormulaRepository;
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

    public void insertRace(RoomRace race){ repository.insertItem(race);}

    public void insertRaceList(List<RoomRace> races){

        for(int i=0; i< races.size(); i++){
            repository.insertItem(races.get(i));
        }
    }

}
