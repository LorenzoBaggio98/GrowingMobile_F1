package com.example.growingmobilef1.Fragment_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.growingmobilef1.MainActivity;
import com.example.growingmobilef1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "REGISTER_TAG";

    EditText mEditEmail,mEditPassword;
    Button mRegisterButton,mLoginButton;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();

        // check user status
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if(user != null){
            if(user.isEmailVerified()) {
                startMainActivity();
            }
        }

        setContentView(R.layout.activity_registration);

        mEditEmail = findViewById(R.id.editText_email);
        mEditPassword = findViewById(R.id.editText_password);
        mRegisterButton = findViewById(R.id.btn_signup);
        mLoginButton = findViewById(R.id.btn_login);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEditEmail.getText().toString();
                String password = mEditPassword.getText().toString();

                if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEditEmail.setError("enter a valid email address");
                }

                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    mEditEmail.setError("password less than 6");
                    return;
                }

                mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //Intent vIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                    //startActivity(vIntent);
                                    sendVerificationEmail();
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"E-mail or password is wrong",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent vIntent = new Intent(getApplicationContext(), LoginActivity.class);
                vIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(vIntent);*/
                finish();
            }
        });

    }

    private void sendVerificationEmail() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            // after email is sent, logout the user and finish this activity
                            //FirebaseAuth.getInstance().signOut();
                            Toast.makeText(getApplicationContext(),"verification e-mail sent",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity
                            Toast.makeText(getApplicationContext(),"E-mail not sent, retry",Toast.LENGTH_LONG).show();
                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }

    private void startMainActivity() {
        Intent vIntent = new Intent(getApplicationContext(), MainActivity.class);
        vIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(vIntent);
        overridePendingTransition(R.anim.left_in_page, R.anim.left_out_page);
        finish();
    }

}