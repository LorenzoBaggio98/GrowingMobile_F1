package com.example.growingmobilef1.Database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.growingmobilef1.Database.FormulaRepository;
import com.example.growingmobilef1.Database.InterfaceDao.RaceResultsDao;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
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

    public LiveData<List<RoomRaceResult>> getRaceResults(String race_id){
        return repository.getRaceResultsRequested(race_id);
    }

    public LiveData<List<RaceResultsDao.RoomPodium>> getRaceResultPodium(List<String> races){
        return repository.getRaceResultPodium(races);
    }

    public void insertResults(RoomRaceResult result){
        repository.insertItem(result);
    }

}
