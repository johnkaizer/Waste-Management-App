package com.galaxiescoders.wastetracker.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.adapters.AdminToolsAdapter;
import com.galaxiescoders.wastetracker.adapters.JobAdminAdapter;
import com.galaxiescoders.wastetracker.adapters.TenderAdminAdapter;
import com.galaxiescoders.wastetracker.databinding.FragmentHomeBinding;
import com.galaxiescoders.wastetracker.models.JobModel;
import com.galaxiescoders.wastetracker.models.TenderModel;
import com.galaxiescoders.wastetracker.models.Vehicle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ProgressDialog progressDialog;
    private ArrayList<TenderModel> tenderList;
    private ArrayList<JobModel> jobList;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;
    private RecyclerView tendersRec;
    private RecyclerView jobsRec;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading data...");
        progressDialog.setCancelable(false);
        // Display Jobs RecyclerView horizontally
        jobsRec = root.findViewById(R.id.jobsRv);
        LinearLayoutManager jobsLayoutManager = new LinearLayoutManager(getActivity());
        jobsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // Set horizontal orientation
        jobsRec.setLayoutManager(jobsLayoutManager);
        jobList = new ArrayList<>();

// Initialize Firebase for Jobs
        FirebaseDatabase jobDatabase = FirebaseDatabase.getInstance();
        databaseReference = jobDatabase.getReference("Jobs");

// Display Tenders RecyclerView horizontally
        tendersRec = root.findViewById(R.id.tenderRv);
        LinearLayoutManager tendersLayoutManager = new LinearLayoutManager(getActivity());
        tendersLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // Set horizontal orientation
        tendersRec.setLayoutManager(tendersLayoutManager);
        tenderList = new ArrayList<>();

// Initialize Firebase for Tenders
        FirebaseDatabase tenderDatabase = FirebaseDatabase.getInstance();
        databaseReference1 = tenderDatabase.getReference("Tenders");

        fetchTenders();
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
        binding.addJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.add_jobs, null);

                EditText title = dialogView.findViewById(R.id.idEdTitle);
                EditText description = dialogView.findViewById(R.id.idEdDesc);
                EditText salary = dialogView.findViewById(R.id.idEdAmount);
                EditText deadline = dialogView.findViewById(R.id.idDeadline);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                // Set OnClickListener for pickDateButton to show date picker
                deadline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Initialize Calendar instance
                        final Calendar calendar = Calendar.getInstance();

                        // Create DatePickerDialog with the current date
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        // Set the chosen date on EditText
                                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                        deadline.setText(selectedDate);
                                    }
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));

                        // Show the DatePickerDialog
                        datePickerDialog.show();
                    }
                });

                dialogView.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Title = title.getText().toString();
                        String Desc = description.getText().toString();
                        String Salary = salary.getText().toString();
                        String Deadline = deadline.getText().toString();

                        // Check if any of the fields are empty
                        if (TextUtils.isEmpty(Title) || TextUtils.isEmpty(Desc) || TextUtils.isEmpty(Salary) || TextUtils.isEmpty(Deadline)) {
                            Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs");
                        JobModel jobModel = new JobModel(Title, Desc, Salary, Deadline);
                        databaseReference.child(Title).setValue(jobModel);

                        Toast.makeText(getContext(), "Data saved ", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        // Clear the fields in your dialog
                        title.getText().clear();
                        description.getText().clear();
                        salary.getText().clear();
                        deadline.getText().clear();

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
        binding.addTender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.add_tender, null);

                EditText title = dialogView.findViewById(R.id.idEdTitle);
                EditText description = dialogView.findViewById(R.id.idEdDesc);
                EditText salary = dialogView.findViewById(R.id.idEdAmount);
                EditText deadline = dialogView.findViewById(R.id.idDeadline);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                // Set OnClickListener for pickDateButton to show date picker
                deadline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Initialize Calendar instance
                        final Calendar calendar = Calendar.getInstance();

                        // Create DatePickerDialog with the current date
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        // Set the chosen date on EditText
                                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                        deadline.setText(selectedDate);
                                    }
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));

                        // Show the DatePickerDialog
                        datePickerDialog.show();
                    }
                });

                dialogView.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Title = title.getText().toString();
                        String Desc = description.getText().toString();
                        String Salary = salary.getText().toString();
                        String Deadline = deadline.getText().toString();

                        // Check if any of the fields are empty
                        if (TextUtils.isEmpty(Title) || TextUtils.isEmpty(Desc) || TextUtils.isEmpty(Salary) || TextUtils.isEmpty(Deadline)) {
                            Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Tenders");
                        JobModel jobModel = new JobModel(Title, Desc, Salary, Deadline);
                        databaseReference.child(Title).setValue(jobModel);

                        Toast.makeText(getContext(), "Data saved ", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        // Clear the fields in your dialog
                        title.getText().clear();
                        description.getText().clear();
                        salary.getText().clear();
                        deadline.getText().clear();

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


        return root;
    }

    private void fetchJobs() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobList.clear(); // Clear existing data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Parse vacancies data from Firebase
                    JobModel jobModel = dataSnapshot.getValue(JobModel.class);

                    if (jobModel != null) {
                        jobList.add(jobModel); // Add vacancies to the list
                    }
                }

                // Create and set the adapter for the RecyclerView
                JobAdminAdapter jobAdminAdapter = new JobAdminAdapter(jobList, getContext());
                jobsRec.setAdapter(jobAdminAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
                Toast.makeText(getContext(), "Failed to fetch vacancies from Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchTenders() {
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tenderList.clear(); // Clear existing data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Parse tenders data from Firebase
                    TenderModel tender = dataSnapshot.getValue(TenderModel.class);

                    if (tender != null) {
                        tenderList.add(tender); // Add tenders to the list
                    }
                }

                // Create and set the adapter for the RecyclerView
                TenderAdminAdapter tenderAdminAdapter = new TenderAdminAdapter(tenderList, getContext());
                tendersRec.setAdapter(tenderAdminAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
                Toast.makeText(getContext(), "Failed to fetch tenders from Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}