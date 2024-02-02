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
    public static final int REQUEST_CODE_IMAGE = 101;
    private ProgressDialog progressDialog;

    Uri imageUri;
    boolean isImageAdded = false;
    ImageView imageView;
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
        imageView =root.findViewById(R.id.imageV);
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
                ImageView imageView = dialogView.findViewById(R.id.imageV);
                EditText regNumber = dialogView.findViewById(R.id.prod_title);
                EditText engNumber = dialogView.findViewById(R.id.price);
                EditText manufacture = dialogView.findViewById(R.id.quantity);
                EditText model = dialogView.findViewById(R.id.description);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.car_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, REQUEST_CODE_IMAGE);
                    }
                });

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

                        // Check if an image is selected
                        if (!isImageAdded) {
                            Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Perform image upload to Firebase Storage
                        uploadImageToFirebase(regNum, engineNum, YOM, Model);
                        progressDialog.show();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            isImageAdded = true;
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadImageToFirebase(String regNum, String engineNum, String YOM, String Model) {
        if (imageUri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("tools_images/" + System.currentTimeMillis() + ".jpg");
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Handle the download URL (uri) and other details here
                            String imageUrl = uri.toString();

                            // Now you can save the imageUrl along with other details to Firebase Database
                            saveDataToFirebase(regNum, engineNum, YOM, Model, imageUrl);
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        } else {
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Image not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDataToFirebase(String regNum, String engineNum, String YOM, String Model, String imageUrl) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Tools");
        Vehicle  vehicle = new Vehicle(regNum, engineNum, YOM, Model, imageUrl,"Unassigned","none");
        databaseReference.push().setValue(vehicle);

        Toast.makeText(getContext(), "Data saved to Firebase", Toast.LENGTH_SHORT).show();
    }
    private void fetchTools() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vehiclesList.clear(); // Clear existing data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Parse Vehicle data from Firebase
                    Vehicle vehicle  = dataSnapshot.getValue(Vehicle.class);

                    if (vehicle != null) {
                        vehiclesList.add(vehicle); // Add vehicle to the list
                    }
                }

                // Create and set the adapter for the RecyclerView
                AdminToolsAdapter adminToolsAdapter = new AdminToolsAdapter(vehiclesList);
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
