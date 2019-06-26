package com.example.growingmobilef1.Fragment_Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.example.growingmobilef1.Adapter.ConstructorsAdapter;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.ConstructorsDataHelper;
import com.example.growingmobilef1.Model.ConstructorStandings;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ApiAsyncCallerFragment extends Fragment {

    public interface IOnConstructorCalled {
        void onConstructorCalled(ArrayList<ConstructorStandings> aConstructorList);
    }
    private IOnConstructorCalled mConstructorListener;
    private ConstructorApiAsyncCaller mConstructorCaller;

    private Context mContext;

    public static ApiAsyncCallerFragment getInstance(){
        ApiAsyncCallerFragment vFragment = new ApiAsyncCallerFragment();
        Bundle vBundle = new Bundle();
        return vFragment;
    }

    public void startConstructorsCall(){
        if (mConstructorCaller == null){
            mConstructorCaller = new ConstructorApiAsyncCaller(mConstructorListener);
            mConstructorCaller.execute();
        }
    }

    public void stopConstructorsCall(){
        if (mConstructorCaller != null){
            mConstructorCaller.cancel(true);
            mConstructorCaller = null;
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IOnConstructorCalled){
            mConstructorListener = (IOnConstructorCalled)context;
            if (mConstructorCaller != null){
                mConstructorCaller.setListener(mConstructorListener);
            }
        }
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mConstructorListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mConstructorCaller.cancel(true);
    }

    private class ConstructorApiAsyncCaller extends AsyncTask<String, Void, String> {

        private JSONObject vJsonToParse;
        private ConstructorsDataHelper vConstructorsDataHelper;
        private ArrayList<ConstructorStandings> mConstructorsItemArraylist;

        private WeakReference<IOnConstructorCalled> mConstructorListener;
        public ConstructorApiAsyncCaller(IOnConstructorCalled aCaller) {
            mConstructorListener = new WeakReference<>(aCaller);
        }

        public void setListener(IOnConstructorCalled aCaller){
            mConstructorListener = new WeakReference<>(aCaller);
        }

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
            if (mConstructorListener.get() != null){
                mConstructorListener.get().onConstructorCalled(mConstructorsItemArraylist);
            }
           /* ((ConstructorsAdapter)mAdapter).updateData(mConstructorsItemArraylist);
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

            }*/
        }
    }
}
