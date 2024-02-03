package com.galaxiescoders.wastetracker.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.databinding.ActivitySignInBinding;
import com.galaxiescoders.wastetracker.databinding.ActivityStewardsDashboardBinding;
import com.galaxiescoders.wastetracker.my_activities.ActivityAdminDashboard;
import com.galaxiescoders.wastetracker.my_activities.SplashActivity;
import com.galaxiescoders.wastetracker.my_activities.StewardsDashboard;
import com.galaxiescoders.wastetracker.my_activities.UsersDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private EditText EditTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = findViewById(R.id.progressBar);
        EditTextEmail = findViewById(R.id.user_txt1);
        editTextPassword = findViewById(R.id.pass_txt1);

        mAuth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = preferences.edit();

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = EditTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    EditTextEmail.setError("Email is required!!");
                    EditTextEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    EditTextEmail.setError("Please provide a valid email address!");
                    EditTextEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    editTextPassword.setError("Password is required!!");
                    editTextPassword.requestFocus();
                    return;
                }
                if (!isInternetConnected()) {
                    Toast.makeText(SignInActivity.this, "No internet connection available", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Authenticate the user using Firebase Authentication
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    // Authentication successful
                                    retrieveUserRoleAndNavigate();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    private void retrieveUserRoleAndNavigate() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
            userRef.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Get user details
                        String userRole = dataSnapshot.child("role").getValue(String.class);
                        String idNo = dataSnapshot.child("idNo").getValue(String.class);

                        // Store user role and idNo in SharedPreferences
                        editor.putString("userRole", userRole);
                        editor.putString("idNo", idNo);
                        editor.apply();

                        // Navigate to the respective dashboard based on the user's role
                        if ("Admin".equals(userRole)) {
                            startActivity(new Intent(SignInActivity.this, ActivityAdminDashboard.class));
                        } else if ("Resident".equals(userRole)) {
                            startActivity(new Intent(SignInActivity.this, UsersDashboard.class));
                        } else if ("Steward".equals(userRole)) {
                            startActivity(new Intent(SignInActivity.this, StewardsDashboard.class));
                        }

                        finish();
                    } else {
                        // User not found in the database, handle accordingly
                        Toast.makeText(SignInActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                    Toast.makeText(SignInActivity.this, "Error retrieving user details.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    public boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
