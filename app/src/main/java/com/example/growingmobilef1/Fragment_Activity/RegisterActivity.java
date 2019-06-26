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


public class RegisterActivity extends AppCompatActivity {

    EditText mEditEmail,mEditPassword;
    Button mRegisterButton,mLoginButton;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser()!=null){
            Intent vIntent = new Intent(getApplicationContext(), MainActivity.class);
            vIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(vIntent);
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

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                }

                if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                }

                mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    startMainActivity();
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
                Intent vIntent = new Intent(getApplicationContext(), LoginActivity.class);
                vIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(vIntent);
                finish();
            }
        });

    }

    private void startMainActivity() {
        Intent vIntent = new Intent(getApplicationContext(), MainActivity.class);
        vIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(vIntent);
        finish();
        overridePendingTransition(R.anim.left_in_page, R.anim.left_out_page);
    }

}