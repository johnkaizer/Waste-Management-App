package com.galaxiescoders.wastetracker.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.adapters.ComplaintAdapter;
import com.galaxiescoders.wastetracker.databinding.FragmentUserComplaintBinding;
import com.galaxiescoders.wastetracker.models.ComplaintModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserComplaintFragment extends Fragment {

    private FragmentUserComplaintBinding binding;
    private ProgressDialog progressDialog;
    private RecyclerView complaintRv;
    private DatabaseReference databaseReference;
    private ComplaintAdapter complaintAdapter;
    private List<ComplaintModel> complaintList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserComplaintBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Initialize Firebase components
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Complaints");
        complaintRv = root.findViewById(R.id.recyclerViewComplaints);
        // Initialize RecyclerView
        complaintList = new ArrayList<>();
        complaintAdapter = new ComplaintAdapter(complaintList);
        complaintRv.setLayoutManager(new LinearLayoutManager(getContext()));
        complaintRv.setAdapter(complaintAdapter);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading data...");
        progressDialog.setCancelable(false);
        binding.btnCreateComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.add_complaint, null);

                EditText description = dialogView.findViewById(R.id.idEdTitle);
                EditText location = dialogView.findViewById(R.id.idEdDesc);
                EditText phone = dialogView.findViewById(R.id.idEdAmount);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialogView.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String complaint = description.getText().toString();
                        String phoneNumber = phone.getText().toString();
                        String locationComp = location.getText().toString();

                        // Check if any of the fields are empty
                        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(complaint) || TextUtils.isEmpty(locationComp)) {
                            Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Retrieve clientId from SharedPreferences
                        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                        String userId = preferences.getString("idNo", "");

                        // Generate postDate in the required format
                        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd h:mma", Locale.getDefault());
                        String compdate = sdf.format(new Date());

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Complaints");
                        String complaintId = databaseReference.push().getKey(); // Generate unique ID
                        ComplaintModel userComplaint = new ComplaintModel(complaintId, userId, locationComp, complaint, compdate, "pending", phoneNumber);
                        databaseReference.child(complaintId).setValue(userComplaint); // Use generated ID to set value

                        Toast.makeText(getContext(), "Data saved ", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        // Clear the fields in your dialog
                        phone.getText().clear();
                        description.getText().clear();
                        location.getText().clear();

                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });

                dialogView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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

        // Fetch user's complaints
        fetchUserComplaints();

        return root;
    }

    private void fetchUserComplaints() {
        progressDialog.show();

        // Retrieve clientId from SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String clientId = preferences.getString("idNo", "");

        // Query the database to fetch complaints where userId equals clientId
        Query query = databaseReference.orderByChild("userId").equalTo(clientId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                complaintList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ComplaintModel complaint = snapshot.getValue(ComplaintModel.class);
                    complaintList.add(complaint);
                }
                complaintAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Failed to fetch complaints: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}