package com.example.growingmobilef1.Fragment_Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.growingmobilef1.Adapter.QualifyingResultsAdapter;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.QualifyingResultsDataHelper;
import com.example.growingmobilef1.Model.QualifyingResults;
import com.example.growingmobilef1.Model.Races;
import com.example.growingmobilef1.R;
import org.json.JSONObject;
import java.util.ArrayList;

public class QualifyingResultsFragment extends Fragment {

    public static final String QUALIFYING_ITEM = "QI";

    private ArrayList<QualifyingResults> mQualResultsArrayList;
    private Races mRace;

    private QualifyingResultsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    public static QualifyingResultsFragment newInstance(Races aRace){

        Bundle vBundle = new Bundle();
        vBundle.putSerializable(QUALIFYING_ITEM, aRace);

        QualifyingResultsFragment vFragment = new QualifyingResultsFragment();
        vFragment.setArguments(vBundle);

        return vFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vView = inflater.inflate(R.layout.fragment_race_results, container, false);

        mRecyclerView = vView.findViewById(R.id.list_race_results);

        Bundle vStartBundle = getArguments();
        if(vStartBundle != null){

            mRace = (Races)vStartBundle.getSerializable(QUALIFYING_ITEM);
        }

        QualResultsApiAsyncCaller vLongOperation = new QualResultsApiAsyncCaller();
        vLongOperation.execute();


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new QualifyingResultsAdapter(new ArrayList<QualifyingResults>());
        mRecyclerView.setAdapter(mAdapter);

        return vView;
    }

    /**
     *
     */
    private class QualResultsApiAsyncCaller extends AsyncTask<String, Void, String>{

        private JSONObject vJsonToParse;
        private QualifyingResultsDataHelper vQRDataHelper;

        @Override
        protected String doInBackground(String... strings) {

            ApiRequestHelper vARHelper = new ApiRequestHelper();
            vQRDataHelper = new QualifyingResultsDataHelper();

            String downloadUrl = String.format("http://ergast.com/api/f1/current/%s/qualifying.json", mRace.getRound());
            vJsonToParse = vARHelper.getContentFromUrl(downloadUrl);
            mQualResultsArrayList = vQRDataHelper.getQualResults(vJsonToParse);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            mAdapter.updateData(mQualResultsArrayList);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

}
