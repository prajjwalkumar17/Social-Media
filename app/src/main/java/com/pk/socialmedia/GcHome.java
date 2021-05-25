package com.pk.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.PipedInputStream;

public class GcHome extends AppCompatActivity {
    ImageView imageView2;
    TextView textView4, textView3;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gc_home);
        imageView2 = findViewById(R.id.imageView2);
        textView4 = findViewById(R.id.textView4);
        textView3 = findViewById(R.id.textView3);
        logOut = findViewById(R.id.Logoutbot);
        Intent intent = getIntent();
        String gcName = intent.getStringExtra("gcName");
        String gcId = intent.getStringExtra("gcId");
        String gcPic = intent.getStringExtra("gcPic");


        textView3.setText(gcName);
        textView4.setText(gcId);
        Picasso
                .get()
                .load(gcPic)
                .into(imageView2);


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
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