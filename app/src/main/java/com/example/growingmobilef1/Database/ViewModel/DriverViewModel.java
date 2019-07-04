package com.example.growingmobilef1.Database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.growingmobilef1.Database.FormulaRepository;
import com.example.growingmobilef1.Database.ModelRoom.RoomDriver;

import java.util.List;

public class DriverViewModel extends AndroidViewModel {

    private FormulaRepository repository;
    private LiveData<List<RoomDriver>> allDriver;

    public DriverViewModel(Application application) {
        super(application);

        repository = new FormulaRepository(application);
        allDriver = repository.getAllDrivers();
    }

    public LiveData<List<RoomDriver>> getAllDriver() {
        return allDriver;
    }

    public void insertDriver(RoomDriver driver){ repository.insertItem(driver);}

}
