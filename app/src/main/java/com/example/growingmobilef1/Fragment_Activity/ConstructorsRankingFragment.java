package com.example.growingmobilef1.Fragment_Activity;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar mPgsBar;
    private View vView;

    public static ConstructorsRankingFragment newInstance() {
        return new ConstructorsRankingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vView = inflater.inflate(R.layout.fragment_constructors_ranking, container, false);

        // Call the async class to perform the api call
        CalendarApiAsyncCaller vLongOperation = new CalendarApiAsyncCaller();
        vLongOperation.execute();

        recyclerView = (RecyclerView) vView.findViewById(R.id.list);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ConstructorsAdapter(new ArrayList<ConstructorStandings>());
        recyclerView.setAdapter(mAdapter);

        // progressbar
        mPgsBar = (ProgressBar)vView.findViewById(R.id.progress_loaderC);
        // start loading
        mPgsBar.setVisibility(vView.VISIBLE);


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

            if(vJsonToParse == null) {
                Toast.makeText(getActivity(), "Can't fetch ranking, check internet connection", Toast.LENGTH_LONG);
            } else {
                mPgsBar.setVisibility(vView.GONE);
            }

        }
    }

}
