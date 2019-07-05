package com.example.growingmobilef1.Fragment_Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.growingmobilef1.Helper.IGenericHelper;
import com.example.growingmobilef1.Model.IListableModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ApiAsyncCallerFragment2 extends Fragment {
private Prova prova;

    private IOnApiCalled mElementListener;


    public interface IOnApiCalled {
        void onApiCalled(ArrayList<IListableModel> aReturnList);
    }






    public static ApiAsyncCallerFragment2 getInstance(){
        ApiAsyncCallerFragment2 vFragment = new ApiAsyncCallerFragment2();
        return vFragment;


    }
    private static class Prova{
        private   JsonObjectRequest mJsonObjectRequest;
        String url;
        IGenericHelper mApiGenericHelper;
        WeakReference<ApiAsyncCallerFragment2> mFragmentWeak;
        private ArrayList<IListableModel> mHelperArrayList;


        public Prova(ApiAsyncCallerFragment2 aListener) {
            mFragmentWeak=new WeakReference<>(aListener);
        }

        public void startCall(String aUrl, IGenericHelper aApiGenericHelper) {

            this.url=aUrl;
            this.mApiGenericHelper=aApiGenericHelper;
            mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response));

                        mHelperArrayList =  mApiGenericHelper.getArrayList(jsonObject);

            mFragmentWeak.get().mElementListener.onApiCalled(mHelperArrayList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            if (mJsonObjectRequest != null){
                Volley.newRequestQueue(mFragmentWeak.get().getParentFragment().getContext()).add(mJsonObjectRequest);
            }

        }

    }
    public void startCall(String aUrl, IGenericHelper aApiGenericHelper){
        if (prova == null){
            prova = new Prova(this);
            prova.startCall(aUrl,aApiGenericHelper);
        }
    }

    public void stopCall(){
        if (prova !=null){
            if (prova.mJsonObjectRequest != null){
                prova.mJsonObjectRequest.cancel();
                prova.mJsonObjectRequest = null;
            }
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof IOnApiCalled){
            mElementListener=(IOnApiCalled)getActivity();
        }

    //    if (getParentFragment() instanceof IOnApiCalled){
       //     mElementListener = (IOnApiCalled) getParentFragment();
//            if (mElementCaller != null){
//              mElementCaller.setListener(mElementListener);
//            }
    //    }
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
     *  private void getJsonObjectRequest(String url) {
     *         final JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
     *             @Override
     *             public void onResponse(JSONObject response) {
     *
     *                 try {
     *                     JSONObject jsonObject = new JSONObject(String.valueOf(response));
     *                     mArrayListPilots = DriversRankingHelper.getArrayListPilotsPoints(jsonObject);
     *
     *                 } catch (JSONException e) {
     *                     e.printStackTrace();
     *                 }
     *             }
     *
     *             }, new Response.ErrorListener() {
     *             @Override
     *             public void onErrorResponse(VolleyError error) {
     *
     *             }
     *         });
     *         Volley.newRequestQueue(getApplicationContext()).add(mJsonObjectRequest);
     *
     *     }
     *
     */
//    private static class ApiAsyncCaller extends AsyncTask<String, Void, String> {
//
//        private JSONObject vJsonToParse;
//        private ArrayList<IListableModel> mHelperArrayList;
//        private IGenericHelper mApiGenericHelper;
//
//        private WeakReference<IOnApiCalled> mListener;
//
//        public ApiAsyncCaller(IOnApiCalled aCaller, IGenericHelper aApiGenericHelper) {
//            mListener = new WeakReference<>(aCaller);
//            mApiGenericHelper = aApiGenericHelper;
//        }
//
//        public void setListener(IOnApiCalled aCaller){
//            mListener = new WeakReference<>(aCaller);
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            getJsonObjectRequest(params[0]);
//
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (mListener.get() != null){
//                mListener.get().onApiCalled(mHelperArrayList);
//            }
//
//
//
//
//
//        }
        private void getJsonObjectRequest(String url) {

            final JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response));

                      //  mHelperArrayList =  mApiGenericHelper.getArrayList(jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            Volley.newRequestQueue(getInstance().getParentFragment().getActivity()).add(mJsonObjectRequest);


        }

    }

