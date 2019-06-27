package com.example.growingmobilef1.Fragment_Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.growingmobilef1.Helper.ApiRequestHelper;
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

    private IOnApiCalled mElementListener;
    private ApiAsyncCaller mElementCaller;

    public static ApiAsyncCallerFragment getInstance(Serializable aSerializableClass){
        ApiAsyncCallerFragment vFragment = new ApiAsyncCallerFragment();
        return vFragment;
    }

    public void startCall(String aUrl, IGenericHelper aApiGenericHelper){
        if (mElementCaller == null){
            mElementCaller = new ApiAsyncCaller(mElementListener, aApiGenericHelper);
            mElementCaller.execute(aUrl);
        }
    }

    public void stopCall(){
        if (mElementCaller != null){
            mElementCaller.cancel(true);
            mElementCaller = null;
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
            mElementListener = (IOnApiCalled) getParentFragment();
            if (mElementCaller != null){
                mElementCaller.setListener(mElementListener);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mElementListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     *
     */
    private class ApiAsyncCaller extends AsyncTask<String, Void, String> {

        private JSONObject vJsonToParse;
        private ArrayList<IListableModel> mHelperArrayList;
        private IGenericHelper mApiGenericHelper;

        private WeakReference<IOnApiCalled> mListener;

        public ApiAsyncCaller(IOnApiCalled aCaller, IGenericHelper aApiGenericHelper) {
            mListener = new WeakReference<>(aCaller);
            mApiGenericHelper = aApiGenericHelper;
        }

        public void setListener(IOnApiCalled aCaller){
            mListener = new WeakReference<>(aCaller);
        }

        @Override
        protected String doInBackground(String... params) {
            ApiRequestHelper vApiRequestHelper = new ApiRequestHelper();

            // get json from api
            vJsonToParse = vApiRequestHelper.getContentFromUrl(params[0]);

            // parse json to list
            mHelperArrayList =  mApiGenericHelper.getArrayList(vJsonToParse);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (mListener.get() != null){
                mListener.get().onApiCalled(mHelperArrayList);
            }
        }
    }
}
