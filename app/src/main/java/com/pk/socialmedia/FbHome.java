package com.pk.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class FbHome extends AppCompatActivity {
    ImageView imageView2;
    TextView textView4, textView3;
    Button Logoutbot;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mFirebaseUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView2 = findViewById(R.id.imageView2);
        textView4 = findViewById(R.id.textView4);
        textView3 = findViewById(R.id.textView3);
        Logoutbot = findViewById(R.id.Logoutbot);
        Intent intent = getIntent();
        String fbName = intent.getStringExtra("fbName");
        String fbId = intent.getStringExtra("fbId");
        if (mFirebaseUser != null) {
            textView3.setText(fbName);
            textView4.setText(fbId);
            Picasso
                    .get()
                    .load("https://graph.facebook.com/" + fbId + "/picture?type=large")
                    .into(imageView2);
        }

        Logoutbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutFromFacebook();
            }
        });


    }

    public void logoutFromFacebook() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(getApplicationContext(), signup.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseUser == null) {
            openProfile();
        }
    }

    private void openProfile() {
        startActivity(new Intent(this, signup.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), signup.class));
        finish();
    }

}