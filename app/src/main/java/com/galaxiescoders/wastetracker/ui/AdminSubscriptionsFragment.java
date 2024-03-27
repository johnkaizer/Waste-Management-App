package com.galaxiescoders.wastetracker.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.adapters.AdminPaymentAdapter;
import com.galaxiescoders.wastetracker.databinding.FragmentAdminSubscriptionsBinding;
import com.galaxiescoders.wastetracker.databinding.FragmentAdminToolsBinding;
import com.galaxiescoders.wastetracker.models.Payment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminSubscriptionsFragment extends Fragment {

    private FragmentAdminSubscriptionsBinding binding;
    private RecyclerView subscriptionRV;
    private AdminPaymentAdapter adminPaymentAdapter;
    private DatabaseReference subscriptionsRef;
    private ArrayList<Payment> subscriptionList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminSubscriptionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        subscriptionRV = binding.subsRV;
        subscriptionRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        subscriptionList = new ArrayList<>();
        adminPaymentAdapter = new AdminPaymentAdapter(subscriptionList);
        subscriptionRV.setAdapter(adminPaymentAdapter);

        // Initialize Firebase
        subscriptionsRef = FirebaseDatabase.getInstance().getReference().child("Payments");

        // Retrieve subscriptions from Firebase
        retrieveSubscriptions();

        return root;
    }

    private void retrieveSubscriptions() {
        subscriptionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subscriptionList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Payment payment = snapshot.getValue(Payment.class);
                    if (payment != null) {
                        subscriptionList.add(payment);
                    }
                }
                adminPaymentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Log.e("AdminSubscriptions", "Error retrieving subscriptions: " + databaseError.getMessage());
            }
        });
    }

}

