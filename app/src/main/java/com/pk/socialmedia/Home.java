package com.pk.socialmedia;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {
    ImageView imageView2;
    TextView textView4, textView3;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mFirebaseUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageView2 = findViewById(R.id.imageView2);
        textView4 = findViewById(R.id.textView4);
        textView3 = findViewById(R.id.textView3);
        if (mFirebaseUser != null) {
            Intent intent = getIntent();
            String fbName = intent.getStringExtra("fbName");
            String fbId = intent.getStringExtra("fbId");
            textView3.setText(fbName);
            textView4.setText(fbId);
            Picasso
                    .get()
                    .load("https://graph.facebook.com/" + fbId + "/picture?type=large")
                    .into(imageView2);

        }
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
}