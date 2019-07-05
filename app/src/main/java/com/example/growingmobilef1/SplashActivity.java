package com.example.growingmobilef1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.growingmobilef1.Fragment_Activity.LoginActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

abstract class SplashActivity extends AppCompatActivity {

    private static final int ACTIVITY_AUTH = 1000;
    private static final String TAG = "SPLASH_TAG";

    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener  mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // firebase auth
        mFirebaseAuth = FirebaseAuth.getInstance();
/*
        if (!isAuthenticated()) {
            startActivityForResult(new Intent(this, LoginActivity.class), ACTIVITY_AUTH);
        }*/


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (!isAuthenticated()) {
                    startActivityForResult(new Intent(SplashActivity.this, LoginActivity.class), ACTIVITY_AUTH);
                }

            }
        };


        setTheme(R.style.AppTheme_Base);

        super.onCreate(savedInstanceState);
    }


    private Boolean isAuthenticated() {
        // check if logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            if(user.isEmailVerified()) {
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Email is not verified", Toast.LENGTH_SHORT).show();
                //user.reload();
            }
        }

        return false;
    }

    private void onAuthenticatedCallback(int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_CANCELED:
                finish();
                break;
            case Activity.RESULT_OK:
                recreate();
                break;
            default:
                return;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == ACTIVITY_AUTH) {
            onAuthenticatedCallback(resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mAuthStateListener!=null){
            // remove auth listener
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        // check auth
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    protected void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut(); // facebook logout, well done :P
    }
}