package com.galaxiescoders.wastetracker.ui;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.adapters.AdminToolsAdapter;
import com.galaxiescoders.wastetracker.adapters.AdminZonesAdapter;
import com.galaxiescoders.wastetracker.databinding.FragmentAdminToolsBinding;
import com.galaxiescoders.wastetracker.models.Vehicle;
import com.galaxiescoders.wastetracker.models.Zone;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdminToolsFragment extends Fragment {
    private FragmentAdminToolsBinding binding;
    private ProgressDialog progressDialog;
    private ArrayList<Vehicle> vehiclesList;
    private DatabaseReference databaseReference;
    private RecyclerView vehiclesRec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminToolsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading image...");
        progressDialog.setCancelable(false);
        vehiclesRec = root.findViewById(R.id.toolsRV);
        vehiclesRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        vehiclesList = new ArrayList<>();

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Tools"); // Replace with your actual Firebase node

        fetchTools();
        binding.addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.add_tools, null);

                // Remove the local variable here
                EditText regNumber = dialogView.findViewById(R.id.prod_title);
                EditText engNumber = dialogView.findViewById(R.id.price);
                EditText manufacture = dialogView.findViewById(R.id.quantity);
                EditText model = dialogView.findViewById(R.id.description);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.submit_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String regNum = regNumber.getText().toString();
                        String engineNum = engNumber.getText().toString();
                        String YOM = manufacture.getText().toString();
                        String Model = model.getText().toString();

                        // Check if any of the fields are empty
                        if (TextUtils.isEmpty(regNum) || TextUtils.isEmpty(engineNum) || TextUtils.isEmpty(YOM) || TextUtils.isEmpty(Model)) {
                            Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Tools");
                        Vehicle vehicle = new Vehicle(regNum, engineNum, YOM, Model, "Unassigned", " ");
                        databaseReference.child(Model).setValue(vehicle);

                        Toast.makeText(getContext(), "Data saved to Firebase", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        // Clear the fields in your dialog
                        regNumber.getText().clear();
                        engNumber.getText().clear();
                        manufacture.getText().clear();
                        model.getText().clear();

                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });

                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });

        return root;
    }

    private void fetchTools() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vehiclesList.clear(); // Clear existing data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Parse Vehicle data from Firebase
                    Vehicle vehicle = dataSnapshot.getValue(Vehicle.class);

                    if (vehicle != null) {
                        vehiclesList.add(vehicle); // Add vehicle to the list
                    }
                }

                // Create and set the adapter for the RecyclerView
                AdminToolsAdapter adminToolsAdapter = new AdminToolsAdapter(vehiclesList, getContext());
                vehiclesRec.setAdapter(adminToolsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
                Toast.makeText(getContext(), "Failed to fetch tools from Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
