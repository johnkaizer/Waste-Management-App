package com.galaxiescoders.wastetracker.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.databinding.ActivitySignInBinding;
import com.galaxiescoders.wastetracker.databinding.ActivityStewardsDashboardBinding;
import com.galaxiescoders.wastetracker.my_activities.SplashActivity;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}