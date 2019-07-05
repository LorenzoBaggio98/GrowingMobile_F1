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

public class ApiAsyncCallerFragment3 extends Fragment {
private Prova prova;

    private IOnApiCalled mElementListener;


    public interface IOnApiCalled {
        void onApiCalled(ArrayList<IListableModel> aReturnList);
    }






    public static ApiAsyncCallerFragment3 getInstance(){
        ApiAsyncCallerFragment3 vFragment = new ApiAsyncCallerFragment3();
        return vFragment;


    }
    private class Prova{
        private   JsonObjectRequest mJsonObjectRequest;
        String url;
        IGenericHelper mApiGenericHelper;
        WeakReference<ApiAsyncCallerFragment3> mFragmentWeak;
       private ArrayList<IListableModel> mHelperArrayList;
        private WeakReference<IOnApiCalled> mListener;


        public Prova(IOnApiCalled aListener) {
            mListener=new WeakReference<>(aListener);
        }
        public void setListener(IOnApiCalled aCaller){
            mListener = new WeakReference<>(aCaller);
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

            mListener.get().onApiCalled(mHelperArrayList);

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
                Volley.newRequestQueue(getContext()).add(mJsonObjectRequest);
            }

        }

    }
    public void startCall(String aUrl, IGenericHelper aApiGenericHelper){
        if (prova == null){
            prova = new Prova(mElementListener);
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
//        if (getActivity() instanceof IOnApiCalled){
//            mElementListener=(IOnApiCalled)getActivity();
//        }

        if (getParentFragment() instanceof IOnApiCalled){
            mElementListener = (IOnApiCalled) getParentFragment();
            if (prova != null){
              prova.setListener(mElementListener);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mElementListener = null;
    }


    }

