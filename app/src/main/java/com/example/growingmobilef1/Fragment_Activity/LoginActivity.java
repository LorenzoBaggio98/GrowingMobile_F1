package com.example.growingmobilef1.Fragment_Activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.growingmobilef1.MainActivity;
import com.example.growingmobilef1.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    // TAGs
    private static final String TAG = "TAG_LOGIN";

    // firebase auth
    private static final int RC_SIGN_IN = 9001;
    FirebaseAuth mFirebaseAuth;
    CallbackManager mCallbackManager;
    GoogleSignInClient mGoogleApiClient;

    // widgets
    EditText mEmailField,mPasswordField;
    TextView mTextOr;
    boolean visible = false;
    Button googleLoginBtn;
    Button newPassButton;
    Button facebookLoginBtn;
    Dialog progressDialog;
    ImageView imageViewLogo;

    // layouts
    LinearLayout bootmView, topView;

/*
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //moveLogo(imageViewLogo);
            move(topView);
            move(bootmView);
            move(editsView);
        }
    };
*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // START FIREBASE INITIALIZATION
        // instance firebase
        mFirebaseAuth = FirebaseAuth.getInstance();

        // check if logged in
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        if(user != null){
            if(user.isEmailVerified()) {
                startMainActivity();
                //performCallBack();
            } else {
                Toast.makeText(getApplicationContext(),"Email is not verified",Toast.LENGTH_SHORT).show();
            }
        }
        // END FIREBASE INITIALIZATION

        // connects widgets
        mEmailField = (EditText) findViewById(R.id.editText_email);
        mPasswordField = (EditText) findViewById(R.id.editText_password);
        Button loginButton = findViewById(R.id.btn_login);
        TextView registerButton = findViewById(R.id.btn_register);
        imageViewLogo = findViewById(R.id.imgView_logo);

        bootmView = (LinearLayout) findViewById(R.id.bottomLayout);
        topView = (LinearLayout) findViewById(R.id.topLayout);
        //editsView = (LinearLayout) findViewById(R.id.editsLayout);

        mTextOr = bootmView.findViewById(R.id.textOr);
        googleLoginBtn = bootmView.findViewById(R.id.btn_signin_google);
        newPassButton = bootmView.findViewById(R.id.btn_pw_forgotten);
        facebookLoginBtn = bootmView.findViewById(R.id.btn_signin_facebook);

        setDialog();

        // EMAIL AND PASSWORD LOGIN
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });


        // set google sign-in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
/*
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        */
        mGoogleApiClient = GoogleSignIn.getClient(this, gso);

        // GOOGLE LOGIN
        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });


        // setting facebook login
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                Toast.makeText(getApplicationContext(),"Facebook auth error",Toast.LENGTH_LONG).show();
            }
        });

        // FACEBOOK LOGIN
        facebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends"));
            }
        });

        // GO TO REGISTRATION PAGE
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterActivity();
            }
        });

        // CHANGE PASSWORD
        newPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEmail()) {
                    sendPasswordReset(mEmailField.getText().toString());
                }
            }
        });


        //handler.postDelayed(runnable, 2000);

    }


    private void goToRegisterActivity() {
        Intent vIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        //vIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(vIntent);
    }

    private void googleSignIn() {
        Intent signIntent = mGoogleApiClient.getSignInIntent();
        startActivityForResult(signIntent,RC_SIGN_IN);
    }

    private boolean validateForm() {
        boolean valid = true;

        if(!validateEmail()) {
            valid = false;
        }

        if(!validatePassword()) {
            valid = false;
        }

        return valid;
    }

    private boolean validateEmail() {
        String email = mEmailField.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailField.setError("enter a valid email address");

            return false;

        } else {
            mEmailField.setError(null);

            return true;
        }
    }

    private boolean validatePassword() {
        String password = mPasswordField.getText().toString();

        if (password.isEmpty() || password.length() < 6) {
            mPasswordField.setError("password less than 6");

            return false;

        } else {
            mPasswordField.setError(null);

            return true;
        }
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        // show progress dialog
        hideShowProgressDialg(true);

        // sign_in_with_email
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        hideShowProgressDialg(false);

                        if (!task.isSuccessful()) {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Is the email or password correct?",Toast.LENGTH_LONG).show();
                        } else  {

                            checkIfEmailVerified();
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // pass back to activityResult
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
/*
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            boolean flag = result.isSuccess();

            if(flag){
                GoogleSignInAccount account = result.getSignInAccount();
                authWithGoogle(account);
            }
        }*/
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                authWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                Toast.makeText(getApplicationContext(), "Google auth error", Toast.LENGTH_LONG).show();
                // [END_EXCLUDE]
            }
        }
    }

    public void sendPasswordReset(String vEmail) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(vEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(getApplicationContext(), "Check your email", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void authWithGoogle(GoogleSignInAccount account) {
        hideShowProgressDialg(true);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideShowProgressDialg(false);
                if(task.isSuccessful()){
                    // start main
                    //startMainActivity();
                    Log.d(TAG, "signInWithgooglel:success");
                    Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_LONG).show();
                    performCallBack();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Auth Error",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        hideShowProgressDialg(true);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        hideShowProgressDialg(false);

                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_LONG).show();
                            /*startMainActivity();*/
                            performCallBack();

                        }
                        else {
                            // If sign in fails, display a message
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // TODO - dialog or something else
    }

    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified()) {

            Log.d(TAG, "signInWithEmail:success");
            Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_LONG).show();
            performCallBack();

        }
        else {
            Toast.makeText(getApplicationContext(), "Email in not verified", Toast.LENGTH_LONG).show();
            Log.d(TAG, "signInWithEmail:unsuccess");
            FirebaseAuth.getInstance().signOut();
/*
            Intent vIntent = new Intent(LoginActivity.this, LoginActivity.class);
            vIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(vIntent);*/

        }
    }


    private void startMainActivity() {
        Intent vIntent = new Intent(getApplicationContext(), MainActivity.class);
        vIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(vIntent);
        overridePendingTransition(R.anim.left_in_page, R.anim.left_out_page);
        finish();
    }


    private void setDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setView(R.layout.progress);
        progressDialog = builder.create();

    }

    private void hideShowProgressDialg(boolean show) {
        if (show) {
            progressDialog.show();
        }
        else {
            progressDialog.dismiss();
        }
    }

    /*
    public static void move(final LinearLayout view){
        ValueAnimator va = ValueAnimator.ofInt(0, 0);
        va.setDuration(400);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                view.getLayoutParams().height = value.intValue();
                view.requestLayout();
            }
        });
        va.start();
    }*/

    private void performCallBack() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }



}