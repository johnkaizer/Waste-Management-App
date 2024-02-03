package com.galaxiescoders.wastetracker.ui;

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
import com.galaxiescoders.wastetracker.adapters.JobAdapter;
import com.galaxiescoders.wastetracker.adapters.PolicyAdapter;
import com.galaxiescoders.wastetracker.adapters.StaffToolAdapter;
import com.galaxiescoders.wastetracker.adapters.UsersManagementAdapter;
import com.galaxiescoders.wastetracker.databinding.FragmentHomeBinding;
import com.galaxiescoders.wastetracker.databinding.FragmentStaffHomeBinding;
import com.galaxiescoders.wastetracker.models.JobModel;
import com.galaxiescoders.wastetracker.models.PolicyModel;
import com.galaxiescoders.wastetracker.models.User;
import com.galaxiescoders.wastetracker.models.Vehicle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kotlinx.coroutines.Job;

public class StaffHomeFragment extends Fragment {
    private FragmentStaffHomeBinding binding;
    //Policy
    PolicyAdapter policyAdapter;
    private ArrayList<PolicyModel> policyList;
    private RecyclerView policyRec;
    private DatabaseReference databaseReference;
    JobAdapter jobAdapter;
    private ArrayList<JobModel> list;
    private RecyclerView jobRec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStaffHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        jobRec = root.findViewById(R.id.jobsRv);
        jobRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Jobs");

        // Fetch jobs lists from Firebase
        fetchJobs();

        // Get the current hour
        Calendar calendar1 = Calendar.getInstance();
        int hourOfDay = calendar1.get(Calendar.HOUR_OF_DAY);
        // Get the current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM, yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar1.getTime());

        // Set the appropriate greeting based on the time of day
        String greeting;
        if (hourOfDay >= 0 && hourOfDay < 12) {
            greeting = "Good morning";
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
            greeting = "Good afternoon";
        } else {
            greeting = "Good evening";
        }

        // Display the greeting in the TextView
        binding.greetingsText.setText(greeting);
        // Set the formatted date in the TextView
        binding.dateTextView.setText(formattedDate);
        //Policy Stuff
        policyRec = root.findViewById(R.id.policesRv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        policyRec.setLayoutManager(layoutManager);
        policyList = (ArrayList<PolicyModel>) getSamplePolicyData();
        policyAdapter = new PolicyAdapter(getContext(), policyList);
        policyRec.setAdapter(policyAdapter);



        return root;
    }

    private void fetchJobs() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear existing data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    JobModel job = dataSnapshot.getValue(JobModel.class);
                    list.add(job);
                }

                // Initialize and set adapter
                jobAdapter = new JobAdapter(list);
                jobRec.setAdapter(jobAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors here
                Toast.makeText(getActivity(), "Error fetching jobs: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<PolicyModel> getSamplePolicyData() {
        List<PolicyModel> policy = new ArrayList<>();
        policy.add(new PolicyModel("SOLID WASTE MANAGEMENT", "Sept 28th 2019", "It is the city County ’s duty to regulate waste and its management within the city.\n" +
                "The County may revoke/cancel a waste operator’s permit in the breach of given conditions.\n" +
                "Waste operators permit is not transferable without the consent of the County .\n" +
                "The County must provide a place to dispose waste before it is transferable to a final disposal.",  "239"));
        policy.add(new PolicyModel("Waste Management Act", "16 October 2015", "The Act declares that solid waste management shall be a shared responsibility amongst all actors including the county government, generators, owners and occupiers of premises and contracted service providers and that every person within the county is entitled to a clean and healthy environment and has a duty to safeguard and enhance the quality of the environment. ",  "100"));
        policy.add(new PolicyModel("Cleaning Nairobi City", "12th Dec 2023", "As the population of Nairobi grows, so does the amount of solid waste. Currently, the population of the city stands at approximately 5 million, with each individual generating about 0.62 kgs of garbage per day.",  "340"));
        policy.add(new PolicyModel("SUSTAINABLE WASTE MANAGEMENT ACT", ": 6th July, 2022", "Each county government shall—\n" +
                "(a) enact a county sustainable waste management\n" +
                "legislation within two years of the coming into\n" +
                "operation of thi s Act;\n" +
                "(b) establish waste recovery and recycling facilities\n" +
                "and sanitary landfills for the disposal of\n" +
                "recoverable waste;",  "12"));
        policy.add(new PolicyModel("Waste Management Services by Private Firms", "15th June 2022", "County governments have contracted private waste management firms to\n" +
                "collect garbage, transport and dispose waste and other related services.Many\n" +
                "counties also supply bins, liners and collection bags. ",  "424"));
        policy.add(new PolicyModel("SOLID WASTE MANAGEMENT", "Sept 28th 2019", "May",  "45"));


        // Add more policies
        return policy;
    }
}