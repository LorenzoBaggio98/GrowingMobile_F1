package com.example.growingmobilef1.Fragment_Activity;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.growingmobilef1.Adapter.ConstructorsAdapter;
import com.example.growingmobilef1.Database.ViewModel.ConstructorViewModel;
import com.example.growingmobilef1.Database.ModelRoom.RoomConstructor;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.ConstructorsDataHelper;

import com.example.growingmobilef1.Model.ConstructorStandings;
import com.example.growingmobilef1.R;
import com.example.growingmobilef1.Utils.LayoutAnimations;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConstructorsRankingFragment extends Fragment {

    public final static String CONSTRUCTORS_RANKING_FRAGMENT_TAG = "CONSTRUCTORS_RANKING_FRAGMENT";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mPgsBar;
    private View vView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LayoutAnimations mLayoutAnimation;
    private ConstructorViewModel constructorViewModel;

    public static ConstructorsRankingFragment newInstance() {
        return new ConstructorsRankingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ConstructorsAdapter(new ArrayList<RoomConstructor>());

        // ViewModel creato da Provider
        constructorViewModel = ViewModelProviders.of(this).get(ConstructorViewModel.class);

        constructorViewModel.getAllConstructors().observe(this, new Observer<List<RoomConstructor>>() {
            @Override
            public void onChanged(@Nullable List<RoomConstructor> vConstructors) {

                if (vConstructors != null) {
                    mPgsBar.setVisibility(vView.GONE);
                    ((ConstructorsAdapter)mAdapter).updateData(vConstructors);
                } else {
                    mPgsBar.setVisibility(vView.VISIBLE);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vView = inflater.inflate(R.layout.fragment_constructors_ranking, container, false);

        // Call the async class to perform the api call
        refreshItems();

        // get objects
        mRecyclerView = vView.findViewById(R.id.list); // list
        mPgsBar = vView.findViewById(R.id.progress_loaderC); // progressbar
        mSwipeRefreshLayout = vView.findViewById(R.id.swipeRefreshConstructos);
        mLayoutAnimation = new LayoutAnimations();

        // start loading progress bar
        mPgsBar.setVisibility(vView.VISIBLE);

        // set list animation
        int resId = R.anim.layout_animation_slide_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(container.getContext(), resId);
        mRecyclerView.setLayoutAnimation(animation);

        // create list
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);



        // create swipe refresh listener...
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // refresh items
                refreshItems();
            }
        });

        return vView;
    }

    // Private class needed to perform the API call asynchronously
    private class CalendarApiAsyncCaller extends AsyncTask<String, Void, String> {

        private JSONObject vJsonToParse;
        private ConstructorsDataHelper vConstructorsDataHelper;
        private ArrayList<ConstructorStandings> mConstructorsItemArraylist;

        public CalendarApiAsyncCaller() {

        }

        @Override
        protected String doInBackground(String... params) {
            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();
            vConstructorsDataHelper = new ConstructorsDataHelper();

            // get json from api
            vJsonToParse = vApiRequestHelper.getContentFromUrl("https://ergast.com/api/f1/current/constructorStandings.json");

            // parse json to list
            mConstructorsItemArraylist =  vConstructorsDataHelper.getArraylist(vJsonToParse);

            for(ConstructorStandings cs : mConstructorsItemArraylist) {
                constructorViewModel.insert(cs.toRoomConstructor());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            mLayoutAnimation.runLayoutAnimation(mRecyclerView);

            if(vJsonToParse == null) {
                Toast.makeText(getActivity(), "Can't fetch ranking, check internet connection", Toast.LENGTH_LONG);
            } else {

                switch (mPgsBar.getVisibility())
                {
                    case View.GONE:
                        mSwipeRefreshLayout.setRefreshing(false);
                        break;
                    case View.VISIBLE:

                        mPgsBar.setVisibility(vView.GONE);
                        break;
                }

            }
        }
    }

    void refreshItems() {
        // Load items
        // Call the async class to perform the api call
        CalendarApiAsyncCaller vLongOperation = new CalendarApiAsyncCaller();
        vLongOperation.execute();
    }

}
