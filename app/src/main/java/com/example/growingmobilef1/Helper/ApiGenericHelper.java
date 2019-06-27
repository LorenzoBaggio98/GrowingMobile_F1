package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Model.IListableModel;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ApiGenericHelper<T extends IGenericHelper> implements Serializable {

    private T apiHelper;

    public ArrayList<IListableModel> getModelArrayList(JSONObject aJsonToParse) {
        ArrayList<IListableModel> vArrayList = apiHelper.getArrayList(aJsonToParse);
        return vArrayList;
    }
}