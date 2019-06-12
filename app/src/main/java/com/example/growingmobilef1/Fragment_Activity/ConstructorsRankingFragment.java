package com.example.growingmobilef1.Fragment_Activity;


import android.support.v4.app.Fragment;

import android.content.Context;

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
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.ConstructorsDataHelper;

import com.example.growingmobilef1.Model.ConstructorStandings;
import com.example.growingmobilef1.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class ConstructorsRankingFragment extends Fragment {

    public final static String CONSTRUCTORS_RANKING_FRAGMENT_TAG = "CONSTRUCTORS_RANKING_FRAGMENT";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mPgsBar;
    private View vView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static ConstructorsRankingFragment newInstance() {
        return new ConstructorsRankingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vView = inflater.inflate(R.layout.fragment_constructors_ranking, container, false);

        // Call the async class to perform the api call
        CalendarApiAsyncCaller vLongOperation = new CalendarApiAsyncCaller();
        vLongOperation.execute();

        // get objects
        mRecyclerView = (RecyclerView) vView.findViewById(R.id.list); // list
        mPgsBar = (ProgressBar)vView.findViewById(R.id.progress_loaderC); // progressbar
        mSwipeRefreshLayout = (SwipeRefreshLayout) vView.findViewById(R.id.swipeRefreshConstructos);

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
        mAdapter = new ConstructorsAdapter(new ArrayList<ConstructorStandings>());
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

        @Override
        protected String doInBackground(String... params) {
            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();
            vConstructorsDataHelper = new ConstructorsDataHelper();

            // get json from api
            vJsonToParse = vApiRequestHelper.getContentFromUrl("https://ergast.com/api/f1/current/constructorStandings.json");

            // parse json to list
            mConstructorsItemArraylist =  vConstructorsDataHelper.getArraylist(vJsonToParse);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ((ConstructorsAdapter)mAdapter).updateData(mConstructorsItemArraylist);
            runLayoutAnimation(mRecyclerView);

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

    private void runLayoutAnimation(final RecyclerView recyclerView) {

        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right);

        recyclerView.setLayoutAnimation(controller);
        // update view
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }
}
