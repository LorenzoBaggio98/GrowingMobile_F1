package com.example.growingmobilef1.Database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.example.growingmobilef1.Database.FormulaRepository;
import com.example.growingmobilef1.Database.ModelRoom.RoomQualifyingResult;
import java.util.List;

public class QualifyingResultsViewModel extends AndroidViewModel {

    private FormulaRepository mRepository;
    private LiveData<List<RoomQualifyingResult>> mAllQualifyingResults;

    public QualifyingResultsViewModel(Application application) {
        super(application);

        mRepository = new FormulaRepository(application);
        mAllQualifyingResults = mRepository.getAllQualResults();
    }

    public LiveData<List<RoomQualifyingResult>> getAllQualReslts() {
        return mAllQualifyingResults;
    }

    public LiveData<List<RoomQualifyingResult>> getRaceQualifyingResults(String race_id){
        return mRepository.getQualResultsRequested(race_id);
    }

    public void insert(RoomQualifyingResult race){ mRepository.insertItem(race);}

}
