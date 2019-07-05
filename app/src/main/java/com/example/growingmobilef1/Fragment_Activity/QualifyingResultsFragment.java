package com.example.growingmobilef1.Fragment_Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.growingmobilef1.Adapter.QualifyingResultsAdapter;
import com.example.growingmobilef1.Database.ModelRoom.RoomDriver;
import com.example.growingmobilef1.Database.ModelRoom.RoomQualifyingResult;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ViewModel.DriverViewModel;
import com.example.growingmobilef1.Database.ViewModel.QualifyingResultsViewModel;
import com.example.growingmobilef1.Helper.ConnectionStatusHelper;
import com.example.growingmobilef1.Helper.QualifyingResultsDataHelper;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.R;
import com.example.growingmobilef1.Utils.LayoutAnimations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class QualifyingResultsFragment extends Fragment implements ApiAsyncCallerFragment.IOnApiCalled {

    private static final String QUAL_API_CALLER = "QUAL API CALLER";
    public static final String QUALIFYING_ITEM = "QI";

    private ArrayList<RoomQualifyingResult> mQualResultsArrayList;
    private RoomRace mRace;
    private QualifyingResultsAdapter mAdapter;
    private LayoutAnimations mLayoutAnimation;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;

    private TextView dateQualifying;

    // Database
    private QualifyingResultsViewModel qualifyingViewModel;
    private DriverViewModel driverViewModel;

    private ApiAsyncCallerFragment mApiCallerFragment;

    public static QualifyingResultsFragment newInstance(RoomRace aRace){

        Bundle vBundle = new Bundle();
        vBundle.putSerializable(QUALIFYING_ITEM, aRace);

        QualifyingResultsFragment vFragment = new QualifyingResultsFragment();
        vFragment.setArguments(vBundle);

        return vFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new QualifyingResultsAdapter(new ArrayList<RoomQualifyingResult>(), new ArrayList<RoomDriver>());

        qualifyingViewModel = ViewModelProviders.of(this).get(QualifyingResultsViewModel.class);
        driverViewModel = ViewModelProviders.of(this).get(DriverViewModel.class);

        Bundle vStartBundle = getArguments();
        if(vStartBundle != null){
            mRace = (RoomRace)vStartBundle.getSerializable(QUALIFYING_ITEM);
        }

        qualifyingViewModel.getRaceQualifyingResults(mRace.circuitId).observe(this, new Observer<List<RoomQualifyingResult>>() {
            @Override
            public void onChanged(List<RoomQualifyingResult> roomQualifyingResults) {

                mQualResultsArrayList = (ArrayList<RoomQualifyingResult>) roomQualifyingResults;
                mAdapter.updateData(roomQualifyingResults);
                listBeforeViewing();
            }
        });

        // Prendo tutti i driver
        driverViewModel.getAllDriver().observe(this, new Observer<List<RoomDriver>>() {
            @Override
            public void onChanged(@Nullable List<RoomDriver> roomDrivers) {
                mAdapter.addAllDriver(roomDrivers);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vView = inflater.inflate(R.layout.fragment_race_results, container, false);

        mRecyclerView = vView.findViewById(R.id.list_race_results);
        mSwipeRefresh = vView.findViewById(R.id.race_results_frag_swipe);

        dateQualifying = vView.findViewById(R.id.race_results_date);
        setDateQualifying();

        //Set Q labels
        TextView q1 = vView.findViewById(R.id.lblq1);
        q1.setText("Q1");

        TextView q2 = vView.findViewById(R.id.lblq2);
        q2.setText("Q2");

        TextView q3 = vView.findViewById(R.id.lblq3);
        q3.setText("Q3");

        mLayoutAnimation = new LayoutAnimations();

        mApiCallerFragment = (ApiAsyncCallerFragment) getFragmentManager().findFragmentByTag(QUAL_API_CALLER);
        if(mApiCallerFragment == null){
            launchApiCallerFragment();
        }

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Se c'è connessione faccio il refresh
                if (ConnectionStatusHelper.statusConnection(getContext())){
                    if (mApiCallerFragment == null){
                        launchApiCallerFragment();
                    }
                    startCall();

                }else{
                    Toast.makeText(getContext(),"Non c'è connessione Internet", Toast.LENGTH_SHORT).show();
                    mSwipeRefresh.setRefreshing(false);
                }
            }
        });

        return vView;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if(mApiCallerFragment != null){
            mApiCallerFragment.stopCall();
        }
    }

    private void launchApiCallerFragment(){
        FragmentTransaction vFT = getChildFragmentManager().beginTransaction();
        mApiCallerFragment = ApiAsyncCallerFragment.getInstance();
        vFT.add(mApiCallerFragment, QUAL_API_CALLER);
        vFT.commit();
    }

    public void startCall(){
        QualifyingResultsDataHelper vDataHelper = new QualifyingResultsDataHelper();
        mApiCallerFragment.startCall("https://ergast.com/api/f1/current/"+mRace.round+"/qualifying.json", vDataHelper);
    }

    public void setDateQualifying(){

        String qualifyingDate = mRace.qualifyingDate();
        dateQualifying.setText(qualifyingDate);
    }

    @Override
    public void onApiCalled(ArrayList<IListableModel> aReturnList) {

        // Da IListableModel a RoomRace
        for (IListableModel temp: aReturnList) {
            mQualResultsArrayList.add((RoomQualifyingResult) temp);
        }

        // Inserisco su DB
        insertQualResultsToDb();

        listBeforeViewing();
        mApiCallerFragment.stopCall();
    }

    void insertQualResultsToDb(){

        for(int i=0; i< mQualResultsArrayList.size(); i++){
            qualifyingViewModel.insert(mQualResultsArrayList.get(i));
        }
    }

    public void listBeforeViewing(){
        mLayoutAnimation.runLayoutAnimation(mRecyclerView);
        mSwipeRefresh.setRefreshing(false);
    }
}
