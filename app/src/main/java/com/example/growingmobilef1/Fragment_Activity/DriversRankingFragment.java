package com.example.growingmobilef1.Fragment_Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.growingmobilef1.Adapter.DriverAdapter;

import com.example.growingmobilef1.Helper.ConnectionStatusHelper;
import com.example.growingmobilef1.Helper.DriversRankingHelper;

import com.example.growingmobilef1.Model.DriverStandings;
import com.example.growingmobilef1.R;
import com.example.growingmobilef1.Utils.LayoutAnimations;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DriversRankingFragment extends Fragment {
    private static final String SAVE_LISTPILOTS = "SAVE_LISTPILOTS";

    private ArrayList<DriverStandings> mArrayListPilots;
    private RecyclerView mRecyclerViewList;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LayoutAnimations mLayoutAnimation;
    private DriverAdapter vDriversAdapter;

    public static DriversRankingFragment newInstance() {
        return new DriversRankingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_pilots_ranking, container, false);

        mArrayListPilots = new ArrayList<>();

        mRecyclerViewList = vView.findViewById(R.id.recyclerViewPiloti);
        mProgressBar = vView.findViewById(R.id.frag_calendar_progress_bar);
        mSwipeRefreshLayout = vView.findViewById(R.id.swipeRefreshPilots);

        mLayoutAnimation = new LayoutAnimations();

        mRecyclerViewList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewList.setLayoutManager(linearLayoutManager);
        vDriversAdapter = new DriverAdapter(mArrayListPilots, getContext());
        mRecyclerViewList.setAdapter(vDriversAdapter);


        if (savedInstanceState != null) {
            mArrayListPilots = (ArrayList<DriverStandings>) savedInstanceState.getSerializable(SAVE_LISTPILOTS);
            makeNewRecycleWiev();
            mProgressBar.setVisibility(View.INVISIBLE);

        } else {

            if (!ConnectionStatusHelper.statusConnection(getContext())) {
                Toast.makeText(getApplicationContext(),/* message*/  "NO connesione", Toast.LENGTH_SHORT).show();
            } else {

                getJsonObjectRequest("https://ergast.com/api/f1/current/driverStandings.json");
            }
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJsonObjectRequest("https://ergast.com/api/f1/current/driverStandings.json");
            }
        });
        return vView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVE_LISTPILOTS, mArrayListPilots);
    }


    private void getJsonObjectRequest(String url) {
        final JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    mArrayListPilots = DriversRankingHelper.getArrayListPilotsPoints(jsonObject);
                    makeNewRecycleWiev();
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressBar.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(mJsonObjectRequest);

    }

    private void makeNewRecycleWiev() {
        vDriversAdapter.updateData(mArrayListPilots);
        mLayoutAnimation.runLayoutAnimation(mRecyclerViewList);
    }


}
