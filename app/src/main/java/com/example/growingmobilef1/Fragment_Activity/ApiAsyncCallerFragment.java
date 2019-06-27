package com.example.growingmobilef1.Fragment_Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.growingmobilef1.Helper.ApiGenericHelper;
import com.example.growingmobilef1.Helper.ApiRequestHelper;
import com.example.growingmobilef1.Helper.ConstructorsDataHelper;
import com.example.growingmobilef1.Helper.IGenericHelper;
import com.example.growingmobilef1.Model.IListableModel;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ApiAsyncCallerFragment extends Fragment {

    public interface IOnApiCalled {
        void onApiCalled(ArrayList<IListableModel> aConstructorList);
    }

    private IOnApiCalled mConstructorListener;
    private ConstructorApiAsyncCaller mConstructorCaller;
    private IGenericHelper mApiGenericHelper;

    public static ApiAsyncCallerFragment getInstance(Serializable aSerializableClass){
        ApiAsyncCallerFragment vFragment = new ApiAsyncCallerFragment();
        return vFragment;
    }

    public void startApiCall(String aUrl, IGenericHelper aApiGenericHelper){
        if (mConstructorCaller == null){
            mConstructorCaller = new ConstructorApiAsyncCaller(mConstructorListener, aApiGenericHelper);
            mConstructorCaller.execute(aUrl);
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
        if (getParentFragment() instanceof IOnApiCalled){
            mConstructorListener = (IOnApiCalled) getParentFragment();
            if (mConstructorCaller != null){
                mConstructorCaller.setListener(mConstructorListener);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mConstructorListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class ConstructorApiAsyncCaller extends AsyncTask<String, Void, String> {

        private JSONObject vJsonToParse;
        private ArrayList<IListableModel> mHelperArrayList;
        private IGenericHelper mApiGenericHelper;

        private WeakReference<IOnApiCalled> mConstructorListener;
        public ConstructorApiAsyncCaller(IOnApiCalled aCaller, IGenericHelper aApiGenericHelper) {
            mConstructorListener = new WeakReference<>(aCaller);
            mApiGenericHelper = aApiGenericHelper;
        }

        public void setListener(IOnApiCalled aCaller){
            mConstructorListener = new WeakReference<>(aCaller);
        }

        @Override
        protected String doInBackground(String... params) {
            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();

            // get json from api
            vJsonToParse = vApiRequestHelper.getContentFromUrl(params[0]);

            // parse json to list
            try {
                mHelperArrayList =  mApiGenericHelper.getArrayList(vJsonToParse);
            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (mConstructorListener.get() != null){
                mConstructorListener.get().onApiCalled(mHelperArrayList);
            }
        }
    }
}
