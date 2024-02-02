package com.galaxiescoders.wastetracker.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.databinding.FragmentHomeBinding;
import com.galaxiescoders.wastetracker.databinding.FragmentStaffHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StaffHomeFragment extends Fragment {
    private FragmentStaffHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStaffHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get the current hour
        Calendar calendar1 = Calendar.getInstance();
        int hourOfDay = calendar1.get(Calendar.HOUR_OF_DAY);
        // Get the current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM, yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar1.getTime());

        // Set the appropriate greeting based on the time of day
        String greeting;
        if (hourOfDay >= 0 && hourOfDay < 12) {
            greeting = "Good morning";
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
            greeting = "Good afternoon";
        } else {
            greeting = "Good evening";
        }

        // Display the greeting in the TextView
        binding.greetingsText.setText(greeting);
        // Set the formatted date in the TextView
        binding.dateTextView.setText(formattedDate);


        return root;
    }
}