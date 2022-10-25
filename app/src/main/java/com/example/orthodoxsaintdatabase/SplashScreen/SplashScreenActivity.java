package com.example.orthodoxsaintdatabase.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orthodoxsaintdatabase.Activity.MainActivity;
import com.example.orthodoxsaintdatabase.R;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView logo;
    String ID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        logo = findViewById(R.id.image);
        rotateImage();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
           startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
            }
        },2000);
    }

    private void rotateImage(){
        Animation rotateAnimation = new RotateAnimation(.0f, 360,
                Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
                .5f);
        rotateAnimation.setDuration(2000);
        logo.startAnimation(rotateAnimation);
    }
}
