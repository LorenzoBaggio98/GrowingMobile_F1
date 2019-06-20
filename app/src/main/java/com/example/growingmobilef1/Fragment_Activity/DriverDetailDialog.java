package com.example.growingmobilef1.Fragment_Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.growingmobilef1.Model.Driver;
import com.example.growingmobilef1.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class DriverDetailDialog extends DialogFragment {

    private static final String DRIVER = "Driver to show";

    private ImageView mImage;
    private TextView mName;
    private TextView mBirthday;
    private TextView mNationality;

    private Driver mDriver;

    public static DriverDetailDialog getInstance(Serializable aDriver){
        DriverDetailDialog vDialog = new DriverDetailDialog();
        Bundle vBundle = new Bundle();
        vBundle.putSerializable(DRIVER, aDriver);
        vDialog.setArguments(vBundle);
        return vDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder vBuilder = new AlertDialog.Builder(getContext(), R.style.TransparentBackgroundDialog);
        View vView = getActivity().getLayoutInflater().inflate(R.layout.dialog_pilot_detail, null);
        mImage = vView.findViewById(R.id.pilot_dialog_image);
        mName = vView.findViewById(R.id.pilot_dialog_name);
        mBirthday = vView.findViewById(R.id.pilot_dialog_birthdate);
        mNationality = vView.findViewById(R.id.pilot_dialog_nationality);

        vBuilder.setView(vView);

        Bundle vStartingBundle = getArguments();
        if (vStartingBundle != null)
            mDriver = (Driver) vStartingBundle.getSerializable(DRIVER);

        mName.setText(mDriver.getGivenName() + " " + mDriver.getFamilyName());
        mBirthday.setText(mDriver.getDateOfBirth());
        mNationality.setText(mDriver.getNationality());
        try {
            // get input stream
            InputStream ims = getContext().getAssets().open("drivers/" + mDriver.getDriverId() + ".jpg");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mImage.setImageDrawable(d);
            ims.close();

        } catch (IOException ex) {
            Log.e("ERROR", "Error on drivers image reading");
        }

        return vBuilder.create();
    }


}
