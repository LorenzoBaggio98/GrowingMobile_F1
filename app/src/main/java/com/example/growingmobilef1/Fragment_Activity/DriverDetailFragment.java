package com.example.growingmobilef1.Fragment_Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.growingmobilef1.Database.ModelRoom.RoomDriver;
import com.example.growingmobilef1.R;

import java.io.IOException;
import java.io.InputStream;

public class DriverDetailFragment extends Fragment {

    private static final String ERROR_TAG =  "ERROR_TAG";
    private TextView txtName, txtSurname, txtDateOfBirth, txtNationality, txtOtherInformation;
    private ImageView imgImageDriver;
    private static final String VAL_ID = "VAL_ID";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragement_detail_driver, container, false);

        txtName = vView.findViewById(R.id.txtName);
        txtSurname = vView.findViewById(R.id.txtSurname);
        txtDateOfBirth = vView.findViewById(R.id.txtDateOfBirth);
        txtNationality = vView.findViewById(R.id.txtNationality);
        txtOtherInformation = vView.findViewById(R.id.txtOtherInfomation);
        imgImageDriver=vView.findViewById(R.id.imageViewDrivers);


        Bundle vBundle = getArguments();
        if (vBundle != null) {
            RoomDriver driver = (RoomDriver) vBundle.getSerializable(VAL_ID);
            txtName.setText(driver.name);
            txtSurname.setText(driver.surname);
            txtDateOfBirth.setText(driver.dateOfBirth);
            txtNationality.setText(driver.nationality);

            txtOtherInformation.setText("" + driver.url);
            txtOtherInformation.setLinksClickable(true);
            setImageDriver(driver.driverId);
        }


        return vView;
    }

    public static DriverDetailFragment newinstance(RoomDriver driver) {
        DriverDetailFragment vFragment = new DriverDetailFragment();
        Bundle vBundle = new Bundle();
        vBundle.putSerializable(VAL_ID, driver);
        vFragment.setArguments(vBundle);
        return vFragment;

    }
    private void setImageDriver(String driverid){
        try {


            // get input stream
            InputStream ims = getContext().getAssets().open("drivers/" + driverid + ".jpg");

            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imgImageDriver.setImageDrawable(d);
            ims.close();

        } catch (IOException ex) {
            Log.e(ERROR_TAG, "Error on drivers image reading");
        }
    }

}
