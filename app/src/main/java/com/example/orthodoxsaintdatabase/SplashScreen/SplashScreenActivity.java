package com.example.orthodoxsaintdatabase.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orthodoxsaintdatabase.Activity.MainActivity;
import com.example.orthodoxsaintdatabase.R;

public class SplashScreenActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
            }
        },2000);
    }
}
