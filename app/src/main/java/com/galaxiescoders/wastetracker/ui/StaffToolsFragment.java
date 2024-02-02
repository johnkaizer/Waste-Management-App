package com.galaxiescoders.wastetracker.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.adapters.StaffToolAdapter;
import com.galaxiescoders.wastetracker.databinding.FragmentAdminToolsBinding;
import com.galaxiescoders.wastetracker.databinding.FragmentStaffToolsBinding;
import com.galaxiescoders.wastetracker.models.Vehicle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class StaffToolsFragment extends Fragment {
    private FragmentStaffToolsBinding binding;
    StaffToolAdapter staffToolAdapter;
    private ArrayList<Vehicle> vehiclesList;
    private DatabaseReference databaseReference;
    private RecyclerView vehiclesRec;
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


        return root;
    }
}