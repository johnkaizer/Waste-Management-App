package com.galaxiescoders.wastetracker.my_activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.galaxiescoders.wastetracker.AdminApplicationsFragment;
import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.authentication.SignInActivity;
import com.galaxiescoders.wastetracker.databinding.ActivityAdminDashboardBinding;
import com.galaxiescoders.wastetracker.ui.AdminComplaintsFragment;
import com.galaxiescoders.wastetracker.ui.UserComplaintFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class ActivityAdminDashboard extends AppCompatActivity {

    private ActivityAdminDashboardBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = preferences.edit();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard,  R.id.navigation_tools, R.id.admin_zones ,R.id.admin_applications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_out) {
            editor.clear();
            editor.commit();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ActivityAdminDashboard.this, SignInActivity.class));
            finish();
            return true;
        } else if (id == R.id.action_complaint) {
            // Open ComplaintFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, new AdminComplaintsFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}