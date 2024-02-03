package com.galaxiescoders.wastetracker.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.adapters.StaffToolAdapter;
import com.galaxiescoders.wastetracker.adapters.StaffZoneAdapter;
import com.galaxiescoders.wastetracker.databinding.FragmentAdminToolsBinding;
import com.galaxiescoders.wastetracker.databinding.FragmentStaffToolsBinding;
import com.galaxiescoders.wastetracker.models.Vehicle;
import com.galaxiescoders.wastetracker.models.Zone;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StaffToolsFragment extends Fragment {
    private FragmentStaffToolsBinding binding;
    StaffToolAdapter staffToolAdapter;
    private ArrayList<Vehicle> vehiclesList;
    private DatabaseReference databaseReference;
    private RecyclerView vehiclesRec;
    private String staffId; // Variable to store the staff id
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStaffToolsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vehiclesRec = root.findViewById(R.id.staffToolsRV);
        vehiclesRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        vehiclesList = new ArrayList<>();

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Tools");

        // Retrieve staff id from SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        staffId = preferences.getString("idNo", "");

        // Query to filter vehicles based on staff ID
        Query query = databaseReference.orderByChild("staffId").equalTo(staffId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vehiclesList.clear(); // Clear existing data

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Vehicle vehicle  = snapshot.getValue(Vehicle.class);
                    if (vehicle != null) {
                        vehiclesList.add(vehicle);
                    }
                }

                // Update your RecyclerView adapter with the filtered vehicles
                staffToolAdapter = new StaffToolAdapter(vehiclesList);
                vehiclesRec.setAdapter(staffToolAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(getActivity(), "Error retrieving vehicles/tools: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}