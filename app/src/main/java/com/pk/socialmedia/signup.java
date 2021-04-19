package com.pk.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    Button gsign, fbsign;
    FirebaseAuth mAuth;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        gsign = findViewById(R.id.gsign);
        fbsign = findViewById(R.id.fbsign);
        mAuth = FirebaseAuth.getInstance();
        gsignin();
        gsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();

            }
        });
        fbsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbsignin();
            }
        });


    }

    private void fbsignin() {


    }
    private void gsignin() {}

    private void signIn() {

    }




}