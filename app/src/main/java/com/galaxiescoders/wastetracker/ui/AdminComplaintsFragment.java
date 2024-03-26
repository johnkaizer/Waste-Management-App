package com.galaxiescoders.wastetracker.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.adapters.AdminComplaintAdapter;
import com.galaxiescoders.wastetracker.databinding.FragmentAdminComplaintsBinding;
import com.galaxiescoders.wastetracker.databinding.FragmentUserComplaintBinding;
import com.galaxiescoders.wastetracker.models.ComplaintModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminComplaintsFragment extends Fragment {

    private FragmentAdminComplaintsBinding binding;
    private ProgressDialog progressDialog;
    private RecyclerView complaintRv;
    private DatabaseReference databaseReference;
    private AdminComplaintAdapter complaintAdapter;
    private List<ComplaintModel> complaintList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminComplaintsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize Firebase components
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Complaints");

        // Initialize RecyclerView
        complaintRv = root.findViewById(R.id.recyclerViewComplaints);
        complaintRv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Complaint List and Adapter
        complaintList = new ArrayList<>();
        complaintAdapter = new AdminComplaintAdapter(complaintList);
        complaintRv.setAdapter(complaintAdapter);

        // Fetch all complaints
        fetchAllComplaints();

        return root;
    }

    private void fetchAllComplaints() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading pending complaints...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Query to fetch complaints with status equal to "pending"
        Query query = databaseReference.orderByChild("status").equalTo("pending");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
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
                // Handle database error
            }
        });
    }

}
