package com.example.growingmobilef1.Fragment_Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.growingmobilef1.Model.Driver;
import com.example.growingmobilef1.R;

public class DriverDetailFragment extends Fragment {

private TextView txtName,txtSurname,txtDateOfBirth,txtPermanentNumber,txtNationality,txtOtherInformation;
    private static final String VAL_ID ="VAL_ID" ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View vView=inflater.inflate(R.layout.fragement_detail_driver,container,false);
       txtName=vView.findViewById(R.id.txtName);
       txtSurname=vView.findViewById(R.id.txtSurname);
        txtDateOfBirth=vView.findViewById(R.id.txtDateOfBirth);
        txtNationality=vView.findViewById(R.id.txtNationality);
        txtOtherInformation=vView.findViewById(R.id.txtOtherInfomation);
       Bundle vBundle=getArguments();
       if (vBundle !=null){
        Driver driver=(Driver) vBundle.getSerializable(VAL_ID);
           txtName.setText(driver.getFamilyName());
           txtSurname.setText(driver.getFamilyName());
           txtDateOfBirth.setText(driver.getDateOfBirth());
           txtNationality.setText(driver.getNationality());
           txtOtherInformation.setText(""+driver.getUrl());

       }




      return vView;
    }

    public static DriverDetailFragment newinstance(Driver driver){
        DriverDetailFragment vFragment=new DriverDetailFragment();
        Bundle vBundle=new Bundle();
        vBundle.putSerializable(VAL_ID,driver);
        vFragment.setArguments(vBundle);
        return vFragment;

    }

}
