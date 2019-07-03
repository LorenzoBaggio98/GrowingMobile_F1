package com.example.growingmobilef1.Fragment_Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.growingmobilef1.Adapter.DriverAdapter;
import com.example.growingmobilef1.Database.ModelRoom.RoomDriver;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ViewModel.DriverViewModel;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.ConnectionStatusHelper;
import com.example.growingmobilef1.Helper.ConstructorsDataHelper;
import com.example.growingmobilef1.Helper.DriversRankingHelper;
import com.example.growingmobilef1.Model.DriverStandings;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.R;
import com.example.growingmobilef1.Utils.LayoutAnimations;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class DriversRankingFragment extends Fragment  implements ApiAsyncCallerFragment.IOnApiCalled{

    public final static String DRIVER_API_CALLER = "DRIVER api caller tag";
    private static final String SAVE_LISTPILOTS = "SAVE_LISTPILOTS";

    private ArrayList<IListableModel> mArrayListPilots;
    private RecyclerView mRecyclerViewList;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LayoutAnimations mLayoutAnimation;

    private boolean stateProgresBar = true;
    private DriverAdapter vDriversAdapter;

    //PilotsApiAsync vPilotsApiAsync;
    private ApiAsyncCallerFragment mApiCallerFragment;

    private DriverViewModel driverViewModel;

    public static DriversRankingFragment newInstance() {
        return new DriversRankingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vDriversAdapter = new DriverAdapter(new ArrayList<RoomDriver>(), getContext());

        driverViewModel = ViewModelProviders.of(this).get(DriverViewModel.class);
        driverViewModel.getAllDriver().observe(this, new Observer<List<RoomDriver>>() {
            @Override
            public void onChanged(List<RoomDriver> roomDrivers) {

                vDriversAdapter.updateData(roomDrivers);
                makeNewRecycleView();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vView = inflater.inflate(R.layout.fragment_pilots_ranking, container, false);

        mApiCallerFragment = (ApiAsyncCallerFragment) getFragmentManager().findFragmentByTag(DRIVER_API_CALLER);
        if (mApiCallerFragment == null){
            launchApiCallerFragment();
        }

        mRecyclerViewList = vView.findViewById(R.id.recyclerViewPiloti);
        mProgressBar = vView.findViewById(R.id.frag_calendar_progress_bar);
        mSwipeRefreshLayout = vView.findViewById(R.id.swipeRefreshPilots);

        mLayoutAnimation = new LayoutAnimations();

        mRecyclerViewList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewList.setLayoutManager(linearLayoutManager);

        mRecyclerViewList.setAdapter(vDriversAdapter);

        if (savedInstanceState != null) {

            mArrayListPilots = (ArrayList<IListableModel>) savedInstanceState.getSerializable(SAVE_LISTPILOTS);
            //makeNewRecycleView(mArrayListPilots);
            mProgressBar.setVisibility(View.INVISIBLE);

        } else {
           /* if (ConnectionStatusHelper.statusConnection(getContext())){
                Toast.makeText(getContext(),"Si connessione", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(),"No connessione", Toast.LENGTH_SHORT).show();
            }*/
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(ConnectionStatusHelper.statusConnection(getContext())){
                    startCall();
                }else{
                    Toast.makeText(getContext(),"Non c'è connessione Internet", Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        return vView;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if(mApiCallerFragment != null) {
            mApiCallerFragment.stopCall();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVE_LISTPILOTS, mArrayListPilots);
    }

    /**
     *
     */
    /*private class PilotsApiAsync extends AsyncTask<String, Void, String> {

        private JSONObject vJsonObjectToParse;
        private ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();

        @Override
        protected String doInBackground(String... params) {

            if (stateProgresBar)
                mProgressBar.setVisibility(View.VISIBLE);

            vJsonObjectToParse = vApiRequestHelper.getContentFromUrl("https://ergast.com/api/f1/current/driverStandings.json");

            if(mArrayListPilots == null) {
                mArrayListPilots = DriversRankingHelper.getArrayListPilotsPoints(vJsonObjectToParse);
                //insertDriversToDb();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            makeNewRecycleView();

            if (stateProgresBar) {
                mProgressBar.setVisibility(View.INVISIBLE);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                stateProgresBar = true;
            }
        }
    }*/

    void insertDriversToDb(){

        for(int i=0; i< mArrayListPilots.size(); i++){
            driverViewModel.insertDriver((RoomDriver)mArrayListPilots.get(i));
        }
    }

    private void launchApiCallerFragment(){

        FragmentTransaction vFT = getChildFragmentManager().beginTransaction();
        mApiCallerFragment = ApiAsyncCallerFragment.getInstance();
        vFT.add(mApiCallerFragment, DRIVER_API_CALLER);
        vFT.commit();
    }

    /**
     *
     */
    void startCall() {
        DriversRankingHelper vDataHelper = new DriversRankingHelper();
        mApiCallerFragment.startCall("https://ergast.com/api/f1/current/driverStandings.json", vDataHelper);
    }

    @Override
    public void onApiCalled(ArrayList<IListableModel> aReturnList) {

        mArrayListPilots = aReturnList;

        //Inserisco su DB
        insertDriversToDb();

        makeNewRecycleView();
        mApiCallerFragment.stopCall();
    }

    private void makeNewRecycleView(){
        mProgressBar.setVisibility(View.GONE);
        mLayoutAnimation.runLayoutAnimation(mRecyclerViewList);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
