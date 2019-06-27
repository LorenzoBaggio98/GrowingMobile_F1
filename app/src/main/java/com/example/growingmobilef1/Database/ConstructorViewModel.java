package com.example.growingmobilef1.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.example.growingmobilef1.Database.ModelRoom.RoomConstructor;
import java.util.List;


public class ConstructorViewModel extends AndroidViewModel {
    private FormulaRepository repository;
    private LiveData<List<RoomConstructor>> allConstructors;

    public ConstructorViewModel(Application application) {
        super(application);

        repository = new FormulaRepository(application);
        allConstructors = repository.getAllConstructors();
    }

    public LiveData<List<RoomConstructor>> getAllConstructors() {
        return allConstructors;
    }

    public void insert(RoomConstructor constructor){ repository.insertRace(constructor); }

    public void deleteAll() { repository.deleteAll();}
}
