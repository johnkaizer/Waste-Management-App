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
import com.galaxiescoders.wastetracker.databinding.FragmentStaffToolsBinding;
import com.galaxiescoders.wastetracker.databinding.FragmentStaffZoneBinding;
import com.galaxiescoders.wastetracker.models.Vehicle;
import com.galaxiescoders.wastetracker.models.Zone;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StaffZoneFragment extends Fragment {
    private FragmentStaffZoneBinding binding;
    StaffZoneAdapter staffZoneAdapter;
    private ArrayList<Zone> zoneList;
    private DatabaseReference databaseReference;
    private RecyclerView zonesRec;
    private String staffId; // Variable to store the staff id

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStaffZoneBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        zonesRec = root.findViewById(R.id.staffZoneRV);
        zonesRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        zoneList = new ArrayList<>();

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Zones");

        // Retrieve staff id from SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        staffId = preferences.getString("idNo", "");

        // Query to filter zones based on staff ID
        Query query = databaseReference.orderByChild("staffId").equalTo(staffId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                zoneList.clear(); // Clear existing data

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Zone zone = snapshot.getValue(Zone.class);
                    if (zone != null) {
                        zoneList.add(zone);
                    }
                }

                // Update your RecyclerView adapter with the filtered zones
                staffZoneAdapter = new StaffZoneAdapter(zoneList);
                zonesRec.setAdapter(staffZoneAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(getActivity(), "Error retrieving zones: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
