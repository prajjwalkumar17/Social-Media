package com.pk.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    ImageView imageView2;
    TextView textView4, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageView2 = findViewById(R.id.imageView2);
        textView4 = findViewById(R.id.textView4);
        textView3 = findViewById(R.id.textView3);
    }
}