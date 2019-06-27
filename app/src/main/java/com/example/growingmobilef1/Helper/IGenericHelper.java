package com.example.growingmobilef1.Helper;

import com.example.growingmobilef1.Model.IListableModel;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public interface IGenericHelper extends Serializable {

    ArrayList<IListableModel> getArrayList(JSONObject aJsonToParse);
}
