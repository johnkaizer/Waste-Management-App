package com.galaxiescoders.wastetracker.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.adapters.PaymentAdapter;
import com.galaxiescoders.wastetracker.adapters.StaffToolAdapter;
import com.galaxiescoders.wastetracker.databinding.FragmentNotificationsBinding;
import com.galaxiescoders.wastetracker.models.Payment;
import com.galaxiescoders.wastetracker.models.Vehicle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    PaymentAdapter paymentAdapter;
    private ArrayList<Payment> paymentList;
    private DatabaseReference databaseReference;
    private RecyclerView payRec;
    private String userId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        payRec = root.findViewById(R.id.subRv);
        payRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        paymentList = new ArrayList<>();

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Payments");

        // Retrieve staff id from SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        userId = preferences.getString("idNo", "");

        // Query to filter vehicles based on user ID
        Query query = databaseReference.orderByChild("userUid").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                paymentList.clear(); // Clear existing data

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Payment payment  = snapshot.getValue(Payment.class);
                    if (payment != null) {
                        paymentList.add(payment);
                    }
                }

                // Update your RecyclerView adapter with the filtered payments
                paymentAdapter = new PaymentAdapter(paymentList);
                payRec.setAdapter(paymentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(getActivity(), "Error retrieving payments " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}