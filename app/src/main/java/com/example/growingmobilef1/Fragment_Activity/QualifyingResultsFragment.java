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

import com.example.growingmobilef1.Adapter.QualifyingResultsAdapter;
import com.example.growingmobilef1.Database.ModelRoom.RoomQualifyingResult;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ViewModel.QualifyingResultsViewModel;
import com.example.growingmobilef1.Model.QualifyingResults;
import com.example.growingmobilef1.R;
import com.example.growingmobilef1.Utils.LayoutAnimations;

import java.util.ArrayList;
import java.util.List;

public class QualifyingResultsFragment extends Fragment {

    private static final String QUAL_API_CALLER = "QUAL API CALLER";
    public static final String QUALIFYING_ITEM = "QI";

    private ArrayList<RoomQualifyingResult> mQualResultsArrayList;
    private RoomRace mRace;
    private QualifyingResultsAdapter mAdapter;
    private LayoutAnimations mLayoutAnimation;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;

    // Database
    private QualifyingResultsViewModel qualifyingViewModel;
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

        mAdapter = new QualifyingResultsAdapter(new ArrayList<RoomQualifyingResult>());
        qualifyingViewModel = ViewModelProviders.of(this).get(QualifyingResultsViewModel.class);

        Bundle vStartBundle = getArguments();
        if(vStartBundle != null){
            mRace = (RoomRace)vStartBundle.getSerializable(QUALIFYING_ITEM);
        }

        qualifyingViewModel.getAllQualReslts().observe(this, new Observer<List<RoomQualifyingResult>>() {
            @Override
            public void onChanged(List<RoomQualifyingResult> roomQualifyingResults) {

                mQualResultsArrayList = (ArrayList<RoomQualifyingResult>) roomQualifyingResults;
                //mAdapter.updateData(roomQualifyingResults);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vView = inflater.inflate(R.layout.fragment_race_results, container, false);

        mRecyclerView = vView.findViewById(R.id.list_race_results);
        mSwipeRefresh = vView.findViewById(R.id.race_results_frag_swipe);

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
}
