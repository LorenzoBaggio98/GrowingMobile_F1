package com.example.growingmobilef1.Database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.growingmobilef1.Database.FormulaRepository;
import com.example.growingmobilef1.Database.ModelRoom.RoomRaceResult;

import java.util.List;

public class RaceResultsViewModel extends AndroidViewModel {

    private FormulaRepository repository;
    private LiveData<List<RoomRaceResult>> allResults;

    public RaceResultsViewModel(Application application) {
        super(application);

        repository = new FormulaRepository(application);
        allResults = repository.getAllRaceResults();
    }

    public LiveData<List<RoomRaceResult>> getAllResults(){ return allResults; }

    public void insertResults(RoomRaceResult result){
        repository.insertItem(result);
    }

}
