package com.galaxiescoders.wastetracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.ComplaintModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminComplaintAdapter extends RecyclerView.Adapter<AdminComplaintAdapter.ViewHolder> {

    private List<ComplaintModel> complaints;

    // Constructor
    public AdminComplaintAdapter(List<ComplaintModel> complaints) {
        this.complaints = complaints;
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textLocationValue;
        private TextView textDateValue;
        private TextView textComplaintValue;
        private TextView textPhoneValue;
        private TextView buttonResolve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textLocationValue = itemView.findViewById(R.id.textLocationValue);
            textDateValue = itemView.findViewById(R.id.textDateValue);
            textComplaintValue = itemView.findViewById(R.id.textComplaintValue);
            textPhoneValue = itemView.findViewById(R.id.textPhoneValue);
            buttonResolve = itemView.findViewById(R.id.buttonResolve);
        }

        public void bind(ComplaintModel complaint) {
            textLocationValue.setText(complaint.getLocation());
            textDateValue.setText(complaint.getComplaintDate());
            textComplaintValue.setText(complaint.getComplaint());
            textPhoneValue.setText(complaint.getPhoneNumber());

            buttonResolve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Update status to "Fixed" in Firebase
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Complaints").child(complaint.getComplaintId());
                    databaseReference.child("status").setValue("Fixed");
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_complaint_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(complaints.get(position));
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }
}


