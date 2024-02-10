package com.galaxiescoders.wastetracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.galaxiescoders.wastetracker.adapters.AdminToolsAdapter;
import com.galaxiescoders.wastetracker.adapters.ApplicationAdapter;
import com.galaxiescoders.wastetracker.databinding.FragmentAdminApplicationsBinding;
import com.galaxiescoders.wastetracker.databinding.FragmentAdminToolsBinding;
import com.galaxiescoders.wastetracker.models.ApplicationModel;
import com.galaxiescoders.wastetracker.models.Vehicle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminApplicationsFragment extends Fragment {
    private FragmentAdminApplicationsBinding binding;

    private ArrayList<ApplicationModel> applications;
    private DatabaseReference databaseReference;
    private RecyclerView applicationRec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminApplicationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        applicationRec = root.findViewById(R.id.appRv);
        applicationRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        applications = new ArrayList<>();

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Applications");

        fetchApplications();
        return root;
    }

    private void fetchApplications() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                applications.clear(); // Clear existing data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Parse application data from Firebase
                    ApplicationModel applicat = dataSnapshot.getValue(ApplicationModel.class);

                    if (applicat != null) {
                        applications.add(applicat); // Add application to the list
                    }
                }

                // Create and set the adapter for the RecyclerView
                ApplicationAdapter applicationAdapter = new ApplicationAdapter(applications, getContext());
                applicationRec.setAdapter(applicationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
                Toast.makeText(getContext(), "Failed to fetch application from Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}