package com.galaxiescoders.wastetracker.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.adapters.PackagesAdapter;
import com.galaxiescoders.wastetracker.adapters.PolicyAdapter;
import com.galaxiescoders.wastetracker.databinding.FragmentStaffHomeBinding;
import com.galaxiescoders.wastetracker.databinding.FragmentUsersSubscriptionsBinding;
import com.galaxiescoders.wastetracker.models.PolicyModel;
import com.galaxiescoders.wastetracker.models.Subscription;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class UsersSubscriptionsFragment extends Fragment {
    private FragmentUsersSubscriptionsBinding binding;
    PackagesAdapter packagesAdapter;
    private ArrayList<Subscription> subscriptions;
    private RecyclerView subRec;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUsersSubscriptionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Subscription Packages Stuff
        subRec = root.findViewById(R.id.packagesRV);
        subRec.setLayoutManager(new LinearLayoutManager(getContext()));
        subscriptions = (ArrayList<Subscription>) getSampleSubData();
        packagesAdapter = new PackagesAdapter(getContext(), subscriptions);
        subRec.setAdapter(packagesAdapter);

        return root;
    }

    private List<Subscription> getSampleSubData() {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription("1", "Basic Plan",  "Weekly trash pickup\n" +
                "Recycling services\n" +
                "Complimentary waste bins"));
        subscriptions.add(new Subscription("2", "Green Plan",  "Eco-friendly waste disposal\n" +
                "Bi-weekly garden waste removal\n" +
                "Recycling education materials"));
        subscriptions.add(new Subscription("4", "Premium Plan",  "Daily trash pickup\n" +
                "Hazardous waste disposal\n" +
                "Exclusive customer support hotline\n" +
                "Complimentary waste audit"));
        subscriptions.add(new Subscription("5", "Business Eco Bundle",  "Waste reduction consultation\n" +
                "Commercial recycling services\n" +
                "Quarterly sustainability reports\n" +
                "Employee environmental training"));
        subscriptions.add(new Subscription("6", "Family Plan",  "Multiple waste bin options\n" +
                "Monthly bulk item pickup\n" +
                "Exclusive family-friendly recycling workshops"));
        subscriptions.add(new Subscription("8", "Community Cleanup",  "Community-wide cleanup events\n" +
                "Educational seminars\n" +
                "Discounted rates for neighborhood participants"));
        subscriptions.add(new Subscription("9", "Tech Recycling Add-On",  "Electronic waste pickup\n" +
                "Secure data destruction\n" +
                "Annual gadget recycling event"));
        subscriptions.add(new Subscription("3", "Student Sustainability Pack",  "Bi-monthly waste reduction challenges\n" +
                "Student-exclusive eco-friendly merchandise\n" +
                "Green campus initiatives updates"));
        subscriptions.add(new Subscription("10", "Zero-Waste Warrior",  "Advanced recycling options\n" +
                "Compostable waste collection\n" +
                "Exclusive zero-waste lifestyle guide"));

        // Add more subscriptions
        return subscriptions;
    }
}