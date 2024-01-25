package com.galaxiescoders.wastetracker.my_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.databinding.ActivityAdminDashboardBinding;
import com.galaxiescoders.wastetracker.databinding.ActivityStewardsDashboardBinding;
import com.galaxiescoders.wastetracker.databinding.ActivityUsersDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UsersDashboard extends AppCompatActivity {

    private ActivityUsersDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUsersDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_users, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_user);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}