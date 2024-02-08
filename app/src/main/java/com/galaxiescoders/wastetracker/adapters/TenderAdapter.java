package com.galaxiescoders.wastetracker.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.ApplicationModel;
import com.galaxiescoders.wastetracker.models.TenderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TenderAdapter extends RecyclerView.Adapter<TenderAdapter.ViewHolder> {
    List<TenderModel>list;
    Context context;

    public TenderAdapter(List<TenderModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TenderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tender_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TenderAdapter.ViewHolder holder, int position) {
        TenderModel tenderModel  = list.get(position);
        holder.titleTxt.setText(tenderModel.getTitle());
        holder.Salary.setText(tenderModel.getAmount());
        holder.deadline.setText(tenderModel.getDeadline());
        holder.desc.setText(tenderModel.getDescription());
        holder.applybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve idNo from SharedPreferences
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE);
                String idNo = sharedPreferences.getString("idNo", "");

                // Get the currently logged-in user from Firebase Authentication
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = auth.getCurrentUser();

                // Check if a user is logged in
                if (currentUser != null) {
                    // User is logged in, retrieve their display name
                    String loggedInUserName = currentUser.getDisplayName();
                    // Display name is available, check if the user has already applied for this job
                    DatabaseReference applicationsRef = FirebaseDatabase.getInstance().getReference().child("Applications").child(tenderModel.getTitle());
                    applicationsRef.orderByChild("idNo").equalTo(idNo).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // User has already applied for this job
                                Toast.makeText(view.getContext(), "You have already applied for this job", Toast.LENGTH_SHORT).show();
                            } else {
                                // User has not applied for this job yet, proceed to save the application
                                saveApplication(idNo, loggedInUserName);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle database read error
                            Toast.makeText(view.getContext(), "Failed to check existing applications", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // No user is currently logged in, you may handle this case accordingly
                    Toast.makeText(view.getContext(), "No user is logged in", Toast.LENGTH_SHORT).show();
                }
            }

            private void saveApplication(String idNo, String loggedInUserName) {
                // Retrieve job title
                String jobTitle = tenderModel.getTitle();

                // Get today's date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDate = dateFormat.format(new Date());

                // Create an ApplicationModel instance
                ApplicationModel applicationModel = new ApplicationModel();
                applicationModel.setIdNo(idNo);
                applicationModel.setName(loggedInUserName);
                applicationModel.setTitle(jobTitle);
                applicationModel.setDate(currentDate);

                // Save the application using the job title as the node
                DatabaseReference applicationsRef = FirebaseDatabase.getInstance().getReference().child("Applications");
                applicationsRef.child(jobTitle).setValue(applicationModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Application submitted successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Failed to submit application", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        TextView Salary;
        TextView deadline;
        TextView desc;
        Button applybutton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.modelTxt);
            Salary = itemView.findViewById(R.id.numberTxt);
            deadline = itemView.findViewById(R.id.mantxt);
            desc = itemView.findViewById(R.id.description);
            applybutton = itemView.findViewById(R.id.applyBtn);

        }
    }
}
