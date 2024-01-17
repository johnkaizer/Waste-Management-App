package com.galaxiescoders.wastetracker.my_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.authentication.SignInActivity;
import com.galaxiescoders.wastetracker.authentication.SignUpActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Using a Handler to delay the opening of SignInActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start SignInActivity after 3 seconds
                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // 3000 milliseconds (3 seconds)
    }
}