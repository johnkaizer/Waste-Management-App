package com.galaxiescoders.wastetracker.my_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.authentication.SignInActivity;
import com.galaxiescoders.wastetracker.databinding.ActivityUsersDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;

public class UsersDashboard extends AppCompatActivity {

    private ActivityUsersDashboardBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUsersDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = preferences.edit();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.user_home, R.id.user_subscription, R.id.user_payments)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_user);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView2, navController);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_out) {
            editor.clear();
            editor.commit();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(UsersDashboard.this, SignInActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}