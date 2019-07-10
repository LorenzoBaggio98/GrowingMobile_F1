package com.example.growingmobilef1.Fragment_Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.growingmobilef1.Adapter.RacesAdapter;
import com.example.growingmobilef1.Database.FormulaRepository;
import com.example.growingmobilef1.Database.InterfaceDao.RaceResultsDao;
import com.example.growingmobilef1.Database.ModelRoom.RoomRace;
import com.example.growingmobilef1.Database.ViewModel.RaceResultsViewModel;
import com.example.growingmobilef1.Database.ViewModel.RacesViewModel;
import com.example.growingmobilef1.Helper.CalendarRaceDataHelper;
import com.example.growingmobilef1.Helper.ConnectionStatusHelper;
import com.example.growingmobilef1.Model.IListableModel;
import com.example.growingmobilef1.Model.RaceResults;
import com.example.growingmobilef1.R;
import com.example.growingmobilef1.Utils.LayoutAnimations;
import com.example.growingmobilef1.Utils.NotificationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarFragment extends Fragment implements RacesAdapter.IOnRaceClicked, RacesAdapter.IOnNotificationIconClicked, ApiAsyncCallerFragment.IOnApiCalled{

    public final static String CALENDAR_API_CALLER = "Constructor api caller tag";

    private ArrayList<RoomRace> mCalendarRaceItemArraylist;
    private HashMap<String, List<RaceResultsDao.RoomPodium>> mRaceResultsMap;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView.LayoutManager mLayoutManager;
    private RacesAdapter mAdapter;
    private ProgressBar mPgsBar;

    // Notification
    private NotificationUtil mNotificationUtil;
    private LayoutAnimations mLayoutAnimations;

    // Database
    private RacesViewModel racesViewModel;
    private RaceResultsViewModel raceResultsViewModel;

    private ApiAsyncCallerFragment mApiCallerFragment;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRaceResultsMap = new HashMap<>();
        mCalendarRaceItemArraylist = new ArrayList<>();

        //
        mAdapter = new RacesAdapter(getContext(),
                new ArrayList<RoomRace>(),
                new HashMap<>(),
                this,
                this
        );


        // ViewModel creato da Provider
        racesViewModel = ViewModelProviders.of(this).get(RacesViewModel.class);
        raceResultsViewModel = ViewModelProviders.of(this).get(RaceResultsViewModel.class);

        // Observer lista race
        racesViewModel.getAllRaces().observe(this, new Observer<List<RoomRace>>() {
            @Override
            public void onChanged(List<RoomRace> roomRaces) {

                mCalendarRaceItemArraylist = (ArrayList<RoomRace>) roomRaces;

                ArrayList<String> racesId = new ArrayList<>();

                // Prendo il podio dei risultati
                for (RoomRace race: roomRaces) {
                    racesId.add(race.circuitId);
                }

                getPodium(racesId);

                // Update Race list su Adapter
                mAdapter.updateData(roomRaces, null);
                listBeforeViewing();
            }
        });
    }

    /**
     * Recupero il Podio per le gare passate
     * @param race Lista di Race_id di tutte le gare, passata per controllare i risultati di ogni gara
     */
    public void getPodium(ArrayList<String> race){

        raceResultsViewModel.getRaceResultPodium(race).observeForever( new Observer<List<RaceResultsDao.RoomPodium>>() {
            @Override
            public void onChanged(List<RaceResultsDao.RoomPodium> roomPodiums) {

                if(roomPodiums != null && !roomPodiums.isEmpty()){

                    for(int i=0; i<roomPodiums.size(); i++){

                        String currentRace = roomPodiums.get(i).res_race_id;

                        List<RaceResultsDao.RoomPodium> temp = roomPodiums
                                .stream()
                                .filter(roomPodium -> roomPodium.res_race_id.equals(currentRace))
                                .collect(Collectors.toList());

                        mRaceResultsMap.put(roomPodiums.get(i).res_race_id, temp);
                    }

                    mAdapter.updateData(null, mRaceResultsMap);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_calendar, container, false);

        mRecyclerView = vView.findViewById(R.id.frag_calendar_listview);
        mPgsBar = vView.findViewById(R.id.frag_calendar_progress_bar);
        mSwipeRefresh = vView.findViewById(R.id.frag_calendar_refresh_layout);

        // Animazione della lista
        mLayoutAnimations = new LayoutAnimations();

        // Call the async class to perform the api call
        mApiCallerFragment = (ApiAsyncCallerFragment) getFragmentManager().findFragmentByTag(CALENDAR_API_CALLER);
        if (mApiCallerFragment == null){
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
                    mPgsBar.setVisibility(View.GONE);
                    mSwipeRefresh.setRefreshing(false);
                }
            }
        });

        return vView;
    }

    @Override
    public void onStart() {
        super.onStart();

        firstCall();
    }

    private void firstCall(){
        if (ConnectionStatusHelper.statusConnection(getContext())){
            if (mApiCallerFragment == null){
                launchApiCallerFragment();
            }
            startCall();

        }else{
            Toast.makeText(getContext(),"Non c'è connessione Internet", Toast.LENGTH_SHORT).show();
            mPgsBar.setVisibility(View.GONE);
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mApiCallerFragment != null) {
            mApiCallerFragment.stopCall();
        }
    }

    private void launchApiCallerFragment(){
        FragmentTransaction vFT = getChildFragmentManager().beginTransaction();
        mApiCallerFragment = ApiAsyncCallerFragment.getInstance();
        vFT.add(mApiCallerFragment, CALENDAR_API_CALLER);
        vFT.commit();
    }

    /**
     * Override metodo RecycleView
     * @param aPosition
     */
    @Override
    public void onRaceClicked(int aPosition) {
        RoomRace vRaceItem = new RoomRace();
        long vId = mCalendarRaceItemArraylist.get(aPosition).round;

        boolean isFound = true;
        int i = 0;

        /* Looks for the clicked item in the ArrayList, then pass it to the detail fragment */
        while(isFound) {

            if (mCalendarRaceItemArraylist.get(i).round == vId) {
                vRaceItem = mCalendarRaceItemArraylist.get(aPosition);
                isFound = false;
            }
            i++;
        }

        launchRaceDetailActivity(vRaceItem);
    }

    /**
     * Avvio del dettaglio race
     * @param aRaceItem
     */
    private void launchRaceDetailActivity(RoomRace aRaceItem){

        Intent intent = new Intent(getContext(), RaceDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle bundle = new Bundle();

        bundle.putSerializable(RaceDetailActivity.RACE_ITEM, aRaceItem);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Override metodo RecycleView
     * @param aPosition
     */
    @Override
    public void onNotificationScheduled(int aPosition) {
        // Send notification to the system
        mNotificationUtil = new NotificationUtil(
                mCalendarRaceItemArraylist.get(aPosition).dateToCalendar(),
                getContext(),
                mCalendarRaceItemArraylist.get(aPosition)
        );
        mNotificationUtil.sendNotification();

        // Update the notificationSet status in the db
        int vNotificationStatus = 0;
        if (mCalendarRaceItemArraylist.get(aPosition).notification == 0){
            vNotificationStatus = 1;
        }
        racesViewModel.updateRaceNotification(mCalendarRaceItemArraylist.get(aPosition), vNotificationStatus);
    }

    /**
     * Database calls
     */
    void insertRacesToDb(){

        for(int i=0; i< mCalendarRaceItemArraylist.size(); i++){
            racesViewModel.insertRace(mCalendarRaceItemArraylist.get(i));
        }
    }

    //
    public void listBeforeViewing(){

        mPgsBar.setVisibility(View.GONE);
        mLayoutAnimations.runLayoutAnimation(mRecyclerView);
        mSwipeRefresh.setRefreshing(false);
    }

    public void scrollTo(){

        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        smoothScroller.setTargetPosition(mAdapter.getOffsetOccurred());
        mLayoutManager.startSmoothScroll(smoothScroller);
    }

    // Eseguire la chiamata per ricevere i dati da Ergast
    public void startCall(){
        CalendarRaceDataHelper vDataHelper = new CalendarRaceDataHelper();
        mApiCallerFragment.startCall("https://ergast.com/api/f1/current.json", vDataHelper);
    }

    @Override
    public void onApiCalled(ArrayList<IListableModel> aRaceList) {

        // Da IListableModel a RoomRace
        for (IListableModel temp: aRaceList) {
            mCalendarRaceItemArraylist.add((RoomRace) temp);
        }

        // Inserisco su DB
        insertRacesToDb();

        listBeforeViewing();
        mApiCallerFragment.stopCall();
    }
}
