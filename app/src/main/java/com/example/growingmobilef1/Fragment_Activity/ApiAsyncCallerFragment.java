
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
import java.util.ArrayList;

public class ApiAsyncCallerFragment extends Fragment {

    private ArrayList<IListableModel> mHelperArrayList;
    private JsonObjectRequest mJsonObjectRequest;
    String url;
    IGenericHelper mApiGenericHelper;

    public interface IOnApiCalled {
        void onApiCalled(ArrayList<IListableModel> aReturnList);
    }

    IOnApiCalled mElementListener;

    public static ApiAsyncCallerFragment getInstance() {
        ApiAsyncCallerFragment vFragment = new ApiAsyncCallerFragment();
        return vFragment;
    }

    public void startCall(String aUrl, IGenericHelper aApiGenericHelper) {

        this.url = aUrl;
        this.mApiGenericHelper = aApiGenericHelper;
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    mHelperArrayList = mApiGenericHelper.getArrayList(jsonObject);
                    mElementListener.onApiCalled(mHelperArrayList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        if (mJsonObjectRequest != null) {
            Volley.newRequestQueue(getContext()).add(mJsonObjectRequest);
        }

    }


    public void stopCall() {
        if (mJsonObjectRequest != null) {
            mJsonObjectRequest.cancel();
            mJsonObjectRequest = null;
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

        if (getParentFragment() instanceof IOnApiCalled) {
            mElementListener = (IOnApiCalled) getParentFragment();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mElementListener = null;
    }
}